import CsvEncoder._
import IceCream._
import shapeless.Generic

object GenericEncoder extends App {

  implicit def genericEncoder[A, R](implicit
    gen: Generic.Aux[A, R],
    env: CsvEncoder[R]):
  CsvEncoder[A] = createEncoder(a => env.encode(gen.to(a)))

  println(writeCsv(iceCreams))
}
