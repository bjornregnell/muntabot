package muntabot

import util.Random.nextInt as rnd

case class Week(w: Int)

val countOf = collection.mutable.Map.empty[Any, Int].withDefaultValue(0)
def reg(a: Any): Unit = countOf(a) += 1

extension [T](xs: Seq[T]) def pick: T = 
  val counts: Seq[Int] = xs.map(countOf)
  val min = counts.minOption.getOrElse(0)
  val leastUsed = for i <- xs.indices if counts(i) == min yield xs(i)
  val result = leastUsed(rnd(leastUsed.length))
  reg(result)
  result


case class Concepts(cs: String*)

object Concepts: 
  lazy val all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten 

  def pickAnyQuestion: String = 
    val p = all.pick
    s"Vad menas med \n$p?\n\nGe exempel på normal och \nfelaktig/konstig användning."


case class Contrasts(cs: (String, String)*) 
object Contrasts:
  lazy val all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten 

  def pickAnyQuestion: String = 
    val p = all.pick
    s"Vad finns det för skillnader och likheter mellan \n$p?\n\nGe exempel på normal och \nfelaktig/konstig användning."


case class Code(cs: String*)
object Code:
  lazy val all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten 

  def pickAnyQuestion: String = 
    val p = all.pick
    s"Skriv kod på papper med\n$p?\n\nSkriv testfall som testar din kod."


