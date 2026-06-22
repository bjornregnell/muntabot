package shared

/** UI language. Swedish is the default. */
enum Lang:
  case Sv, En

object Lang:
  var current: Lang = Sv
  def isEn: Boolean = current == En

/** The quest data for the currently selected language. */
def terms: Seq[(Week, Concepts | Contrasts | Code)] =
  if Lang.isEn then termsEn else termsSv
