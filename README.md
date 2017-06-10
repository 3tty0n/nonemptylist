# NonEmptyList

This is a prototyping implementation of NonEmptyList for Scala collection library.

## Usage

```scala
scala> NonEmptyList(1, 2, 3)
res1: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3)

scala> 1 :: NonEmptyList(2, 3, 4)
res1: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3, 4)

scala> NonEmptyList()
org.micchon.nonemptylist.NonEmptyList$CannotConvertException: Cannot create NonEmptyList()
  at org.micchon.nonemptylist.NonEmptyList$.apply(NonEmptyList.scala:59)
  ... 39 elided
```
