package shared

import util.Random.nextInt as rnd

case class Week(w: Int)

object Week:
  private val titles: Map[Int, String] =
    Map(
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
    )
  def title(week: Week): String = titles.getOrElse(week.w, "")

val countOf = collection.mutable.Map.empty[Any, Int].withDefaultValue(0)
def reg(a: Any): Unit = countOf(a) += 1

abstract class Question(cs: Vector[String | (String, String)]):
  def foreach = cs.foreach
  val title: String
  val instruction: String
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String)

abstract class Questions:
  val title: String
  val questionToAsk: String
  val instruction: String
  lazy val all: Seq[String] | Seq[(String, String)]

  def punctuation: Char = '?'

  def pickAnyQuestion(
      toWeek: Int,
      tpe: Questions
  ): String | (String, String) =
    val termsToWeekOfType: Seq[String | (String, String)] =
      val resultNested = for
        (Week(w), q) <- terms if w <= toWeek && q.title == tpe.title
      yield q match
        case xs: Concepts  => xs.cs
        case xs: Contrasts => xs.cs
        case xs: Code      => xs.cs
      resultNested.flatten
    if termsToWeekOfType.isEmpty then
      s"SORRY: Muntabotten har ingen sådan fråga till vecka $toWeek"
    else
      val counts: Seq[Int] = termsToWeekOfType.map(countOf)
      val minCount: Int = counts.minOption.getOrElse(0)
      val leastUsed =
        for i <- termsToWeekOfType.indices if counts(i) == minCount
        yield termsToWeekOfType(i)
      val result = leastUsed(rnd(leastUsed.length))
      reg(result)
      result

  def getQuestion(question: String | (String, String)): String =
    s"$title: $questionToAsk\n$question$punctuation\n\n$instruction"

  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    s"$questionToAsk ${question.toString.toUpperCase}$punctuation"

object Questions:
  val types: Vector[Questions] = Vector(Concepts, Contrasts, Code)

case class Concepts(cs: String*) extends Question(cs.toVector):
  val title = Concepts.title
  val instruction = Concepts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Concepts.getShortQuestion(question)
object Concepts extends Questions:
  val title = "Förklara koncept"
  val questionToAsk = "Vad menas med"
  val instruction =
    "Ge exempel på normal och felaktig/konstig användning. Förklara varför/när konceptet är bra att ha."

  lazy val all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten

case class Contrasts(cs: (String, String)*) extends Question(cs.toVector):
  val title = Contrasts.title
  val instruction = Contrasts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Contrasts.getShortQuestion(question)
object Contrasts extends Questions:
  val title = "Jämför koncept"
  val questionToAsk = "Vad finns det för skillnader och likheter mellan"
  val instruction =
    "Ge exempel på normal eller felaktig/konstig användning som belyser skillnader/likheter. Förklara varför koncepten finns och vad de ska vara bra till."

  lazy val all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten

case class Code(cs: (String, String)*) extends Question(cs.toVector):
  val title = Code.title
  val instruction = Code.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Code.getShortQuestion(question)
object Code extends Questions:
  val title = "Skriv kod"
  val questionToAsk: String =
    "Skriv kod på papper med"
  val instruction: String = "Skriv testfall som testar din kod."

  override def punctuation = '.'

  lazy val all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten
  override def getQuestion(
      question: String | (String, String)
  ): String =
    question match
      case question: (String, String) => s"${questionToAsk}:\n${question._1}39d2c101a1a9746c5e54da6ba6a4ed48${question._2}" // Random hash to split at
      case _ => "unexpected"

  override def getShortQuestion(
      question: String | (String, String)
  ): String =
    question match
      case question: (String, String) => s"${questionToAsk}:\n${question._1}"
      case _ => "unexpected"
