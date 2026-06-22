package shared

import util.Random.nextInt as rnd

case class Week(w: Int)

object Week:
  /** Week themes for the current language (see Texts). */
  def titles: Map[Int, String] = Texts.current.weekThemes
  def title(week: Week): String = titles.getOrElse(week.w, "")

val countOf = collection.mutable.Map.empty[Any, Int].withDefaultValue(0)
def reg(a: Any): Unit = countOf(a) += 1

abstract class Question(cs: Vector[String | (String, String)]):
  def foreach = cs.foreach
  def title: String
  /** Stable language-independent id ("concepts"/"contrasts"/"code") for matching. */
  def kind: String
  def instruction: String
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String)
  def shortItem(question: String | (String, String)): String

abstract class Questions:
  def title: String
  def questionToAsk: String
  def instruction: String
  /** Stable language-independent id, used to match data entries to a type. */
  def kind: String
  def all: Seq[String] | Seq[(String, String)]

  def punctuation: Char = '?'

  def pickAnyQuestion(
      fromWeek: Int,
      toWeek: Int,
      tpe: Questions
  ): String | (String, String) =
    val termsToWeekOfType: Seq[String | (String, String)] =
      val resultNested = for
        (Week(w), q) <- terms if w >= fromWeek && w <= toWeek && q.kind == tpe.kind
      yield q match
        case xs: Concepts  => xs.cs
        case xs: Contrasts => xs.cs
        case xs: Code      => xs.cs
      resultNested.flatten
    if termsToWeekOfType.isEmpty then Texts.current.sorry(fromWeek, toWeek)
    else
      val counts: Seq[Int] = termsToWeekOfType.map(countOf)
      val minCount: Int = counts.minOption.getOrElse(0)
      val leastUsed =
        for i <- termsToWeekOfType.indices if counts(i) == minCount
        yield termsToWeekOfType(i)
      val result = leastUsed(rnd(leastUsed.length))
      reg(result)
      result

  def show(question: String | (String, String)): String = question match
    case (a, b)    => s"$a ${Texts.current.and} $b"
    case s: String => s

  /** The item without the question prompt (term, "a och b", or code desc). */
  def shortItem(question: String | (String, String)): String = show(question)

  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    s"$questionToAsk ${show(question)}$punctuation"

object Questions:
  val types: Vector[Questions] = Vector(Concepts, Contrasts, Code)

case class Concepts(cs: String*) extends Question(cs.toVector):
  def title = Concepts.title
  def kind = Concepts.kind
  def instruction = Concepts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Concepts.getShortQuestion(question)
  def shortItem(question: String | (String, String)): String =
    Concepts.shortItem(question)
object Concepts extends Questions:
  def title = Texts.current.conceptsTitle
  def kind = "concepts"
  def questionToAsk = Texts.current.conceptsAsk
  def instruction = Texts.current.conceptsInstr

  def all = (for case (w, t: Concepts) <- terms yield t).map(_.cs).flatten

case class Contrasts(cs: (String, String)*) extends Question(cs.toVector):
  def title = Contrasts.title
  def kind = Contrasts.kind
  def instruction = Contrasts.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Contrasts.getShortQuestion(question)
  def shortItem(question: String | (String, String)): String =
    Contrasts.shortItem(question)
object Contrasts extends Questions:
  def title = Texts.current.contrastsTitle
  def kind = "contrasts"
  def questionToAsk = Texts.current.contrastsAsk
  def instruction = Texts.current.contrastsInstr

  def all = (for case (w, t: Contrasts) <- terms yield t).map(_.cs).flatten

case class Code(cs: (String, String)*) extends Question(cs.toVector):
  def title = Code.title
  def kind = Code.kind
  def instruction = Code.instruction
  def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    Code.getShortQuestion(question)
  def shortItem(question: String | (String, String)): String =
    Code.shortItem(question)
object Code extends Questions:
  def title = Texts.current.codeTitle
  def kind = "code"
  def questionToAsk: String = Texts.current.codeAsk
  def instruction: String = Texts.current.codeInstr

  override def punctuation = '.'

  def all = (for case (w, t: Code) <- terms yield t).map(_.cs).flatten

  override def shortItem(question: String | (String, String)): String =
    question match
      case (code, _) => code
      case _         => question.toString

  override def getShortQuestion(
      question: String | (String, String)
  ): String | (String, String) =
    question match
      case question: (String, String) => s"$questionToAsk ${question._1.toString}$punctuation"
      case _ => "unexpected"
