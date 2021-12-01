package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document

object Muntabot extends App:
  val page = "muntabot"

  def run: Unit =
    setupUI()

  def appendPar(targetNode: dom.Node, text: String): dom.html.Paragraph =
    val parNode = document.createElement("p").asInstanceOf[dom.html.Paragraph]
    parNode.textContent = text
    targetNode.appendChild(parNode)
    parNode

  def appendButton(targetNode: dom.Node, text: String)(action: => Unit): Unit =
    val b = document.createElement("button").asInstanceOf[dom.html.Button]
    b.classList.add("button")
    b.appendChild(document.createTextNode(text))
    b.onclick = (e: dom.Event) => action
    targetNode.appendChild(b)

  def setupUI(): Unit =
    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka så får du en uppgift."

    appendButton(document.body, "Förklara koncept") {
      showText.textContent = Concepts.withQuestion(Concepts.pickAnyQuestion)
    }

    appendButton(document.body, "Jämför koncept") {
      showText.textContent = Contrasts.withQuestion(Contrasts.pickAnyQuestion)
    }

    appendButton(document.body, "Skriv kod") {
      showText.textContent = Code.withQuestion(Code.pickAnyQuestion)
    }

    document.body.appendChild(showText)
