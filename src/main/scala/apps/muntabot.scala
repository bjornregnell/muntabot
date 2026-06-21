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

  def weekInput =
    document.getElementById("week-input").asInstanceOf[dom.html.Input]

  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    Document.appendText(
      containerElement,
      "p",
      "Slumpa en fråga i taget till och med läsvecka:"
    )

    Document.appendInput(containerElement, "Ange heltal (1-10)", "week-input") {
      val newInput = weekInput.value

      if newInput == "" then untilWeek = MaxWeek
      else
        val toWeek =
          newInput.trim.filter(_.isDigit).toIntOption.getOrElse(MaxWeek)
        untilWeek = MaxWeek.min(toWeek)
        weekInput.value = untilWeek.toString
      end if

    }

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Spojla alla frågor"
    )

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
              showHelp.href = url
              showHelp.textContent = "Visa information från kursboken"
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
        if untilWeek < 1 || untilWeek > MaxWeek then untilWeek = MaxWeek
        weekInput.value = untilWeek.toString
      }

    Document.appendHtml(
      containerElement,
      "p",
      "<b>Hjälpmedel:</b> papper, penna, REPL, snabbreferens."
    )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)
