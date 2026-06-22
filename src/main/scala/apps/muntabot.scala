package muntabot

import shared._
import org.scalajs.dom
import org.scalajs.dom.document
import rehearsal.Rehearsal

object Muntabot extends App:
  val page = "#muntabot"
  val title = "muntabot"

  val MinWeek = 1
  val MaxWeek = 10

  var fromWeek = MinWeek
  var toWeek = MaxWeek

  /** A "Swedish/English" dropdown; on change it sets the language and re-renders. */
  def appendLanguageSelect(target: dom.Node)(reRender: => Unit): Unit =
    val sel = document.createElement("select").asInstanceOf[dom.html.Select]
    for (label, l) <- Seq("Swedish" -> Lang.Sv, "English" -> Lang.En) do
      val option = document.createElement("option").asInstanceOf[dom.html.Option]
      option.value = l.toString // "Sv" / "En"
      option.textContent = label
      option.selected = l == Lang.current
      sel.appendChild(option)
    sel.onchange = (e: dom.Event) =>
      Lang.current = if sel.value == "En" then Lang.En else Lang.Sv
      reRender
    val p = document.createElement("p")
    p.appendChild(sel)
    target.appendChild(p)

  def setupUI(): Unit =
    val t = Texts.current
    val containerElement = Document.appendDynamicContainer()

    val weekParagraph = Document.appendText(
      containerElement,
      "p",
      t.pickFromWeek
    )

    // Two dropdowns for a week range. The from-dropdown offers MinWeek..toWeek and
    // the to-dropdown offers fromWeek..MaxWeek, so picking either constrains the
    // other (from can never exceed to). Only valid weeks are selectable.
    val fromSelect =
      document.createElement("select").asInstanceOf[dom.html.Select]
    val toSelect =
      document.createElement("select").asInstanceOf[dom.html.Select]

    def fillWeeks(sel: dom.html.Select, lo: Int, hi: Int, selected: Int): Unit =
      sel.innerHTML = ""
      for w <- lo to hi do
        val option =
          document.createElement("option").asInstanceOf[dom.html.Option]
        option.value = w.toString // value stays the plain number (easy to read back)
        val theme = Week.titles.getOrElse(w, "")
        option.textContent = if theme.nonEmpty then s"$w. $theme" else w.toString
        option.selected = w == selected
        sel.appendChild(option)

    def refreshWeekRange(): Unit =
      fillWeeks(fromSelect, MinWeek, toWeek, fromWeek)
      fillWeeks(toSelect, fromWeek, MaxWeek, toWeek)

    fromSelect.onchange = (e: dom.Event) =>
      fromWeek = fromSelect.value.toIntOption.getOrElse(MinWeek)
      refreshWeekRange()
    toSelect.onchange = (e: dom.Event) =>
      toWeek = toSelect.value.toIntOption.getOrElse(MaxWeek)
      refreshWeekRange()

    refreshWeekRange()
    weekParagraph.appendChild(fromSelect)
    weekParagraph.appendChild(document.createTextNode(t.toWeek))
    weekParagraph.appendChild(toSelect)

    // Language selector right after the leading text; re-renders the page.
    appendLanguageSelect(containerElement) { setupUI() }

    val showText = document.createElement("pre").asInstanceOf[dom.html.Pre]
    showText.textContent = t.clickButtons

    val showHelp = document.createElement("p")

    // Render one question into showText/showHelp as structured HTML:
    // bold label, the question (code rendered via Markup), italic guidance.
    def renderQuestion(qt: Questions, q: String | (String, String)): Unit =
      qt match
        case Code =>
          q match
            case (desc, url) =>
              showText.innerHTML =
                s"<b>${Markup.escapeHtml(qt.questionToAsk)}:</b>\n" +
                  Markup.render(desc) + "\n\n" +
                  s"<em>${Markup.escapeHtml(qt.instruction)}</em>"
              val (href, linkText) = Compendium.link(url)
              showHelp.innerHTML =
                s"""${Markup.escapeHtml(Texts.current.readInCompendium)}<a href="$href" target="_blank">${Markup.escapeHtml(linkText)}</a>"""
            case _ => showText.textContent = q.toString
        case _ =>
          Compendium.linkForConcept(q) match
            case Some((href, linkText)) =>
              showHelp.innerHTML =
                s"""${Markup.escapeHtml(Texts.current.readInCompendium)}<a href="$href" target="_blank">${Markup.escapeHtml(linkText)}</a>"""
            case None => showHelp.innerHTML = ""
          showText.innerHTML =
            s"<b>${Markup.escapeHtml(qt.title)}:</b> " +
              s"${Markup.escapeHtml(qt.questionToAsk)}\n" +
              Markup.render(qt.show(q)) + qt.punctuation + "\n\n" +
              s"<em>${Markup.escapeHtml(qt.instruction)}</em>"

    for questionType <- Questions.types do
      val button = Document.appendButton(containerElement, questionType.title) {
        renderQuestion(
          questionType,
          questionType.pickAnyQuestion(fromWeek, toWeek, questionType)
        )
      }
      button.classList.add(questionType match
        case Concepts  => "btn-green"
        case Contrasts => "btn-yellow"
        case Code      => "btn-red"
        case _         => "button"
      )

    containerElement.appendChild(showText)
    containerElement.appendChild(showHelp)

    // Moved here from index.html so the question shows higher up (less scroll on mobile).
    Document.appendHtml(containerElement, "p", t.readAboutExamHtml)

    Document.appendHtml(containerElement, "p", t.aidsHtml)

    Document.appendLinkToApp(
      containerElement,
      Rehearsal,
      t.revealAll
    )
