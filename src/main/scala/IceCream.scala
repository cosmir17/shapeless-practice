object IceCream {
  case class IceCream(value: String, numCherries: Int, inCone: Boolean)

  val iceCreams: List[IceCream] = List(
    IceCream("Sundae", 1, false),
    IceCream("Cornetto", 0, true),
    IceCream("Banana Split", 0, false)
  )
}
