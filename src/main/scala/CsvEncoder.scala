import shapeless.{::, HList, HNil}

object CsvEncoder {
  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(v => enc.encode(v).mkString(", ")).mkString("\n")

  def createEncoder[A](fn: A => List[String]): CsvEncoder[A] = (value: A) => fn(value)

  implicit val stringEncoder: CsvEncoder[String] = createEncoder(str => List(str))
  implicit val intEncoder: CsvEncoder[Int] = createEncoder(int => List(int.toString))
  implicit val booleanEncoder: CsvEncoder[Boolean] = createEncoder(bool => List(if (bool) "yes" else "no"))

  implicit val hnilEncoder: CsvEncoder[HNil] = createEncoder(_ => Nil) //shapeless is used

  //shapeless used as HList, ::
  implicit def hlistEncoder[H, T <: HList] (implicit hEncoder: CsvEncoder[H], tEncoder: CsvEncoder[T])
  : CsvEncoder[H :: T] =
    createEncoder{case h::t => hEncoder.encode(h) ++ tEncoder.encode(t)} //createEncoder takes fn so partial function is possible.
}

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}
