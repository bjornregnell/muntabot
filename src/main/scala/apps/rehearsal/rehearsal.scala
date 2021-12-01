package rehearsal

import shared._
import org.scalajs.dom
import org.scalajs.dom.document

object Rehearsal extends App:
  val page = "rehearsal"
  def run: Unit =
    perWeek()

  def setup(): dom.Element =
    val oldContainerElement = document.getElementById("container")
    if (oldContainerElement != null) then
      document.body.removeChild(oldContainerElement)

    val containerElement = document.createElement("div")
    containerElement.id = "container"
    document.body.appendChild(containerElement)

    Document.appendButton(containerElement, "Per vecka") {
      perWeek()
    }

    Document.appendButton(containerElement, "Per kategori") {
      perCategory()
    }

    containerElement

  def perCategory(): Unit =
    val containerElement = setup()

    Document.appendText(containerElement, "h2", "Per kategori")

    var number = 1
    for questionType <- Questions.types do
      Document.appendText(containerElement, "h3", questionType.title)
      Document.appendText(
        containerElement,
        "p",
        s"Instruktion: ${Concepts.explanation}"
      )
      for question <- questionType.all do
        Document.appendText(
          containerElement,
          "p",
          s"$number. ${questionType.getShortQuestion(question)}"
        )
        number += 1

  def perWeek(): Unit =
    val containerElement = setup()
    var weeks = terms.map(_._1).distinct
    var number = 1
    for week <- weeks do
      val thisWeek = terms.filter(_._1 == week)
      Document.appendText(
        containerElement,
        "h2",
        s"Vecka ${thisWeek(0)._1.w.toString()}"
      )
      for term <- thisWeek do
        Document.appendText(containerElement, "h3", term._2.title)
        for question <- term._2 do
          Document.appendText(
            containerElement,
            "p",
            s"$number. ${term._2.getShortQuestion(question)}"
          )
          number += 1
        Document.appendText(
          containerElement,
          "p",
          term._2.explanation
        )
