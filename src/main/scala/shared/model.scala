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

case class Concepts(cs: String*):
  def foreach = cs.toArray.foreach
object Concepts:
  val preQuestion = "Vad menas med"
  val postQuestion = "Ge exempel på normal och felaktig/konstig användning."

  lazy val all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten

  def pickAnyQuestion = all.pick

  def withQuestion(question: String) =
    s"$preQuestion\n$question?\n\n$postQuestion"

case class Contrasts(cs: (String, String)*):
  def foreach = cs.toArray.foreach
object Contrasts:
  val preQuestion = "Vad finns det för skillnader och likheter mellan"

  val postQuestion =
    "Ge exempel på normal eller felaktig/konstig användning som belyser skillnader/likheter."

  lazy val all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten

  def pickAnyQuestion = all.pick

  def withQuestion(question: (String, String)) =
    s"$preQuestion\n$question?\n\n$postQuestion"

case class Code(cs: String*):
  def foreach = cs.toArray.foreach
object Code:
  val preQuestion: String =
    "Skriv kod på papper med"

  val postQuestion: String = "Skriv testfall som testar din kod."

  lazy val all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten

  def pickAnyQuestion = all.pick

  def withQuestion(question: String): String =
    s"$preQuestion\n$question.\n\n$postQuestion"
