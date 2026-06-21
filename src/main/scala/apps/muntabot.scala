package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"

  val MaxWeek = 10

  var untilWeek = MaxWeek

  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    val weekParagraph = Document.appendText(
      containerElement,
      "p",
      "Slumpa en fråga i taget till och med läsvecka: "
    )

    // Dropdown of valid weeks only (1..MaxWeek) — no invalid input possible.
    val weekSelect =
      document.createElement("select").asInstanceOf[dom.html.Select]
    weekSelect.id = "week-input"
    for w <- 1 to MaxWeek do
      val option =
        document.createElement("option").asInstanceOf[dom.html.Option]
      option.value = w.toString
      option.textContent = w.toString
      option.selected = w == untilWeek
      weekSelect.appendChild(option)
    weekSelect.onchange = (e: dom.Event) =>
      untilWeek = weekSelect.value.toIntOption.getOrElse(MaxWeek)
    weekParagraph.appendChild(weekSelect)

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka på knapparna ovan så får du en uppgift."

    val showHelp = document.createElement("a").asInstanceOf[dom.html.Anchor]
    showHelp.href = ""
    showHelp.textContent = ""
    showHelp.target = "_blank" // Opens in new window

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
              showHelp.href = href
              showHelp.textContent = linkText
            case _ => showText.textContent = q.toString
        case _ =>
          showHelp.textContent = ""
          showHelp.href = ""
          showText.innerHTML =
            s"<b>${Markup.escapeHtml(qt.title)}:</b> " +
              s"${Markup.escapeHtml(qt.questionToAsk)}\n" +
              Markup.render(qt.show(q)) + qt.punctuation + "\n\n" +
              s"<em>${Markup.escapeHtml(qt.instruction)}</em>"

    for questionType <- Questions.types do
      Document.appendButton(containerElement, questionType.title) {
        renderQuestion(
          questionType,
          questionType.pickAnyQuestion(untilWeek, questionType)
        )
      }

    Document.appendHtml(
      containerElement,
      "p",
      "<b>Hjälpmedel:</b> papper, penna, REPL, snabbreferens."
    )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Spojla alla frågor"
    )
