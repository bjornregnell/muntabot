package muntabot

lazy val terms = Seq[(Week, Concepts | Contrasts | Code)](
  Week(1) -> Concepts("funktion", "while-sats", "for-sats", "stränginterpolator", "tilldelning"),
  Week(1) -> Contrasts("map" -> "foreach", "for-uttryck" -> "for-sats", "Vector" -> "Array"),
  Week(2) -> Concepts("for-uttryck", "samlingsmetoden map", "indexering", "kontrollstruktur"),
  Week(2) -> Code("en funktion som ger minsta talet i en sekvens,\nutan att använda inbyggda min")
)