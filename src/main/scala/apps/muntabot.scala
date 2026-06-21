package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"

  val MinWeek = 1
  val MaxWeek = 10

  var fromWeek = MinWeek
  var toWeek = MaxWeek

  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    val weekParagraph = Document.appendText(
      containerElement,
      "p",
      "Slumpa en fråga i taget från läsvecka "
    )

    // Two dropdowns for a week range. The from-dropdown offers MinWeek..toWeek and
    // the to-dropdown offers fromWeek..MaxWeek, so picking either constrains the
    // other (from can never exceed to). Only valid weeks are selectable.
    val fromSelect =
      document.createElement("select").asInstanceOf[dom.html.Select]
    val toSelect =
      document.createElement("select").asInstanceOf[dom.html.Select]

    def fillWeeks(sel: dom.html.Select, lo: Int, hi: Int, selected: Int): Unit =
      sel.innerHTML = ""
      for w <- lo to hi do
        val option =
          document.createElement("option").asInstanceOf[dom.html.Option]
        option.value = w.toString
        option.textContent = w.toString
        option.selected = w == selected
        sel.appendChild(option)

    def refreshWeekRange(): Unit =
      fillWeeks(fromSelect, MinWeek, toWeek, fromWeek)
      fillWeeks(toSelect, fromWeek, MaxWeek, toWeek)

    fromSelect.onchange = (e: dom.Event) =>
      fromWeek = fromSelect.value.toIntOption.getOrElse(MinWeek)
      refreshWeekRange()
    toSelect.onchange = (e: dom.Event) =>
      toWeek = toSelect.value.toIntOption.getOrElse(MaxWeek)
      refreshWeekRange()

    refreshWeekRange()
    weekParagraph.appendChild(fromSelect)
    weekParagraph.appendChild(document.createTextNode(" till och med läsvecka "))
    weekParagraph.appendChild(toSelect)

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka på knapparna ovan så får du en uppgift."

    val showHelp = document.createElement("p")

    // Render one question into showText/showHelp as structured HTML:
    // bold label, the question (code rendered via Markup), italic guidance.
    def renderQuestion(qt: Questions, q: String | (String, String)): Unit =
      qt match
        case Code =>
          q match
            case (desc, url) =>
              showText.innerHTML =
                s"<b>${Markup.escapeHtml(qt.questionToAsk)}:</b>\n" +
                  Markup.render(desc) + "\n\n" +
                  s"<em>${Markup.escapeHtml(qt.instruction)}</em>"
              val (href, linkText) = Compendium.link(url)
              showHelp.innerHTML =
                s"""Läs i kompendiet om: <a href="$href" target="_blank">${Markup.escapeHtml(linkText)}</a>"""
            case _ => showText.textContent = q.toString
        case _ =>
          Compendium.linkForConcept(q) match
            case Some((href, linkText)) =>
              showHelp.innerHTML =
                s"""Läs i kompendiet om: <a href="$href" target="_blank">${Markup.escapeHtml(linkText)}</a>"""
            case None => showHelp.innerHTML = ""
          showText.innerHTML =
            s"<b>${Markup.escapeHtml(qt.title)}:</b> " +
              s"${Markup.escapeHtml(qt.questionToAsk)}\n" +
              Markup.render(qt.show(q)) + qt.punctuation + "\n\n" +
              s"<em>${Markup.escapeHtml(qt.instruction)}</em>"

    for questionType <- Questions.types do
      val button = Document.appendButton(containerElement, questionType.title) {
        renderQuestion(
          questionType,
          questionType.pickAnyQuestion(fromWeek, toWeek, questionType)
        )
      }
      button.classList.add(questionType match
        case Concepts  => "btn-green"
        case Contrasts => "btn-yellow"
        case Code      => "btn-red"
        case _         => "button"
      )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)

    // Moved here from index.html so the question shows higher up (less scroll on mobile).
    Document.appendHtml(
      containerElement,
      "p",
      "Läs om muntan i " +
        "<a href=\"https://fileadmin.cs.lth.se/pgk/compendium.pdf\">kompendiet;-1.7:anvisningar;muntligt prov</a>" +
        " OCH " +
        "<a href=\"https://fileadmin.cs.lth.se/pgk/lect-w12.pdf\">föreläsn. w12</a>"
    )

    Document.appendHtml(
      containerElement,
      "p",
      "Hjälpmedel vid skarp munta: papper, penna, " +
        "<a href=\"https://fileadmin.cs.lth.se/pgk/quickref.pdf\">snabbreferensen</a>."
    )

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Spojla alla frågor"
    )
