package shared

/** Lightweight inline markup for question text.
  *
  * Convention in the data (data.scala): wrap actual code in `backticks`.
  * Rendering: backtick spans become <code> (monospace via style.css); any
  * Scala 3 keyword inside a code span is shown in bold. Prose outside
  * backticks stays in the normal (non-monospace) font.
  */
object Markup:

  /** Scala 3 keywords (hard + soft), copied from
    * introprog/compendium/compendium.cls (\lstdefinelanguage{Scala}). */
  val scalaKeywords: Set[String] = Set(
    // hard keywords
    "abstract", "case", "catch", "class", "def",
    "do", "else", "enum", "export", "extends", "false", "final", "finally",
    "for", "given", "if", "implicit", "import", "lazy", "match",
    "new", "null", "object", "override", "package",
    "private", "protected", "return", "sealed",
    "super", "then", "throw", "trait", "true", "try",
    "type", "val", "var", "while", "with", "yield",
    // soft keywords
    "as", "derives", "end", "extension", "infix", "inline", "opaque", "open",
    "transparent", "using"
  )

  def escapeHtml(s: String): String =
    s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")

  val word = "[A-Za-z_][A-Za-z0-9_]*".r

  /** Bold any Scala keyword token inside an (already HTML-escaped) code span. */
  def boldKeywords(code: String): String =
    word.replaceAllIn(code, m =>
      val w = m.group(0)
      if scalaKeywords(w) then s"<b>$w</b>" else w
    )

  /** Render raw question text (with `backtick` code spans) to safe HTML. */
  def render(raw: String): String =
    val parts = escapeHtml(raw).split("`", -1)
    parts.zipWithIndex
      .map((p, i) => if i % 2 == 1 then s"<code>${boldKeywords(p)}</code>" else p)
      .mkString
