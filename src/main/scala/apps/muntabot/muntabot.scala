package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"
  def setupUI(): Unit =
    val containerElement = Document.setupContainer()

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Alla frågor"
    )

    Document.appendText(
      containerElement,
      "p",
      "Hjälpmedel: papper, penna, REPL, snabbreferens."
    )
    Document.appendText(
      containerElement,
      "p",
      "Alla labbbar ska vara godkända innan muntliga provet."
    )

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka på knapparna så får du en uppgift."

    for questionType <- Questions.types do
      Document.appendButton(containerElement, questionType.title) {
        showText.textContent =
          questionType.getQuestion(questionType.pickAnyQuestion)
      }

    containerElement.appendChild(showText)
