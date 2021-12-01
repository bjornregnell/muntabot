package shared

import util.Random.nextInt as rnd

case class Week(w: Int)

val countOf = collection.mutable.Map.empty[Any, Int].withDefaultValue(0)
def reg(a: Any): Unit = countOf(a) += 1

extension [T](xs: Seq[T])
  def pick: T =
    val counts: Seq[Int] = xs.map(countOf)
    val min = counts.minOption.getOrElse(0)
    val leastUsed = for i <- xs.indices if counts(i) == min yield xs(i)
    val result = leastUsed(rnd(leastUsed.length))
    reg(result)
    result

abstract class Question(cs: Vector[String | (String, String)]):
  def foreach = cs.foreach
  val title: String
  val instruction: String
  def getShortQuestion(question: String | (String, String)): String

abstract class Questions:
  val title: String
  val questionToAsk: String
  val instruction: String
  lazy val all: Seq[String] | Seq[(String, String)]

  def pickAnyQuestion: String | (String, String) = all.pick

  def getQuestion(question: String | (String, String)): String =
    s"$questionToAsk\n$question.\n\n$instruction"

  def getShortQuestion(question: String | (String, String)): String =
    s"$questionToAsk \"$question\"?"

object Questions:
  val types: Vector[Questions] = Vector(Concepts, Contrasts, Code)

case class Concepts(cs: String*) extends Question(cs.toVector):
  val title = Concepts.title
  val instruction = Concepts.instruction
  def getShortQuestion(question: String | (String, String)): String =
    Concepts.getShortQuestion(question)
object Concepts extends Questions:
  val title = "Förklara koncept"
  val questionToAsk = "Vad menas med"
  val instruction = "Ge exempel på normal och felaktig/konstig användning."

  lazy val all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten

case class Contrasts(cs: (String, String)*) extends Question(cs.toVector):
  val title = Contrasts.title
  val instruction = Contrasts.instruction
  def getShortQuestion(question: String | (String, String)): String =
    Contrasts.getShortQuestion(question)
object Contrasts extends Questions:
  val title = "Jämför koncept"
  val questionToAsk = "Vad finns det för skillnader och likheter mellan"
  val instruction =
    "Ge exempel på normal eller felaktig/konstig användning som belyser skillnader/likheter."

  lazy val all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten

case class Code(cs: String*) extends Question(cs.toVector):
  val title = Code.title
  val instruction = Code.instruction
  def getShortQuestion(question: String | (String, String)): String =
    Code.getShortQuestion(question)
object Code extends Questions:
  val title = "Skriv kod"
  val questionToAsk: String =
    "Skriv kod på papper med"
  val instruction: String = "Skriv testfall som testar din kod."

  lazy val all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten
  override def getShortQuestion(question: String | (String, String)) =
    s"${questionToAsk}: \n\n${question}?"
