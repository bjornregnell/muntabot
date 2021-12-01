package shared

import org.scalajs.dom
import org.scalajs.dom.document

object Document:
  def appendButton(targetNode: dom.Node, text: String)(
      action: => Unit
  ): dom.html.Button =
    val button = document.createElement("button").asInstanceOf[dom.html.Button]
    button.classList.add("button")
    button.appendChild(document.createTextNode(text))
    button.onclick = (e: dom.Event) => action
    targetNode.appendChild(button)
    button

  def appendText(
      targetNode: dom.Node,
      tagName: String,
      text: String
  ): dom.Element =
    val textElement = document.createElement(tagName)
    textElement.innerText = text
    targetNode.appendChild(textElement)
    textElement
