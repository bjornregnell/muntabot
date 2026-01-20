package shared

lazy val terms = Seq[(Week, Concepts | Contrasts | Code)](
  Week(1) -> Concepts(
    "funktion",
    "while-sats",
    "for-sats",
    "stränginterpolator",
    "tilldelning",
    "kompilering",
    "grundtyper",
    "booleskt värde",
    "exekvering i sekvens",
    "abstraktion"
  ),
  Week(1) -> Contrasts(
    "map" -> "foreach",
    "for-uttryck" -> "for-sats",
    "Vector" -> "Array",
    "while" -> "for-do",
    "typ" -> "värde",
    "if-uttryck" -> "if-sats",
    "def" -> "val",
    "var" -> "val",
    "kompileringsfel" -> "körtidsfel",
    "Char" -> "String",
    "källkod" -> "maskinkod",
    "Char" -> "String"
  ),
  Week(1) -> Code(
    "ett uttryck som räknar ut cirkelarean givet radien av r" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.1.1.24",
    "ett booleskt uttryck som är sant om x är större än noll eller x är mindre än -42" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.1.1.29",
    "en repetition som skriver ut de första 42 heltalen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.1.1.36",
    "en funktion isEven(n: Int) som är sann om n är ett jämt tal" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.1.1.36"
  ),
  Week(2) -> Concepts(
    "for-uttryck",
    "samlingsmetoden map",
    "indexering",
    "kontrollstruktur",
    "huvudprogram"
  ),
  Week(2) -> Contrasts(
    "pseudokod" -> "exekverbar implementation",
    "parameter" -> "argument",
    "returtyp" -> "parametertyp",
    "for-do" -> "for-yield"
  ),
  Week(2) -> Code(
    "en funktion som ger minsta heltalet i en heltalssekvens,\nutan att använda inbyggda min" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.2.1.13",
    "en funktion som ger största heltalet i en heltalssekvens,\nutan att använda inbyggda max" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.2.1.13",
    "två förändringsbara variabler som först initialiseras\nmed två olika helatal och sedan byter värde med varandra" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.1.1.31",
    "en funktion som summerar alla heltal i en sekvens,\nutan att använda inbyggda sum" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.2.1.8"
  ),
  Week(3) -> Concepts(
    "anonym funktion",
    "predikat",
    "äkta funktion",
    "aktiveringspost",
    "anropsstacken",
    "default-argument",
    "returtyp",
    "namngivna argument",
    "lokal funktion",
    "funktion som äkta värde"
  ),
  Week(3) -> Contrasts(
    "överlagring" -> "default-argument",
    "namngivna argument" -> "default-argument"
  ),
  Week(3) -> Code(
    "en anonym funktion Int => Int som ger kvadraten och som appliceras på alla element i en heltalssekvens" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.3.1.20",
    "en definition av en oäkta funktion som returnerar slumpmässiga jämna heltal mellan n och m" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.3.1.18"
  ),
  Week(4) -> Concepts(
    "singelobjekt",
    "paket",
    "privat medlem",
    "namnrymd",
    "metoden apply",
    "tupel",
    "import",
    "punktnotation",
    "extensionsmetod",
    "classpath"
  ),
  Week(4) -> Contrasts(
    "värdeanrop" -> "namnanrop",
    "val" -> "lazy val",
    "def" -> "lazy val"
  ),
  Week(5) -> Concepts(
    "klass",
    "attribut",
    "fabriksmetod",
    "instansiering",
    "tillstånd",
    "klassparameter",
    "kompanjonsobjekt",
    "nyckelordet this",
    "strängrepresentation"
  ),
  Week(5) -> Contrasts(
    "funktion" -> "metod",
    "klass" -> "case-klass",
    "singelobjekt" -> "klass",
    "getter" -> "setter",
    "referenslikhet" -> "innehållslikhet"
  ),
  Week(6) -> Concepts(
    "mönstermatchning",
    "undantag",
    "konstruktormönster",
    "Option",
    "Try"
  ),
  Week(6) -> Contrasts(
    "Try" -> "try-catch"
  ),
  Week(7) -> Concepts(
    "pseudokod",
    "sekvensalgoritm",
    "filtrering",
    "transformering",
    "registrering",
    "att ändra på plats",
    "likhet mellan strängar",
    "sorterad sekvens av strängar",
    "enumeration",
    "linjärsökning",
    "insättningssortering",
    "repeterade parametrar"
  ),
  Week(7) -> Contrasts(
    "ArrayBuffer" -> "Array",
    "förändringsbar samling" -> "oföränderlig samling",
    "transformering till ny sekvens" -> "transformering på plats",
    "uppräknade värden med heltal" -> "uppräknade värden med enum",
    "förseglad (sealed) typhierarki" -> "ej förseglad typhierarki",
    "List" -> "ListBuffer"
  ),
  Week(7) -> Code(
    "en funktion som linjärsöker efter en instans av klassen Person\nmed ett visst efternamn i en osorterad sekvens av personer\nmed hjälp av en while (och inte find/indexOf).\n Funktionens returtyp ska vara Option" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.7.1.53",
    "ett program som registrerar 1000 tärningskast i en Array" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.7.1.30",
    "en funktion copy som kopierar en Array med heltal\nelement för element till en ny Array\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.7.1.35",
    "en funktion insert som givet en Array med heltal\nstoppar in ett element på en viss plats\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.7.1.38",
    "en funktion som avgör om två strängar är lika\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.7.1.47"
  ),
  Week(8) -> Concepts(
    "matris",
    "nästlad struktur",
    "typparameter",
    "generisk funktion"
  ),
  Week(8) -> Contrasts(
    "matris" -> "vektor",
    "generisk datastruktur" -> "generisk funktion"
  ),
  Week(8) -> Code(
    "en funktion som skriver ut en strängmatris rad för rad" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.8.1.14",
    "ett predikat som testar om en nästlad sekvens av heltal\när en matematisk matris med m rader och n kolumner\nmed och utan hjälp av befintliga metoden forall" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.8.1.5"
  ),
  Week(9) -> Contrasts(
    "mängd" -> "sekvens",
    "mängd" -> "nyckel-värde-tabell",
    "nyckel-värde-tabell" -> "sekvens"
  ),
  Week(9) -> Code(
    "en funktion readPersonSet som läser in rader med kommaseparerade efternamn och förnamn från en textfil\noch skapar en mängd av instanser av case-klassen Person och kastar ett undantag om det finns dubbletter" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.9.1.32",
    "en funktion readPersonMap som läser in rader med kommaseparerade efternamn och förnamn och personnummer från en textfil\noch skapar en nyckel-värde-tabell med personnummer av typen Long som nyckel och instanser av case-klassen Person som värde" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#subsection.9.1.32"
  ),
  Week(10) -> Concepts(
    "arv",
    "subtypspolymorfism",
    "bastyp",
    "final medlem",
    "kodgranskning",
    "skyddad medlem (protected)",
    "överskuggning (override)",
    "dynamisk bindning",
    "kod-duplicering",
    "anonym klass",
    "förseglad typ"
  ),
  Week(10) -> Contrasts(
    "subtyp" -> "supertyp",
    "klass" -> "trait",
    "arv" -> "komposition",
    "abstrakt medlem" -> "konkret medlem",
    "statisk typ" -> "dynamisk typ"
  )
)
