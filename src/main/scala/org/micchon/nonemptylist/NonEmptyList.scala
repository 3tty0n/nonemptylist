package org.micchon.nonemptylist

import scala.collection.mutable.ListBuffer

final class NonEmptyList[+A] private (val head: A, val tail: List[A]) {

  def ::[T >: A](x: T): NonEmptyList[T] =
    new NonEmptyList[T](x, head :: tail)

  def :::[T >: A](xs: List[T]): NonEmptyList[T] =
    xs match {
      case Nil => this
      case x :: xs => new NonEmptyList(x, xs ::: this.toList)
    }

  def :::[T >: A](xs: NonEmptyList[T]): NonEmptyList[T] =
    new NonEmptyList[T](xs.head, xs.tail ::: this.toList)

  def toList: List[A] = head :: tail

  def map[T](f: A => T): NonEmptyList[T] =
    new NonEmptyList[T](f(head), tail.map(f))

  def flatMapOld[T](f: A => NonEmptyList[T]): NonEmptyList[T] = {
    var bf = new ListBuffer[T]
    val hd = f(head)
    bf += hd.head
    bf ++= hd.tail
    tail.foreach { e =>
      val t = f(e)
      bf += t.head
      bf ++= t.tail
    }
    val bbf = bf.toList
    new NonEmptyList[T](bbf.head, bbf.tail)
  }

  def flatMap[T](f: A => NonEmptyList[T]): NonEmptyList[T] = {
    var bf = new ListBuffer[T]
    (head :: tail).foreach { e =>
      val r = f(e)
      bf += r.head
      bf ++= r.tail
    }
    new NonEmptyList[T](bf.head, bf.tail.toList)
  }

  override def toString: String = s"NonEmpty${head :: tail}"

  override def equals(obj: Any): Boolean =
    obj match {
      case that: NonEmptyList[_] => this.toList == that.toList
      case _ => false
    }
}

object NonEmptyList {
  def apply[A](head: A, tail: List[A]): NonEmptyList[A] =
    new NonEmptyList(head, tail)

  def apply[A](head: A, tail: A*): NonEmptyList[A] =
    new NonEmptyList[A](head, tail.toList)

  implicit class RichIterable[A](val iter: Iterable[A]) {
    def toNonEmptyList: NonEmptyList[A] = {
      val lst = iter.toList
      lst match {
        case Nil => throw new CannotConvertException("Nil cannot be converted to NonEmptyList")
        case _ => NonEmptyList(lst.head, lst.tail)
      }
    }

    class CannotConvertException(
                                  message: String = null,
                                  throwable: Throwable = null
                                ) extends RuntimeException(message, throwable)
  }
}
