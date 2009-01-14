package test

import fjs.test.Property._
import fjs.test.Arbitrary.arbSInt
import fj.test.CheckResult.summary

/*
Any integer is either positive, zero or negative. Less succintly, isPositive applied to any integer
is equivalent in truth to the conjunction (&&) of isNegative applied to that integer and a test for
equivalence to zero.
*/
object Triangulation extends Application {
  def isNegative(i: Int) = i < 0
  def isPositive(i: Int) = true // bzzt!

  val p = prop((a: Int) => isPositive(a) == (a != 0 && !isNegative(a)))
  summary println +p // Falsified after 0 passed tests with argument: 0
}
