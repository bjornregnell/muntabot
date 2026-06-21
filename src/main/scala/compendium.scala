package shared

import scala.scalajs.js.URIUtils.decodeURIComponent

/** Builds robust links into compendium.pdf.
  *
  * The data (data.scala) stores `...compendium.pdf#nameddest=<heading>` links.
  * `#nameddest=` is unreliable across browsers (Firefox/pdf.js and Safari
  * often ignore it), so we convert to `#page=<physicalPage>`, which works
  * everywhere, using the generated `headings` table (see FindHeadings.scala).
  */
object Compendium:

  /** heading -> (sectionNumber, printedPage, physicalPage).
    * `headings` is sorted by page; for a non-unique heading we keep the FIRST
    * (lowest-page) occurrence — the body section rather than a later repeat. */
  lazy val infoOf: Map[String, (String, Int, Int)] =
    headings.reverse.map((h, num, printed, phys) => h -> (num, printed, phys)).toMap

  /** Given a `...#nameddest=<heading>` url, return a robust `#page=N` href and a
    * descriptive link text "number heading (s. page)". Falls back to the given
    * url if the heading is unknown. */
  def link(nameddestUrl: String): (String, String) =
    val marker = "#nameddest="
    val i = nameddestUrl.indexOf(marker)
    if i < 0 then (nameddestUrl, "Visa information från kursboken")
    else
      val base = nameddestUrl.substring(0, i)
      val heading = decodeURIComponent(nameddestUrl.substring(i + marker.length))
      infoOf.get(heading) match
        case Some((number, printed, physical)) =>
          val numberPrefix = if number.nonEmpty then s"$number " else ""
          (s"$base#page=$physical", s"$numberPrefix$heading (s. $printed)")
        case None =>
          (nameddestUrl, s"$heading – visa i kursboken")
