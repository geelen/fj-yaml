package test

import fjs.test.Property._
import fjs.test.Bool._
import fjs.test.Arbitrary.SBoundaries.arbSIntBoundaries
import fjs.test.Shrink.shrinkSInt
import fj.test.CheckResult.summary

/*
Adding two positive integers, a and b, results in a positive integer. This is not true, since the
integer value may overflow during the addition resulting in a negative value. This property is
tested with shrinking; note the reasonably small counter-example.
*/
object IntegerOverflow extends Application {
  val p = property((a: Int, b: Int) => (a > 0 && b > 0) -> (a + b > 0)) 
  summary println +p // Falsified after 1 passed test with arguments: [6,2147483647]
}
