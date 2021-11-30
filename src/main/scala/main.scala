package muntabot

import org.scalajs.dom
import org.scalajs.dom.document

@main def run: Unit = 
  document.addEventListener("DOMContentLoaded", (e: dom.Event) => setupUI())  

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

  appendButton(document.body, "Förklara koncept"){
    showText.textContent = Concepts.pickAnyQuestion
  }

  appendButton(document.body, "Jämför koncept"){
    showText.textContent = Contrasts.pickAnyQuestion
  }

  appendButton(document.body, "Skriv kod"){
    showText.textContent = Code.pickAnyQuestion
  }

  document.body.appendChild(showText)
