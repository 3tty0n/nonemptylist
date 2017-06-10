package org.micchon.nonemptylist

import org.scalatest.FlatSpec

import scala.collection.mutable.ListBuffer

class NonEmptyListSpec extends FlatSpec {

  trait SetUp { self =>
    val nel0 = NonEmptyList(1, 2, 3)
    val nel1 = NonEmptyList("hoge", "fuga")
    val nel3 = NonEmptyList(1, "piyo", 3.0, false)
    val lst0 = List(3.14)
  }

  "::" should "concatenate one and one nonempty list" in new SetUp {
    assert(1 :: nel0 === NonEmptyList(1, 1, 2, 3))
  }

  ":::" should "concatenate one list(including nonempty list) and another one" in new SetUp {
    assert(nel1 ::: nel0 === NonEmptyList("hoge", "fuga", 1, 2, 3))
    assert(lst0 ::: nel0 === NonEmptyList(3.14, 1, 2, 3))
  }

  "map" should "behave correctly" in new SetUp {
    assert(nel0.map(i => i + 1) === NonEmptyList(2, 3, 4))
  }

  "flatMap" should "behave correctly" in new SetUp {
    assert(nel0.flatMap(e => NonEmptyList(e, e + 1)) === NonEmptyList(1, 2, 2, 3, 3, 4))
  }

  "collect" should "behave correctly" in new SetUp {
    case class Piyo(value: String)
    case class Poyo(value: Int)
    val nel = NonEmptyList(Piyo("piyo"), Poyo(1), Poyo(2))
    assert(nel.collect { case Poyo(v) => v } === List(1, 2))
  }

  "filter" should "filter NonEmptyList that is specified the conditions" in new SetUp {
    assert(nel0.filter(i => i % 2 == 0) === List(2))
  }

  "filterNot" should "filter NonEmptyList that is not specified the conditions" in new SetUp {
    assert(nel0.filterNot(i => i % 2 == 0) === List(1, 3))
  }

  "forall" should "" in new SetUp {
    assert(nel0.forall(n => n >= 0) === true)
    assert(nel3.forall(n => n == 1) === false)
  }

  "implicit conversion" should "behave correctly" in new SetUp {
    import NonEmptyList._
    assert(List(1, 2, 3).toNonEmptyList === NonEmptyList(1, 2, 3))
    assert(Seq("piyo").toNonEmptyList === NonEmptyList("piyo"))
    intercept[CannotConvertException] {
      List().toNonEmptyList
      Seq().toNonEmptyList
      ListBuffer().toNonEmptyList
    }
  }
}
