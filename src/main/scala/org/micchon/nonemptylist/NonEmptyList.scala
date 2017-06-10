package org.micchon.nonemptylist

import com.sun.jndi.toolkit.ctx.HeadTail

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

final class NonEmptyList[+A] private (val head: A, val tail: List[A]) {

  def ::[T >: A](x: T): NonEmptyList[T] =
    new NonEmptyList[T](x, head :: tail)

  def :::[T >: A](xs: List[T]): NonEmptyList[T] =
    xs match {
      case Nil => this
      case x :: xs => new NonEmptyList(x, xs ::: (head :: tail))
    }

  def :::[T >: A](xs: NonEmptyList[T]): NonEmptyList[T] =
    new NonEmptyList[T](xs.head, xs.tail ::: (head :: tail))

  def toList: List[A] = head :: tail

  def map[T](f: A => T): NonEmptyList[T] =
    new NonEmptyList[T](f(head), tail.map(f))

  def flatMap[T](f: A => NonEmptyList[T]): NonEmptyList[T] = {
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
}
