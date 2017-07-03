# NonEmptyList

This is a prototyping implementation of NonEmptyList for Scala collection library.

## Usage

```scala
$ sbt console

scala> import org.micchon.NonEmptyList
import org.micchon.NonEmptyList

scala> NonEmptyList(1, 2, 3)
res0: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3)

scala> 1 :: NonEmptyList(2, 3, 4)
res1: org.micchon.nonemptylist.NonEmptyList[Int] = NonEmptyList(1, 2, 3, 4)

scala> NonEmptyList()
<console>:13: error: overloaded method value apply with alternatives:
  [A](head: A, tail: A*)org.micchon.NonEmptyList[A] <and>
  [A](head: A, tail: List[A])org.micchon.NonEmptyList[A]
 cannot be applied to ()
       NonEmptyList()
       ^
```
