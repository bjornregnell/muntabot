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
    "while-sats" -> "for-sats",
    "typ" -> "värde",
    "if-uttryck" -> "if-sats",
    "def" -> "val",
    "var" -> "val",
    "kompileringsfel" -> "körtidsfel",
    "Char" -> "String",
    "källkod" -> "maskinkod"
  ),
  Week(1) -> Code(
    "ett uttryck som räknar ut cirkelarean givet radien av r" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Definiera%20namn%20p%C3%A5%20uttryck",
    "ett booleskt uttryck som är sant om x är större än noll eller x är mindre än -42" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Alternativ%20med%20if-uttryck",
    "en repetition som skriver ut de första 42 heltalen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Kontrollstrukturer:%20alternativ%20och%20repetition",
    "en funktion isEven(n: Int) som är sann om n är ett jämt tal" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Kontrollstrukturer:%20alternativ%20och%20repetition"
  ),
  Week(2) -> Concepts(
    "for-uttryck",
    "indexering",
    "kontrollstruktur",
    "huvudprogram"
  ),
  Week(2) -> Contrasts(
    "pseudokod" -> "exekverbar implementation",
    "parameter" -> "argument",
    "returtyp" -> "parametertyp",
    "for-uttryck" -> "for-sats",
    "Vector" -> "Array"
  ),
  Week(2) -> Code(
    "en funktion som ger minsta heltalet i en heltalssekvens,\nutan att använda inbyggda min" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Loopa%20genom%20en%20samling%20med%20en%20while-sats",
    "en funktion som ger största heltalet i en heltalssekvens,\nutan att använda inbyggda max" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Loopa%20genom%20en%20samling%20med%20en%20while-sats",
    "två förändringsbara variabler som först initialiseras\nmed två olika helatal och sedan byter värde med varandra" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Variabeldeklaration%20och%20tilldelningssats",
    "en funktion som summerar alla heltal i en sekvens,\nutan att använda inbyggda sum" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Loopa%20genom%20elementen%20i%20en%20vektor"
  ),
  Week(3) -> Concepts(
    "anonym funktion",
    "predikat",
    "äkta funktion",
    "aktiveringspost",
    "anropsstacken",
    "defaultargument",
    "returtyp",
    "namngivna argument",
    "lokal funktion",
    "funktion som äkta värde",
    "samlingsmetoden map"
  ),
  Week(3) -> Contrasts(
    "överlagring" -> "defaultargument",
    "namngivna argument" -> "defaultargument",
    "map" -> "foreach",
    "värdeanrop" -> "namnanrop"
  ),
  Week(3) -> Code(
    "en anonym funktion Int => Int som ger kvadraten och som appliceras på alla element i en heltalssekvens" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Anonyma%20funktioner",
    "en definition av en oäkta funktion som returnerar slumpmässiga jämna heltal mellan n och m" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Exempel%20p%C3%A5%20o%C3%A4kta%20funktioner:%20slumptal"
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
    "val" -> "lazy val",
    "def" -> "lazy val"
  ),
  Week(4) -> Code(
    "ett singelobjekt Highscore med en privat variabel för högsta poäng,\nen getter, en metod isHighscore(p: Int) och en metod update(p: Int) som ändrar tillståndet" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Exempel:%20singelobjektet%20med%20f%C3%B6r%C3%A4ndringsbart%20tillst%C3%A5nd",
    "en funktion som tar en punkt som en 2-tupel (Double, Double)\noch returnerar en ny 2-tupel, och visa hur man kommer åt delarna med punktnotation" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Tupler%20som%20parametrar%20och%20returv%C3%A4rde.",
    "en extensionsmetod på String som ger strängen baklänges och med versaler,\noch anropa den både med punktnotation och som en vanlig funktion" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Kollektiva%20extensionsmetoder"
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
  Week(5) -> Code(
    "en klass Person med klassparametrar för förnamn, efternamn och ålder\noch en egen toString som ger en läsbar strängrepresentation" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Skapa%20egen%20najs%20toString",
    "en oföränderlig case-klass Point och förklara skillnaden mellan == och eq,\noch använd copy för att skapa en delvis förändrad kopia" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Exempel:%20of%C3%B6r%C3%A4nderliga%20case-klassen%20Point",
    "en klass med en privat variabel för ålder som skyddas av en getter\noch en setter som förhindrar negativa värden" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=F%C3%B6rhindra%20felaktiga%20attributv%C3%A4rden%20med%20setters"
  ),
  Week(6) -> Concepts(
    "mönstermatchning",
    "undantag",
    "konstruktormönster",
    "Option",
    "Try",
    "förseglad typ"
  ),
  Week(6) -> Contrasts(
    "Try" -> "try-catch",
    "förseglad (sealed) typhierarki" -> "ej förseglad typhierarki"
  ),
  Week(6) -> Code(
    "en förseglad (sealed) typhierarki av grönsaker och en funktion som med\nmönstermatchning och en gard klassificerar en grönsak (uttömmande matchning)" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=M%C3%B6nstermatchning%20och%20f%C3%B6rseglade%20typer",
    "en funktion som slår upp ett värde i en nyckel-värde-tabell och ger ett\nstandardvärde om nyckeln saknas, och en variant som mönstermatchar på Some och None" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Option%20f%C3%B6r%20hantering%20av%20ev.%20saknade%20v%C3%A4rden",
    "en funktion som tolkar en sträng till ett heltal och fångar eventuellt fel\nmed Try, och en variant som använder try-catch" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Hantera%20undantag%20som%20ett%20v%C3%A4rde%20med%20Try"
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
    "skapa ny sekvens" -> "ändra på plats",
    "uppräknade värden med heltal" -> "uppräknade värden med enum",
    "List" -> "ListBuffer"
  ),
  Week(7) -> Code(
    "en funktion som linjärsöker efter en instans av klassen Person\nmed ett visst efternamn i en osorterad sekvens av personer\nmed hjälp av en while (och inte find/indexOf).\n Funktionens returtyp ska vara Option" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=S%C3%B6kning",
    "ett program som registrerar 1000 tärningskast i en Array" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Registrering",
    "en funktion copy som kopierar en Array med heltal\nelement för element till en ny Array\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Algoritm:%20SEQ-COPY",
    "en funktion insert som givet en Array med heltal\nstoppar in ett element på en viss plats\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Exempel:%20SEQ-INSERT/REMOVE-COPY",
    "en funktion som avgör om två strängar är lika\nsom använder en while-sats i implementationen" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=J%C3%A4mf%C3%B6ra%20str%C3%A4ngar:%20likhet"
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
    "en funktion som skriver ut en strängmatris rad för rad" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Iterera%20%C3%B6ver%20n%C3%A4stlad%20struktur",
    "ett predikat som testar om en nästlad sekvens av heltal\när en matematisk matris med m rader och n kolumner\nmed och utan hjälp av befintliga metoden forall" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Hur%20indexera%20i%20matriser?"
  ),
  Week(9) -> Concepts(
    "mängd",
    "nyckel-värde-tabell",
    "innehållstest",
    "serialisering",
    "persistens"
  ),
  Week(9) -> Contrasts(
    "mängd" -> "sekvens",
    "mängd" -> "nyckel-värde-tabell",
    "nyckel-värde-tabell" -> "sekvens"
  ),
  Week(9) -> Code(
    "en funktion readPersonSet som läser in rader med kommaseparerade efternamn och förnamn från en textfil\noch skapar en mängd av instanser av case-klassen Person och kastar ett undantag om det finns dubbletter" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=L%C3%A4sa%20text%20fr%C3%A5n%20fil%20och%20webbsida",
    "en funktion readPersonMap som läser in rader med kommaseparerade efternamn och förnamn och personnummer från en textfil\noch skapar en nyckel-värde-tabell med personnummer av typen Long som nyckel och instanser av case-klassen Person som värde" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=L%C3%A4sa%20text%20fr%C3%A5n%20fil%20och%20webbsida",
    "en funktion som grupperar en sekvens av ord i en nyckel-värde-tabell\nefter ordlängd med hjälp av groupBy" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Metoderna%20zipWithIndex,%20groupBy",
    "en funktion som med sliding beräknar medelvärdet av varje par\nav intilliggande tal i en talsekvens" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Metoden%20sliding",
    "en nyckel-värde-tabell som skapas från en sekvens av par (namn, ålder)\nmed hjälp av toMap" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Fr%C3%A5n%20sekvens%20av%20par%20till%20tabell"
  ),
  Week(10) -> Concepts(
    "arv",
    "subtypspolymorfism",
    "bastyp",
    "final medlem",
    "skyddad medlem (protected)",
    "överskuggning (override)",
    "dynamisk bindning",
    "kod-duplicering",
    "anonym klass"
  ),
  Week(10) -> Contrasts(
    "subtyp" -> "supertyp",
    "klass" -> "trait",
    "arv" -> "komposition",
    "abstrakt medlem" -> "konkret medlem",
    "statisk typ" -> "dynamisk typ"
  ),
  Week(10) -> Code(
    "en gemensam bastyp (trait) för olika grönsaker med en abstrakt medlem för vikt,\noch kod som lägger flera grönsaker i en sekvens och summerar vikten" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Skapa%20en%20gemensam%20bastyp%20med%20trait%20och%20extends",
    "en subklass som överskuggar en metod med override\noch anropar supertypens version med super" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Exempel:%20%C3%96verskuggning%20och%20override",
    "en klass som skrivs om från att använda arv till att i stället använda komposition" -> "https://fileadmin.cs.lth.se/pgk/compendium.pdf#nameddest=Alternativ%20till%20arv:%20komposition"
  )
)
