package org.micchon.nonemptylist

import org.scalatest.FlatSpec
import org.micchon.nonemptylist.NonEmptyList._

import scala.collection.mutable.ListBuffer

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
    assert(nel1.flatMap(e => NonEmptyList(1, e)) === NonEmptyList(1, "hoge", 1, "fuga"))
  }

  it should "benchmark test" in new SetUp {
    (1 to 5).foreach { n =>
      val num = Math.pow(10, n + 1).toInt
      println(s"iteration number is $num")
      benchmark(num, false)
      benchmark(num, true)
    }
  }

  it should "benchmark test2" in new SetUp {
    (1 to 5).foreach { n =>
      val num = Math.pow(10, n + 1).toInt
      println(s"iteration number is $num")
      benchmark(num, true)
    }
  }

  def benchmark(num: Int, isOld: Boolean): Unit = {
    if (isOld) {
      val start = System.currentTimeMillis()
      (1 to num).toNonEmptyList.flatMapOld(e => NonEmptyList(e, e))
      val end = System.currentTimeMillis()
      val result =
        s"""
           |Old
           |time: ${end -start}
       """.stripMargin
      println(result)
    } else {
      val start = System.currentTimeMillis()
      (1 to num).toNonEmptyList.flatMap(e => NonEmptyList(e, e))
      val end = System.currentTimeMillis()
      val result =
        s"""
           |New
           |time: ${end -start}
       """.stripMargin
      println(result)
    }
  }
}
