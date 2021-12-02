package shared

import org.scalajs.dom.document
import muntabot.Muntabot
import rehearsal.Rehearsal

val apps: Array[App] = Array(Muntabot, Rehearsal)

abstract class App:
  val page: String
  val title: String
  def setupUI(): Unit
  def run: Unit =
    document.title = title
    setupUI()
object App:
  def runApp(page: String): Unit =
    var notFound = true
    for app <- apps do
      if page == app.page then
        notFound = false
        app.run

    if (notFound) then Document.pageNotFound()
