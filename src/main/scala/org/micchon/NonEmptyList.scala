package org.micchon.nonemptylist

import scala.collection.mutable.ListBuffer

final class NonEmptyList[+A] private (val head: A, val tail: List[A]) {

  def ::[B >: A](x: B): NonEmptyList[B] =
    new NonEmptyList[B](x, head :: tail)

  def :::[B >: A](xs: List[B]): NonEmptyList[B] =
    xs match {
      case Nil => this
      case x :: xs => new NonEmptyList(x, xs ::: this.toList)
    }

  def :::[B >: A](xs: NonEmptyList[B]): NonEmptyList[B] =
    NonEmptyList[B](xs.head, xs.tail ::: this.toList)

  def ++[B >: A](l: List[B]): NonEmptyList[B] =
    NonEmptyList[B](head, tail ++ l)

  def toList: List[A] = head :: tail

  def size: Int = 1 + tail.size

  def length: Int = size

  def reverse: NonEmptyList[A] = (this.toList.reverse: @unchecked) match {
    case x :: xs => NonEmptyList(x, xs)
  }

  def map[B](f: A => B): NonEmptyList[B] =
    NonEmptyList[B](f(head), tail.map(f))

  def flatMap[B](f: A => NonEmptyList[B]): NonEmptyList[B] = {
    val bf = new ListBuffer[B]
    (head :: tail).foreach { e =>
      val r = f(e)
      bf += r.head
      bf ++= r.tail
    }
    NonEmptyList[B](bf.head, bf.tail.toList)
  }

  def collect[B](pf: PartialFunction[A, B]): List[B] = {
    if (pf.isDefinedAt(head)) {
      pf.apply(head) :: tail.collect(pf)
    } else {
      tail.collect(pf)
    }
  }

  def filter(f: A => Boolean): List[A] = {
    if (f(head)) head :: tail.filter(f)
    else tail.filter(f)
  }

  def filterNot(f: A => Boolean): List[A] = {
    if (!f(head)) head :: tail.filterNot(f)
    else tail.filterNot(f)
  }

  def forall(f: A => Boolean): Boolean = {
    if (f(head)) {
      tail match {
        case Nil => true
        case x :: xs => if (f(x)) xs.forall(f) else false
        case _ => false
      }
    } else false
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

  def apply[A](): NonEmptyList[A] =
    throw new CannotCreateNonEmptyListException("Cannot create NonEmptyList()")

  def unapply[A](nel: NonEmptyList[A]): Option[(A, List[A])] =
    Some(nel.head, nel.tail)

  implicit class RichItcerable[A](val iterable: Iterable[A]) {
    def toNonEmptyList: NonEmptyList[A] = {
      val lst = iterable.toList
      lst match {
        case Nil => throw new CannotConvertException("List() cannot be converted to NonEmptyList")
        case x :: xs => NonEmptyList(x, xs)
      }
    }
  }

  class CannotConvertException(
    message: String = null,
    throwable: Throwable = null
  ) extends RuntimeException(message, throwable)

  class CannotCreateNonEmptyListException(
    message: String = null,
    throwable: Throwable = null
  ) extends RuntimeException(message, throwable)
}