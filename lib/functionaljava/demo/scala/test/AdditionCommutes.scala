package test

import fjs.test.Property._
import fjs.test.Arbitrary.arbSInt
import fj.test.CheckResult.summary
import fjs.F2._

/*
Checks that adding any two integers, a + b, is equivalent to b + a.
This is the commutative property of addition.
*/
object AdditionCommutes extends Application {
  val p = prop((a: Int, b: Int) => a + b == b + a)
  summary println +p // OK, passed 100 tests.
}
