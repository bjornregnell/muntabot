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
    p.textContent = "Hjälpmedel: papper, penna, REPL, snabbreferens."
    containerElement.appendChild(p)

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.className = "question-box"
    showText.textContent = "Klicka på knapparna så får du en uppgift."

    // Own container for the buttons since they should have default flex-direction
    val buttonContainer =
      document.createElement("div").asInstanceOf[dom.html.Div]

    for questionType <- Questions.types do
      Document.appendButton(buttonContainer, questionType.title) {
        showText.textContent =
          questionType.getQuestion(questionType.pickAnyQuestion)
      }

    containerElement.appendChild(buttonContainer)

    containerElement.appendChild(showText)
