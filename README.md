# NonEmptyList

This is a prototyping implementation of NonEmptyList for Scala collection library.

## Usage

```scala
scala> NonEmptyList(1, 2, 3)
res1: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3)

scala> 1 :: NonEmptyList(2, 3, 4)
res1: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3, 4)

scala> NonEmptyList()
<console>:15: error: overloaded method value apply with alternatives:
  [A](head: A, tail: A*)org.micchon.nonemptylist.NonEmptyList[A] <and>
  [A](head: A, tail: List[A])org.micchon.nonemptylist.NonEmptyList[A]
 cannot be applied to ()
       NonEmptyList()
       ^
```