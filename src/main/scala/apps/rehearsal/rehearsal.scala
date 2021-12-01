package rehearsal

import shared._
import org.scalajs.dom
import org.scalajs.dom.document

object Rehearsal extends App:
  val page = "rehearsal"
  def run: Unit =
    perWeek()

  def appendText(targetNode: dom.Node, tagName: String, text: String) =
    val textElement = document.createElement(tagName)
    textElement.innerText = text
    targetNode.appendChild(textElement)

  def appendButton(targetNode: dom.Node, text: String)(action: => Unit): Unit =
    val b = document.createElement("button").asInstanceOf[dom.html.Button]
    b.classList.add("button")
    b.appendChild(document.createTextNode(text))
    b.onclick = (e: dom.Event) => action
    targetNode.appendChild(b)

  def setup(): dom.Element =
    val oldContainerElement = document.getElementById("container")
    if (oldContainerElement != null) then
      document.body.removeChild(oldContainerElement)

    val containerElement = document.createElement("div")
    containerElement.id = "container"
    document.body.appendChild(containerElement)

    appendButton(containerElement, "Per vecka") {
      perWeek()
    }

    appendButton(containerElement, "Per kategori") {
      perCategory()
    }

    containerElement

  def perCategory(): Unit =
    val containerElement = setup()

    appendText(containerElement, "h2", "Per kategori")

    var number = 1

    appendText(containerElement, "h3", "Förklara koncept")
    appendText(containerElement, "p", s"Instruktion: ${Concepts.postQuestion}")
    for concept <- Concepts.all do
      appendText(
        containerElement,
        "p",
        s"$number. ${Concepts.preQuestion} \"${concept}\"?"
      )
      number += 1

    appendText(containerElement, "h3", "Jämför koncept")
    appendText(containerElement, "p", s"Instruktion: ${Contrasts.postQuestion}")
    for contrast <- Contrasts.all do
      appendText(
        containerElement,
        "p",
        s"$number. ${Contrasts.preQuestion} \"${contrast}\"?"
      )
      number += 1

    appendText(containerElement, "h3", "Skriv kod")
    appendText(containerElement, "p", s"Instruktion: ${Code.postQuestion}")
    for code <- Code.all do
      appendText(
        containerElement,
        "p",
        s"$number. ${Code.preQuestion}: \n\n${code}"
      )
      number += 1

  def perWeek(): Unit =
    val containerElement = setup()
    var weeks = terms.map(_._1).distinct
    var number = 1
    for week <- weeks do
      val thisWeek = terms.filter(_._1 == week)
      appendText(
        containerElement,
        "h2",
        s"Vecka ${thisWeek(0)._1.w.toString()}"
      )
      for term <- thisWeek do
        if term._2.isInstanceOf[Concepts] then
          appendText(
            containerElement,
            "h3",
            s"Förklara koncept"
          )
          val concepts = term._2.asInstanceOf[Concepts]
          for concept <- concepts do
            appendText(
              containerElement,
              "p",
              s"$number. ${Concepts.preQuestion} \"${concept}\"?"
            )
            number += 1
          appendText(
            containerElement,
            "p",
            Concepts.postQuestion
          )
        else if term._2.isInstanceOf[Contrasts] then
          appendText(
            containerElement,
            "h3",
            s"Jämför koncept"
          )
          val contrasts = term._2.asInstanceOf[Contrasts]
          for contrast <- contrasts do
            appendText(
              containerElement,
              "p",
              s"$number. ${Contrasts.preQuestion} \"${contrast}\"?"
            )
            number += 1
          appendText(
            containerElement,
            "p",
            Contrasts.postQuestion
          )
        else if term._2.isInstanceOf[Code] then
          appendText(
            containerElement,
            "h3",
            s"Skriv kod"
          )
          val code = term._2.asInstanceOf[Code]
          for codeString <- code do
            appendText(
              containerElement,
              "p",
              s"$number. ${Code.preQuestion}: \n\n${codeString}"
            )
            number += 1
          appendText(
            containerElement,
            "p",
            Code.postQuestion
          )
