package shared

import scala.scalajs.js.URIUtils.decodeURIComponent

/** Builds robust links into compendium.pdf.
  *
  * `#nameddest=` is unreliable across browsers (Firefox/pdf.js and Safari often
  * ignore it), so we link via `#page=<physicalPage>` (works everywhere), using
  * the generated `headings` table (see FindHeadings.scala). The link text shows
  * "number heading (s. printedPage)".
  *
  * The compendium is in Swedish, so the link ALWAYS opens the Swedish PDF and
  * the page geometry always comes from the Swedish `headings` table. In English
  * mode only the shown heading text is translated (via headingTranslateSvEn),
  * and concept lookups use the English keys (conceptHeadingEn).
  */
object Compendium:

  val base = "https://fileadmin.cs.lth.se/pgk/compendium.pdf"

  /** heading -> (sectionNumber, printedPage, physicalPage).
    * `headings` is sorted by page; for a non-unique heading we keep the FIRST
    * (lowest-page) occurrence — the body section rather than a later repeat. */
  lazy val infoOf: Map[String, (String, Int, Int)] =
    headings.reverse.map((h, num, printed, phys) => h -> (num, printed, phys)).toMap

  /** English display title for a Swedish heading (falls back to the Swedish). */
  def shownHeading(svHeading: String): String =
    if Lang.isEn then headingTranslateSvEn.getOrElse(svHeading, svHeading) else svHeading

  /** Robust `#page=` href + "number heading (s./p. page)" text for a known
    * (Swedish) heading; the heading text is shown translated in English mode. */
  def linkForHeading(svHeading: String): Option[(String, String)] =
    infoOf.get(svHeading).map: (number, printed, physical) =>
      val numberPrefix = if number.nonEmpty then s"$number " else ""
      (s"$base#page=$physical",
       s"$numberPrefix${shownHeading(svHeading)} (${Texts.current.pageAbbrev} $printed)")

  /** Convert a data `...#nameddest=<heading>` url to a robust #page link + text.
    * Falls back to the given url if the heading is unknown. (Used by Code tasks.) */
  def link(nameddestUrl: String): (String, String) =
    val marker = "#nameddest="
    val i = nameddestUrl.indexOf(marker)
    if i < 0 then (nameddestUrl, Texts.current.showInfo)
    else
      val heading = decodeURIComponent(nameddestUrl.substring(i + marker.length))
      linkForHeading(heading).getOrElse(
        (nameddestUrl, s"${shownHeading(heading)}${Texts.current.showInBookSuffix}")
      )

  /** Lookup key for a Concepts term / Contrasts pair (matches conceptHeading keys). */
  def conceptKey(q: String | (String, String)): String =
    q match
      case (a, b)    => a.replace("`", "").trim + " | " + b.replace("`", "").trim
      case s: String => s.replace("`", "").trim

  /** Robust compendium link for a Förklara/Jämför-koncept question, if mapped
    * (most relevant heading in the concept's own chapter; see conceptHeading). */
  def linkForConcept(q: String | (String, String)): Option[(String, String)] =
    val map = if Lang.isEn then conceptHeadingEn else conceptHeading
    map.get(conceptKey(q)).flatMap(linkForHeading)
