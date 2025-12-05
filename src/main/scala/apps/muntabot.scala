package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"

  val MaxWeek = 10

  var untilWeek = MaxWeek

  def weekInput =
    document.getElementById("week-input").asInstanceOf[dom.html.Input]

  def setupUI(): Unit =
    val containerElement = Document.appendDynamicContainer()

    Document.appendText(
      containerElement,
      "p",
      "Slumpa en fråga i taget till och med läsvecka:"
    )

    Document.appendInput(containerElement, "Ange heltal (1-10)", "week-input") {
      val newInput = weekInput.value

      if newInput == "" then untilWeek = MaxWeek
      else
        val toWeek =
          newInput.trim.filter(_.isDigit).toIntOption.getOrElse(MaxWeek)
        untilWeek = MaxWeek.min(toWeek)
        weekInput.value = untilWeek.toString
      end if

    }

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      "Spojla alla frågor"
    )

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = "Klicka på knapparna ovan så får du en uppgift."

    val showHelp = document.createElement("a").asInstanceOf[dom.html.Anchor]
    showHelp.href = ""
    showHelp.textContent = ""
    showHelp.target = "_blank" // Opens in new window

    for questionType <- Questions.types do
      Document.appendButton(containerElement, questionType.title) {
        if questionType == Code then
          val contents = questionType
            .getQuestion(
              questionType.pickAnyQuestion(untilWeek, questionType)
            )
            .split(
              "39d2c101a1a9746c5e54da6ba6a4ed48"
            ) // Random hash to split at, see model.scala

          showText.textContent = contents(0)

          if contents.length > 1 then
            showHelp.href = contents(1)
            showHelp.textContent = "Visa information från kursboken"
          else
            showHelp.textContent = ""
            showHelp.href = ""
        else
          showHelp.textContent = ""
          showHelp.href = ""

          showText.textContent = questionType.getQuestion(
            questionType.pickAnyQuestion(untilWeek, questionType)
          )

          if untilWeek < 1 || untilWeek > MaxWeek then
            untilWeek = MaxWeek
            weekInput.value = untilWeek.toString

          weekInput.value = untilWeek.toString
      }

    Document.appendText(
      containerElement,
      "p",
      "Hjälpmedel: papper, penna, REPL, snabbreferens."
    )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)
