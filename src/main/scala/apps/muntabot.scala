package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"
  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Alla frågor"
    )

    val p = dom.document.createElement("p").asInstanceOf[dom.html.Element]
    p.textContent = "adbiod: papper, penna, REPL, snabbreferens."
    p.className = "test"
    containerElement.appendChild(p)

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka på knapparna så får du en uppgift."

    for questionType <- Questions.types do
      Document.appendButton(containerElement, questionType.title) {
        showText.textContent =
          questionType.getQuestion(questionType.pickAnyQuestion)
      }

    containerElement.appendChild(showText)
