package rehearsal

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.window
import muntabot.Muntabot

object Rehearsal extends App:
  type Subpage = "week" | "category"
  val page = "#rehearsal"
  val title = "Alla frågor från muntabot"

  var searchTerm = ""
  val searchables = collection.mutable.Buffer[String]()

  private var _currentSubpage: Subpage = "week"

  override def currentSubpage = _currentSubpage

  def setSubpage(subpage: Subpage) =
    document.location.hash = s"$page/$subpage"
    _currentSubpage = subpage

  def setupUI(): Unit =
    try {
      _currentSubpage =
        document.location.hash.split("/")(1).asInstanceOf[Subpage]
    } catch error => {}
    runSubpage()

  def runSubpage() =
    if (searchTerm.length > 0) then searchView(setupCommonComponents())
    else
      searchables.clear
      if (currentSubpage == "week") then perWeek(setupCommonComponents())
      else if (currentSubpage == "category") then
        perCategory(setupCommonComponents())

  def searchInput =
    document.getElementById("search-input").asInstanceOf[dom.html.Input]

  def setupCommonComponents(): dom.Element =
    val containerElement = Document.setupContainer()

    Document.appendLinkToApp(containerElement, Muntabot, "Slumpa frågor")

    Document.appendText(
      containerElement,
      "h1",
      "Alla frågor från muntabot"
    )

    Document.appendInput(containerElement, "Sök", "search-input") {
      searchTerm = searchInput.value
      runSubpage()
    }
    if (searchTerm.length > 0) then
      searchInput.value = searchTerm
      searchInput.focus()

    Document.appendButton(
      containerElement,
      "Per vecka",
      disabled = currentSubpage == "week"
    ) {
      searchTerm = ""
      setSubpage("week")
      runSubpage()
    }

    Document.appendButton(
      containerElement,
      "Per kategori",
      disabled = currentSubpage == "category"
    ) {
      searchTerm = ""
      setSubpage("category")
      runSubpage()
    }

    Document.appendText(
      containerElement,
      "p",
      "Svara på frågor och gör koduppdrag nedan. Ge även exempel på normal och felaktig/konstig användning. För varje koduppdrag: skriv även testfall som testar din kod. Hjälpmedel: papper, penna, REPL, snabbreferens."
    )


    containerElement

  def searchView(containerElement: dom.Element): Unit =
    Document.appendText(containerElement, "h2", "Sökresultat")
    for searchable <- searchables do
      if (searchable.toLowerCase.contains(searchTerm.toLowerCase)) then
        Document.appendText(
          containerElement,
          "p",
          searchable
        )

  def perCategory(containerElement: dom.Element): Unit =
    Document.appendText(containerElement, "h2", "Per kategori")

    var number = 1
    for questionType <- Questions.types do
      Document.appendText(containerElement, "h3", questionType.title)
      for question <- questionType.all do
        val questionString =
          s"$number. ${questionType.getShortQuestion(question)}"
        searchables.append(questionString)
        Document.appendText(
          containerElement,
          "p",
          questionString
        )
        number += 1

  def perWeek(containerElement: dom.Element): Unit =
    var weeks = terms.map(_._1).distinct
    var number = 1
    for week <- weeks do
      val thisWeek = terms.filter(_._1 == week)
      val w = thisWeek(0)._1.w
      Document.appendText(
        containerElement,
        "h2",
        s"Vecka $w: ${Week.title(thisWeek(0)._1)}"
      )
      for term <- thisWeek do
        Document.appendText(containerElement, "h3", term._2.title)
        for question <- term._2 do
          val questionString = s"$number. ${term._2.getShortQuestion(question)}"
          searchables.append(questionString)
          Document.appendText(
            containerElement,
            "p",
            questionString
          )
          number += 1
