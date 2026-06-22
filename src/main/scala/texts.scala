package shared

/** All fixed UI strings, per language. Quest content lives in data-sv/data-en;
  * this holds the surrounding chrome, prompts, instructions and week themes. */
case class Texts(
    // muntabot page
    pickFromWeek: String,
    toWeek: String,
    clickButtons: String,
    readInCompendium: String,
    showInfo: String,
    showInBookSuffix: String,
    pageAbbrev: String,
    and: String,
    sorry: (Int, Int) => String,
    revealAll: String,
    readAboutExamHtml: String,
    aidsHtml: String,
    // question types
    conceptsTitle: String,
    conceptsAsk: String,
    conceptsInstr: String,
    contrastsTitle: String,
    contrastsAsk: String,
    contrastsInstr: String,
    codeTitle: String,
    codeAsk: String,
    codeInstr: String,
    // week themes
    weekThemes: Map[Int, String],
    // rehearsal page
    pickOneAtATime: String,
    allQuestions: String,
    search: String,
    perWeek: String,
    perCategory: String,
    rehearsalIntro: String,
    searchResults: String,
    weekWord: String,
    backHome: String
)

object Texts:
  def of(lang: Lang): Texts = lang match
    case Lang.Sv => sv
    case Lang.En => en
  def current: Texts = of(Lang.current)

  val base = "https://fileadmin.cs.lth.se/pgk"

  val sv = Texts(
    pickFromWeek = "Slumpa en fråga i taget från läsvecka ",
    toWeek = " till och med läsvecka ",
    clickButtons = "Klicka på knapparna ovan så får du en uppgift.",
    readInCompendium = "Läs i kompendiet om: ",
    showInfo = "Visa information från kursboken",
    showInBookSuffix = " – visa i kursboken",
    pageAbbrev = "s.",
    and = "och",
    sorry = (a, b) => s"SORRY: Muntabotten har ingen sådan fråga för läsvecka $a–$b",
    revealAll = "Spojla alla frågor",
    readAboutExamHtml =
      "Läs om muntan i " +
        s"""<a href="$base/compendium.pdf">kompendiet;-1.7:anvisningar;muntligt prov</a>""" +
        " OCH " +
        s"""<a href="$base/lect-w12.pdf">föreläsn. w12</a>""",
    aidsHtml =
      "Hjälpmedel vid skarp munta: papper, penna, " +
        s"""<a href="$base/quickref.pdf">snabbreferensen</a>.""",
    conceptsTitle = "Förklara koncept",
    conceptsAsk = "Vad menas med",
    conceptsInstr =
      "Ge exempel på normal och felaktig/konstig användning. Förklara varför/när konceptet är bra att ha.",
    contrastsTitle = "Jämför koncept",
    contrastsAsk = "Vad finns det för skillnader och likheter mellan",
    contrastsInstr =
      "Ge exempel på normal eller felaktig/konstig användning som belyser skillnader/likheter. Förklara varför koncepten finns och vad de ska vara bra till.",
    codeTitle = "Skriv kod",
    codeAsk = "Skriv kod på papper med",
    codeInstr = "Skriv testfall som testar din kod.",
    weekThemes = Map(
      1 -> "Introduktion",
      2 -> "Program, kontrollstrukturer",
      3 -> "Funktioner, abstraktion",
      4 -> "Objekt, inkapsling",
      5 -> "Klasser, datamodellering",
      6 -> "Mönster, felhantering",
      7 -> "Sekvenser, enumerationer",
      8 -> "Matriser, typparametrar",
      9 -> "Mängder, tabeller",
      10 -> "Arv, komposition"
    ),
    pickOneAtATime = "Slumpa en fråga i taget",
    allQuestions = "Alla frågor från muntabot",
    search = "Sök",
    perWeek = "Per vecka",
    perCategory = "Per kategori",
    rehearsalIntro =
      "Svara på frågor och gör koduppdrag nedan. Ge även exempel på normal och felaktig/konstig användning. För varje koduppdrag: skriv även testfall som testar din kod. Hjälpmedel vid skarp munta: papper, penna, snabbreferens.",
    searchResults = "Sökresultat:",
    weekWord = "Vecka",
    backHome = "Tillbaka hem"
  )

  val en = Texts(
    pickFromWeek = "Pick a random question from study week ",
    toWeek = " up to and including study week ",
    clickButtons = "Click the buttons above to get a task.",
    readInCompendium = "Read in the compendium about: ",
    showInfo = "Show information from the course book",
    showInBookSuffix = " – show in the course book",
    pageAbbrev = "p.",
    and = "and",
    sorry = (a, b) => s"SORRY: muntabot has no such question for study week $a–$b",
    revealAll = "Reveal all questions",
    readAboutExamHtml =
      "Read about the oral exam in " +
        s"""<a href="$base/compendium.pdf">the compendium;-1.7:instructions;oral exam</a>""" +
        " AND " +
        s"""<a href="$base/lect-w12.pdf">lecture w12</a>""",
    aidsHtml =
      "Aids during the real oral exam: paper, pen, " +
        s"""<a href="$base/quickref.pdf">the quick reference</a>.""",
    conceptsTitle = "Explain concept",
    conceptsAsk = "What is meant by",
    conceptsInstr =
      "Give examples of normal and incorrect/odd usage. Explain why/when the concept is good to have.",
    contrastsTitle = "Compare concepts",
    contrastsAsk = "What are the differences and similarities between",
    contrastsInstr =
      "Give examples of normal or incorrect/odd usage that highlight differences/similarities. Explain why the concepts exist and what they are good for.",
    codeTitle = "Write code",
    codeAsk = "Write code on paper with",
    codeInstr = "Write test cases that test your code.",
    weekThemes = Map(
      1 -> "Introduction",
      2 -> "Programs, control structures",
      3 -> "Functions, abstraction",
      4 -> "Objects, encapsulation",
      5 -> "Classes, data modelling",
      6 -> "Patterns, error handling",
      7 -> "Sequences, enumerations",
      8 -> "Matrices, type parameters",
      9 -> "Sets, tables",
      10 -> "Inheritance, composition"
    ),
    pickOneAtATime = "Pick one question at a time",
    allQuestions = "All questions from muntabot",
    search = "Search",
    perWeek = "By week",
    perCategory = "By category",
    rehearsalIntro =
      "Answer the questions and do the coding tasks below. Also give examples of normal and incorrect/odd usage. For each coding task: also write test cases that test your code. Aids during the real oral exam: paper, pen, quick reference.",
    searchResults = "Search results:",
    weekWord = "Week",
    backHome = "Back home"
  )
