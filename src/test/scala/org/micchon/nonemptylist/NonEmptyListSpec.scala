package org.micchon.nonemptylist

import org.scalatest.FlatSpec

class NonEmptyListSpec extends FlatSpec {

  trait SetUp { self =>
    val nel0 = NonEmptyList(1, 2, 3)
    val nel1 = NonEmptyList("hoge", "fuga")
    val nel2 = NonEmptyList(100, 200, 10000)
    val lst1 = List(3.14)
  }

  "::" should "concatenate one and one nonempty list" in new SetUp {
    assert(1 :: nel0 === NonEmptyList(1, 1, 2, 3))
  }

  ":::" should "concatenate one list(including nonempty list) and another one" in new SetUp {
    assert(nel1 ::: nel2 === NonEmptyList("hoge", "fuga", 100, 200, 10000))
    assert(lst1 ::: nel2 === NonEmptyList(3.14, 100, 200, 10000))
  }

  "map" should "behave correctly" in new SetUp {
    assert(nel0.map(i => i + 1) === NonEmptyList(2, 3, 4))
  }

  "flatMap" should "behave correctly" in new SetUp {
    assert(nel0.flatMap(e => NonEmptyList(e, e + 1)) === NonEmptyList(1, 2, 2, 3, 3, 4))
  }
}
