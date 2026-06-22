//> using scala "3.8.4"
//> using jvm 21
//> using dep "com.lihaoyi::os-lib:0.11.8"
//> using dep "com.lihaoyi::requests:0.9.3"
//> using dep "com.lihaoyi::ujson:4.4.3"

// auto-translate.sc — regenerate muntabot's English side from the Swedish side.
//
// Run from the muntabot repo root:   scala-cli auto-translate.sc
//
// Reads the Swedish sources (data-sv.scala, headings-GENERATED.scala,
// concept-headings-GENERATED.scala) and writes the English artifacts
// (data-en.scala, headings-En-GENERATED.scala, concept-headings-En-GENERATED.scala).
//
// Determinism & safety:
//  - A committed sv->en cache (translate-cache.tsv) makes the build idempotent:
//    the model runs ONLY on cache misses, so re-running changes nothing and
//    ollama isn't even needed unless the Swedish text actually changed.
//  - Authoritative translations (translations-GENERATED.scala) win over cache/model.
//  - Before translating, `code` and $math$ spans are masked so the model can't
//    corrupt them; output is validated and falls back to the Swedish source on any
//    failure (worst case English==Swedish — never a broken build).
//  - ollama: temperature 0 + fixed seed => reproducible first-time translations.

import scala.collection.mutable
import scala.util.matching.Regex

val ScalaDir = os.pwd / "src" / "main" / "scala"
val CacheFile = os.pwd / "translate-cache.tsv"

val MODEL = "qwen2.5:3b"
val SEED = 42
val OLLAMA = "http://localhost:11434/api/chat"

// ---------- string-literal helpers ----------
val LIT: Regex = "\"((?:\\\\.|[^\"\\\\])*)\"".r           // a Scala string literal
val MARK: Regex = "(`[^`]*`|\\$[^$]*\\$)".r               // `code` or $math$ (kept verbatim)

def decode(s: String): String =
  val sb = StringBuilder(); var i = 0
  while i < s.length do
    if s(i) == '\\' && i + 1 < s.length then
      s(i + 1) match
        case 'n'  => sb.append('\n'); i += 2
        case 't'  => sb.append('\t'); i += 2
        case '"'  => sb.append('"'); i += 2
        case '\\' => sb.append('\\'); i += 2
        case c    => sb.append(c); i += 2
    else { sb.append(s(i)); i += 1 }
  sb.toString

def encode(s: String): String = s.flatMap:
  case '\\' => "\\\\"; case '"' => "\\\""; case '\n' => "\\n"; case '\t' => "\\t"; case c => c.toString

def litsOf(text: String): Seq[String] = LIT.findAllMatchIn(text).map(m => decode(m.group(1))).toVector
def isUrl(s: String): Boolean = s.startsWith("http")
def stripKey(s: String): String = s.replace("`", "").trim

// ---------- block parsing (for concept/contrast keys) ----------
def blocks(text: String): Seq[(String, String)] =
  val out = mutable.ArrayBuffer[(String, String)]()
  for m <- "(Concepts|Contrasts|Code)\\(".r.findAllMatchIn(text) do
    var depth = 0; var j = m.end - 1; val start = j; var done = false
    while j < text.length && !done do
      text(j) match
        case '(' => depth += 1
        case ')' => depth -= 1; if depth == 0 then done = true
        case _   =>
      if !done then j += 1
    out += ((m.group(1), text.substring(start + 1, j)))
  out.toSeq

def conceptKeys(text: String): Seq[String] =
  val ks = mutable.ArrayBuffer[String]()
  for (kind, body) <- blocks(text) do
    val lits = litsOf(body)
    kind match
      case "Concepts"  => ks ++= lits.map(stripKey)
      case "Contrasts" => lits.grouped(2).foreach(p => if p.size == 2 then ks += s"${stripKey(p(0))} | ${stripKey(p(1))}")
      case _           => // skip Code
  ks.toSeq

// ---------- pair parsing ("a" -> "b") ----------
val PAIR: Regex = "\"((?:\\\\.|[^\"\\\\])*)\"\\s*->\\s*\"((?:\\\\.|[^\"\\\\])*)\"".r
def pairsOf(text: String): Seq[(String, String)] =
  PAIR.findAllMatchIn(text).map(m => (decode(m.group(1)), decode(m.group(2)))).toVector

// ---------- authoritative + cache ----------
val authoritative: Map[String, String] =
  val f = ScalaDir / "translations-GENERATED.scala"
  if os.exists(f) then pairsOf(os.read(f)).toMap else Map.empty

val cache = mutable.LinkedHashMap[String, String]()
def loadCache(): Unit =
  if os.exists(CacheFile) then
    for line <- os.read.lines(CacheFile) if line.contains("\t") do
      val Array(sv, en) = line.split("\t", 2)
      cache(sv.replace("\\n", "\n")) = en.replace("\\n", "\n")
def saveCache(): Unit =
  val body = cache.toSeq.sortBy(_._1).map((sv, en) => s"${sv.replace("\n", "\\n")}\t${en.replace("\n", "\\n")}").mkString("\n")
  os.write.over(CacheFile, body + "\n")

/** First run only: seed the cache from the existing reviewed English so the very
  * first regeneration is a no-op (no model calls). */
def bootstrap(): Unit =
  // headings-En: already an sv->en map
  val hEn = ScalaDir / "headings-En-GENERATED.scala"
  if os.exists(hEn) then for (sv, en) <- pairsOf(os.read(hEn)) do cache.getOrElseUpdate(sv, en)
  // data: align literals of data-sv and data-en positionally (skip URLs)
  val svL = litsOf(os.read(ScalaDir / "data-sv.scala"))
  val enF = ScalaDir / "data-en.scala"
  if os.exists(enF) then
    val enL = litsOf(os.read(enF))
    if svL.size == enL.size then
      for (sv, en) <- svL.zip(enL) if !isUrl(sv) do cache.getOrElseUpdate(sv, en)
    else println(s"  [warn] data-sv (${svL.size}) and data-en (${enL.size}) literal counts differ — skipping data bootstrap")

// ---------- ollama ----------
var modelCalls = 0
var fallbacks = 0

def glossaryFor(t: String): String =
  val low = t.toLowerCase
  authoritative.collect { case (sv, en) if sv.length >= 4 && low.contains(sv.toLowerCase) => s"$sv = $en" }
    .toSeq.sorted.take(40).mkString("; ")

def ollamaTranslate(sv: String): String =
  // mask `code` and $math$ as placeholders the model must keep verbatim
  val spans = mutable.ArrayBuffer[String]()
  val masked = MARK.replaceAllIn(sv, m => { spans += m.matched; Regex.quoteReplacement(s"__C${spans.size - 1}__") })
  val gloss = glossaryFor(sv)
  val system =
    "You are a precise Swedish-to-English translator for an introductory Scala programming course. " +
      "Translate the Swedish to natural English. Keep every placeholder token of the form __C0__, __C1__, ... " +
      "EXACTLY as-is and in place. Keep numbers, symbols and math unchanged. " +
      "Output ONLY the translation: no quotes, no notes, no extra text." +
      (if gloss.nonEmpty then s" Use these official term translations where they occur: $gloss." else "")
  val payload = ujson.Obj(
    "model" -> MODEL,
    "messages" -> ujson.Arr(
      ujson.Obj("role" -> "system", "content" -> system),
      ujson.Obj("role" -> "user", "content" -> masked)
    ),
    "stream" -> false,
    "options" -> ujson.Obj("seed" -> SEED, "temperature" -> 0, "num_thread" -> 4, "num_ctx" -> 2048)
  )
  val resp = requests.post(OLLAMA, data = ujson.write(payload),
    headers = Map("Content-Type" -> "application/json"), readTimeout = 600000, connectTimeout = 10000)
  var out = ujson.read(resp.text())("message")("content").str.trim
  // restore placeholders; validate
  val allPresent = spans.indices.forall(i => out.contains(s"__C${i}__"))
  for i <- spans.indices do out = out.replace(s"__C${i}__", spans(i))
  val valid = allPresent && out.nonEmpty && !out.contains("__C") && out.length <= sv.length * 4 + 80
  if valid then out
  else { fallbacks += 1; println(s"  [fallback] kept Swedish for: ${sv.take(60)}"); sv }

/** sv -> en with precedence: authoritative > cache > model. */
def translate(sv: String): String =
  if sv.isEmpty then ""
  else authoritative.get(sv).orElse(cache.get(sv)).getOrElse {
    modelCalls += 1
    val en = ollamaTranslate(sv)
    cache(sv) = en
    en
  }

// ---------- generators ----------
def genDataEn(): Unit =
  val svText = os.read(ScalaDir / "data-sv.scala")
  val enText = LIT.replaceAllIn(svText, m => {
    val c = decode(m.group(1))
    val out = if isUrl(c) then c else translate(c)
    Regex.quoteReplacement("\"" + encode(out) + "\"")
  }).replace("lazy val termsSv", "lazy val termsEn")
  os.write.over(ScalaDir / "data-en.scala", enText)

def genHeadingsEn(): Unit =
  val svData = os.read(ScalaDir / "data-sv.scala")
  val fromUrls = "#nameddest=([^\"]+)".r.findAllMatchIn(svData)
    .map(m => java.net.URLDecoder.decode(m.group(1), "UTF-8")).toSeq
  val fromConcept = pairsOf(os.read(ScalaDir / "concept-headings-GENERATED.scala")).map(_._2)
  val headings = (fromUrls ++ fromConcept).distinct.sortBy(_.toLowerCase)
  val entries = headings.map(h => s"""    "${encode(h)}" -> "${encode(translate(h))}"""").mkString(",\n")
  val out =
    s"""package shared
       |
       |  /** English DISPLAY translations of the Swedish compendium headings that
       |    * muntabot links to. Links still open the Swedish compendium.pdf; only the
       |    * shown heading text is translated. GENERATED by auto-translate.sc. */
       |  lazy val headingTranslateSvEn: Map[String, String] = Map(
       |$entries
       |  )
       |""".stripMargin
  os.write.over(ScalaDir / "headings-En-GENERATED.scala", out)

def genConceptHeadingsEn(): Unit =
  val svKeys = conceptKeys(os.read(ScalaDir / "data-sv.scala"))
  val enKeys = conceptKeys(os.read(ScalaDir / "data-en.scala")) // freshly generated this run
  val sv2en = if svKeys.size == enKeys.size then svKeys.zip(enKeys).toMap else Map.empty[String, String]
  if sv2en.isEmpty then println(s"  [warn] concept key counts differ (sv ${svKeys.size}, en ${enKeys.size}) — concept-headings-En may be incomplete")
  val entries = pairsOf(os.read(ScalaDir / "concept-headings-GENERATED.scala")).flatMap { (svKey, heading) =>
    sv2en.get(svKey).map(enKey => s"""    "${encode(enKey)}" -> "${encode(heading)}"""")
  }.mkString(",\n")
  val out =
    s"""package shared
       |
       |  /** English concept/contrast key -> Swedish compendium heading. Keys are the
       |    * English terms (aligned with data-en); values stay Swedish so links resolve
       |    * to the Swedish compendium.pdf. GENERATED by auto-translate.sc. */
       |  lazy val conceptHeadingEn: Map[String, String] = Map(
       |$entries
       |  )
       |""".stripMargin
  os.write.over(ScalaDir / "concept-headings-En-GENERATED.scala", out)

// ---------- main ----------
println(s"auto-translate: model=$MODEL seed=$SEED")
if !os.exists(CacheFile) then
  println("  no cache yet — bootstrapping from existing English (first run is a no-op)")
  bootstrap()
else loadCache()
println(s"  cache: ${cache.size} entries; authoritative: ${authoritative.size}")

genDataEn()
genHeadingsEn()
genConceptHeadingsEn()
saveCache()

println(s"  done. model calls: $modelCalls, fallbacks: $fallbacks, cache now: ${cache.size}")
if modelCalls == 0 then println("  (no model calls — everything came from authoritative/cache)")
