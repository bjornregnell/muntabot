package shared

lazy val terms = Seq[(Week, Concepts | Contrasts | Code)](
  Week(1) -> Concepts("funktion", "while-sats", "for-sats", "stränginterpolator", "tilldelning", "kompilator", "grundtyper", "booleskt värde"),
  Week(1) -> Contrasts("map" -> "foreach", "for-uttryck" -> "for-sats", "Vector" -> "Array", "while" -> "for-do", "typ" -> "värde", "if-uttryck" -> "if-sats", "def" -> "val", "var" -> "val"),
  Week(2) -> Concepts("for-uttryck", "samlingsmetoden map", "indexering", "kontrollstruktur", "huvudprogram"),
  Week(2) -> Code("en funktion som ger minsta heltalet i en heltalssekvens,\nutan att använda inbyggda min"),
  Week(2) -> Code("en funktion som ger största heltalet i en heltalssekvens,\nutan att använda inbyggda max"),
  Week(2) -> Code("två förändringsbara variabler som först initialiseras\nmed två olika helatal och sedan byter värde med varandra"),
  Week(2) -> Code("en funktion som summerar alla heltal i en sekvens,\nutan att använda inbyggda sum"),
  Week(2) -> Contrasts("pseudokod" -> "exekverbar implementation", "parameter" -> "argument", "returtyp" -> "parametertyp"),
  Week(3) -> Concepts("anonym funktion", "predikat", "äkta funktion", "aktiveringspost", "anropsstacken", "default-argument",  "returtyp"),
  Week(4) -> Concepts("singelobjekt", "paket", "privat medlem", "namnrymd", "metoden apply", "tupel", "import", "punktnotation"),
  Week(4) -> Contrasts("värdeanrop" -> "namnanrop", "val" -> "lazy val", "def" -> "lazy val"),
  Week(5) -> Concepts("klass", "attribut", "fabriksmetod", "instansiering", "tillstånd", "klassparameter", "kompanjonsobjekt"),
  Week(5) -> Contrasts("funktion" -> "metod", "klass" -> "case-klass", "singelobjekt" -> "klass", "getter" -> "setter", "referenslikhet" -> "innehållslikhet"),
  Week(6) -> Concepts("mönstermatchning", "undantag", "konstruktormönster", "Option", "Try"),
  Week(7) -> Contrasts("ArrayBuffer" -> "Array", "förändringsbar samling" -> "oföränderlig samling"),
  Week(7) -> Code("en funktion som linjärsöker efter en instans av klassen Person\nmed ett visst efternamn i en osorterad sekvens av personer\nmed hjälp av en while (och inte find/indexOf)"),
  Week(7) -> Concepts("matris", "nästlad struktur", "typparameter", "generisk funktion"),
  Week(7) -> Code("ett program som registrerar 1000 tärningskast i en Array"),
  Week(7) -> Code("funktion copy som kopierar en Array med heltal element för element till en ny Array,\nanvänd en while-sats i implementationen"),
  Week(7) -> Code("funktion insert som givet en Array med heltal stoppar in ett element på en viss plats,\nanvänd en while-sats i implementationen"),
  Week(7) -> Code("funktion som avgör om två strängar är lika,\nanvänd en while-sats i implementationen"),
  Week(8) -> Concepts("repeterade parametrar", "enumeration"),
  Week(8) -> Code("en funktion som skriver ut en strängmatris rad för rad"),
  Week(9) -> Contrasts("mängd" -> "sekvens", "mängd" -> "nyckel-värde-tabell", "nyckel-värde-tabell" -> "sekvens"),
  Week(9) -> Code("en funktion som läser in rader med efternamn, förnamn från en textfil\noch skapar en sekvens av instanser av case-klassen Person"),
  Week(10) -> Concepts("arv", "subtypspolymorfism", "bastyp", "final medlem", "kodgranskning", "skyddad medlem (protected)", "överskuggning (override)", "dynamisk bindning", "kodduplicering", "anonym klass", "förseglade typer"),
  Week(10) -> Contrasts("subtyp" -> "supertyp", "klass" -> "trait", "arv" -> "komposition", "abstrakt medlem" -> "konkret medlem", "statisk typ" -> "dynamisk typ"),
)
