import shared.App
import shared.Document
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.window
import muntabot.Muntabot

@main def run: Unit =
  document.addEventListener(
    "DOMContentLoaded",
    (e: dom.Event) => {
      if (document.location.hash == "") then
        document.location.hash = Muntabot.page
      val page = document.location.hash.split("/").head
      App.runApp(page)

      window.onhashchange =
        (e: dom.Event) => App.runApp(document.location.hash.split("/").head)
    }
  )
