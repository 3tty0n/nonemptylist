package org.micchon.nonemptylist

import org.micchon.NonEmptyList
import org.micchon.exception.CannotConvertException

object implicits {
  implicit class RichIterable[A](val iterable: Iterable[A]) {
    def toNonEmptyList: NonEmptyList[A] = {
      val lst = iterable.toList
      lst match {
        case Nil => throw new CannotConvertException("List() cannot be converted to NonEmptyList")
        case x :: xs => NonEmptyList(x, xs)
      }
    }
  }
}
