import CsvEncoder._
import IceCream._
import shapeless.Generic

object IceCreamEncoder extends App {

//  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly
//  reprEncoder.encode("abc" :: 123 :: true :: HNil)

  implicit val iceCreamEncoder: CsvEncoder[IceCream] = {
    val gen = Generic[IceCream] //shapeless is used
    val enc: CsvEncoder[gen.Repr] = implicitly //CsvEncoder[HList], calling hlistEncoder => string => int => bool => hnil encoder

    createEncoder(iceCream => enc.encode( //'sundae :: 1 :: false :: Hnil' to normal List(sundae :: 1 :: false :: Nil)
      gen.to(iceCream) //case class to sundae :: 1 :: false :: HNil'
    ))
  }

  println(writeCsv(iceCreams)) //I can generalise 1) case class parameters using generic.to HList feature, which is a 2) generalised form of List[Type].
}
