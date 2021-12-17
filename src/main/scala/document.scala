package shared

import org.scalajs.dom
import org.scalajs.dom.document
import muntabot.Muntabot

object Document:
  def appendButton(
      targetNode: dom.Node,
      text: String,
      disabled: Boolean = false
  )(
      action: => Unit
  ): dom.html.Button =
    val button = document.createElement("button").asInstanceOf[dom.html.Button]
    button.classList.add("button")
    button.appendChild(document.createTextNode(text))
    button.onclick = (e: dom.Event) => action
    button.disabled = disabled
    targetNode.appendChild(button)
    button

  def appendInput(
      targetNode: dom.Node,
      placeholder: String,
      id: String,
      disabled: Boolean = false
  )(
      onChange: => Unit
  ): dom.html.Input =
    val input = document.createElement("input").asInstanceOf[dom.html.Input]
    input.classList.add("input")
    input.id = id
    input.placeholder = placeholder
    input.oninput = (e: dom.Event) => onChange
    input.onchange = (e: dom.Event) => input.blur()
    input.disabled = disabled
    targetNode.appendChild(input)
    input

  def appendText(
      targetNode: dom.Node,
      tagName: String,
      text: String
  ): dom.Element =
    val textElement = document.createElement(tagName)
    textElement.innerText = text
    targetNode.appendChild(textElement)
    textElement

  def appendLinkToApp(
      targetNode: dom.Node,
      app: App,
      textContent: String
  ) =
    val linkParagraph = document.createElement("p")
    val linkToApp =
      document.createElement("a").asInstanceOf[dom.html.Anchor]
    linkToApp.textContent = textContent
    linkToApp.href = app.link
    linkParagraph.append(linkToApp)
    targetNode.appendChild(linkParagraph)
    linkToApp

  def appendHomeLink(targetNode: dom.Node) =
    Document.appendLinkToApp(targetNode, Muntabot, "Tillbaka hem")

  /** Deletes the element with the same id from the targetNode if it exists, and
    * then creates a new 'div' element and returns it.
    * 
    * @param id defaults to "container"
    * @param targetNode defaults to the document body
    */
  def appendDynamicContainer(
      id: String = "container",
      targetNode: dom.Node = document.body
  ): dom.Element =
    val oldContainerElement = document.getElementById(id)
    if (oldContainerElement != null) then
      targetNode.removeChild(oldContainerElement)

    val containerElement = document.createElement("div")
    containerElement.id = id
    targetNode.appendChild(containerElement)
    containerElement

  def pageNotFound(): Unit =
    val containerElement = appendDynamicContainer()
    appendHomeLink(containerElement)
    appendText(containerElement, "h1", "Oops! Page not found.")
