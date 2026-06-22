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
  var containerElement: dom.Node = null
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
    containerElement = setupCommonComponents()
    runSubpage()

  def runSubpage() =
    if (currentSubpage == "week") then perWeek()
    else if (currentSubpage == "category") then perCategory()

  def searchInput =
    document.getElementById("search-input").asInstanceOf[dom.html.Input]

  def setupCommonComponents(): dom.Element =
    val t = Texts.current
    val containerElement = Document.appendDynamicContainer()

    Document.appendLinkToApp(containerElement, Muntabot, t.pickOneAtATime)

    Document.appendText(
      containerElement,
      "h1",
      t.allQuestions
    )

    // Language selector right after the leading text; re-renders the page.
    Muntabot.appendLanguageSelect(containerElement) { setupUI() }

    Document.appendInput(containerElement, t.search, "search-input") {
      searchTerm = searchInput.value
      if searchTerm.length > 0 then
        searchView()
        searchInput.value = searchTerm
      else
        searchables.clear
        runSubpage()

    }

    Document.appendButton(
      containerElement,
      t.perWeek,
      disabled = currentSubpage == "week"
    ) {
      searchTerm = ""
      setSubpage("week")
      perWeek()
    }

    Document.appendButton(
      containerElement,
      t.perCategory,
      disabled = currentSubpage == "category"
    ) {
      searchTerm = ""
      setSubpage("category")
      perCategory()
    }

    Document.appendText(
      containerElement,
      "p",
      t.rehearsalIntro
    )

    containerElement

  def searchView(): Unit =
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    Document.appendText(contentElement, "h2", Texts.current.searchResults)
    for searchable <- searchables do
      if (searchable.toLowerCase.contains(searchTerm.toLowerCase)) then
        Document.appendHtml(
          contentElement,
          "p",
          Markup.render(searchable)
        )

  def perCategory(): Unit =
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    Document.appendText(contentElement, "h2", Texts.current.perCategory)

    var number = 1
    for questionType <- Questions.types do
      Document.appendText(contentElement, "h3", questionType.title)
      for question <- questionType.all do
        // full prompt kept for search; the displayed line shows just the item
        // (the prompt is implied by the category heading above).
        searchables.append(s"$number. ${questionType.getShortQuestion(question)}")
        Document.appendHtml(
          contentElement,
          "p",
          Markup.render(s"$number. ${questionType.shortItem(question)}")
        )
        number += 1

  def perWeek(): Unit =
    val t = Texts.current
    val contentElement =
      Document.appendDynamicContainer("content", containerElement)
    var weeks = terms.map(_._1).distinct
    var number = 1
    for week <- weeks do
      val thisWeek = terms.filter(_._1 == week)
      val w = thisWeek(0)._1.w
      Document.appendText(
        contentElement,
        "h2",
        s"${t.weekWord} $w: ${Week.title(thisWeek(0)._1)}"
      )
      for term <- thisWeek do
        Document.appendText(contentElement, "h3", term._2.title)
        for question <- term._2 do
          searchables.append(s"$number. ${term._2.getShortQuestion(question)}")
          Document.appendHtml(
            contentElement,
            "p",
            Markup.render(s"$number. ${term._2.shortItem(question)}")
          )
          number += 1
