package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document

object Muntabot extends App:
  val page = "muntabot"

  def run: Unit =
    setupUI()

  def setupUI(): Unit =
    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka så får du en uppgift."

    for questionType <- Questions.types do
      Document.appendButton(document.body, questionType.title) {
        showText.textContent =
          questionType.getQuestion(questionType.pickAnyQuestion)
      }

    document.body.appendChild(showText)
