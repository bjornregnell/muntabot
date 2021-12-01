import muntabot.Muntabot
import rehearsal.Rehearsal
import shared.App
import org.scalajs.dom
import org.scalajs.dom.document

val apps: Array[App] = Array(Muntabot, Rehearsal)

@main def run: Unit =
  document.addEventListener(
    "DOMContentLoaded",
    (e: dom.Event) => {
      val page = document.body.getAttribute("page")
      for app <- apps do if page == app.page then app.run
    }
  )
