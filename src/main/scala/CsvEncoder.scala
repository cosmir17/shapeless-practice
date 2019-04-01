import shapeless.{::, Generic, HList, HNil}

object CsvEncoder extends App {

  case class IceCream(value: String, numCherries: Int, inCone: Boolean)

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(v => enc.encode(v).mkString(", ")).mkString("\n")

  trait CsvEncoder[A] {
    def encode(value: A): List[String]
  }

  def createEncoder[A](fn: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] = fn(value)
    }

  implicit val stringEncoder: CsvEncoder[String] = createEncoder(str => List(str))
  implicit val intEncoder: CsvEncoder[Int] = createEncoder(int => List(int.toString))
  implicit val booleanEncoder: CsvEncoder[Boolean] = createEncoder(bool => List(if (bool) "yes" else "no"))

  implicit val hnilEncoder: CsvEncoder[HNil] = createEncoder(_ => Nil) //shapeless is used

  //shapeless used as HList, ::
  implicit def hlistEncoder[H, T <: HList] (implicit hEncoder: CsvEncoder[H], tEncoder: CsvEncoder[T])
  : CsvEncoder[H :: T] =
    createEncoder{case h::t => hEncoder.encode(h) ++ tEncoder.encode(t)} //createEncoder takes fn so partial function is possible.

//  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly
//  reprEncoder.encode("abc" :: 123 :: true :: HNil)

  val gen = Generic[IceCream] //shapeless is used
  val enc: CsvEncoder[gen.Repr] = implicitly //CsvEncoder[HList], calling hlistEncoder => string => int => bool => hnil encoder

  implicit val iceCreamEncoder: CsvEncoder[IceCream] =
    createEncoder(iceCream => enc.encode( //'sundae :: 1 :: false :: Hnil' to normal List(sundae :: 1 :: false :: Nil)
      gen.to(iceCream) //case class to sundae :: 1 :: false :: HNil'
    ))

  val iceCreams: List[IceCream] = List(
    IceCream("Sundae", 1, false),
    IceCream("Cornetto", 0, true),
    IceCream("Banana Split", 0, false)
  )

  println(writeCsv(iceCreams)) //I can generalise 1) case class parameters using generic.to HList feature, which is a 2) generalised form of List[Type].
}
