package org.micchon

import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class NonEmptyListSpec extends FunSuite {

  val nel0 = NonEmptyList(1, 2, 3)
  val nel1 = NonEmptyList("hoge", "fuga")
  val nel3 = NonEmptyList(1, "piyo", 3.0, false)
  val lst0 = List(3.14)

  test("::") {
    assert(1 :: nel0 === NonEmptyList(1, 1, 2, 3))
  }

  test(":::") {
    assert(nel1 ::: nel0 === NonEmptyList("hoge", "fuga", 1, 2, 3))
    assert(lst0 ::: nel0 === NonEmptyList(3.14, 1, 2, 3))
  }

  test("++") {
    assert(nel0 ++ lst0 === NonEmptyList(1, 2, 3, 3.14))
  }

  test("reverse") {
    assert(nel0.reverse === NonEmptyList(3, 2, 1))
  }

  test("map") {
    assert(nel0.map(i => i + 1) === NonEmptyList(2, 3, 4))
  }

  test("flatMap") {
    assert(nel0.flatMap(e => NonEmptyList(e, e + 1)) === NonEmptyList(1, 2, 2, 3, 3, 4))
  }

  test("collect") {
    case class Piyo(value: String)
    case class Poyo(value: Int)
    val nel = NonEmptyList(Piyo("piyo"), Poyo(1), Poyo(2))
    assert(nel.collect { case Poyo(v) => v } === List(1, 2))
  }

  test("filter") {
    assert(nel0.filter(i => i % 2 == 0) === List(2))
  }

  test("filterNot") {
    assert(nel0.filterNot(i => i % 2 == 0) === List(1, 3))
  }

  test("forall") {
    assert(nel0.forall(n => n >= 0) === true)
    assert(nel3.forall(n => n == 1) === false)
  }

  test("unapply") {
    val res = nel0 match {
      case NonEmptyList(x, xs) => x
    }
    assert(res === 1)
  }

  test("implicit conversion") {
    import org.micchon.nonemptylist.implicits._
    import org.micchon.exception._

    assert(List(1, 2, 3).toNonEmptyList === NonEmptyList(1, 2, 3))
    assert(Seq("piyo").toNonEmptyList === NonEmptyList("piyo"))
    intercept[CannotConvertException] {
      List().toNonEmptyList
      Seq().toNonEmptyList
      ListBuffer().toNonEmptyList
    }
  }
}
