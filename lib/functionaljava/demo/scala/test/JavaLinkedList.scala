package test

import java.util.LinkedList
import java.lang.Integer
import fjs.test.Property._
import fjs.test.Arbitrary.{arbLinkedList, arbInteger}
import fj.test.CheckResult.summary

/*
For any two linked lists, the size after calling addAll on one with the other is equivalent to
adding the size of each.
*/
object JavaLinkedList extends Application {
  val p = prop((x: LinkedList[Integer], y: LinkedList[Integer]) => {
    val xy = new LinkedList[Integer](x)
    xy addAll y
    xy.size == x.size + y.size
  })
  summary println +p // OK, passed 100 tests.
}
