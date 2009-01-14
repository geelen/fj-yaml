package test

import fjs.pre.Equal.{equal, stringBuilderEqual}
import fjs.test.Arbitrary.SBoundaries.arbSIntBoundaries
import fjs.test.Bool._
import fjs.test.Property
import fjs.test.Property._
import fjs.test.Shrink.shrinkSInt
import java.lang.StringBuilder
import fjs.test.reflect.Check.check
import fj.test.CheckResult
import fj.test.CheckResult.summary
import fj.test.reflect.{Category, CheckParams, Name, NoCheck}

/*
  Performs four test runs of these properties using various test parameters and categories.

  A test property is any of the following:
    - a static field of type fj.test.Property.
    - a static no-argument method that returns fj.test.Property.
    - a non-static field of type fj.test.Property in a class with a no-argument constructor.
    - a non-static no-argument method that returns fj.test.Property in a class with a no-argument
      constructor.

  Any property annotated with fj.test.reflect.NoCheck will not be checked. A property's categories
  are the union of the reduction.reflect.Category values of the enclosing class and itself. Default
  check parameters can be overridden with the reduction.reflect.CheckParams annotation. A property
  can have a name associated with it, that is first taken from its fj.test.reflect.Name
  annotation and if it doesn't exist, then the same annotation on the enclosing class.
*/
@CheckParams { val minSuccessful = 500 }
@Category(Array("fj.test.reflect demo"))
object Reflect {
  @Name("Integer Addition Commutes")
  @Category(Array("Passes for demo"))
  @CheckParams { val minSuccessful = 20 }
  def p1 = prop((a: Int, b: Int) => a + b == b + a)

  @Name("Natural Integer Addition yields Natural Integer")
  @Category(Array("Fails for demo"))
  @CheckParams{ val minSuccessful = 1618034, val maxDiscarded = 1346269 }
  def p2 = property((a: Int, b: Int) => (a > 0 && b > 0) -> (a + b > 0))

  @Category(Array("Passes for demo"))
  def p3 = prop((sb: StringBuilder) => equal(sb.reverse.reverse, new StringBuilder(sb)))

  @Category(Array("Passes for demo"))
  def p4 = prop((c: Char) => equal(new StringBuilder append c, (new StringBuilder append c).reverse))

  @Category(Array("Passes for demo"))
  def p5 = prop((x: StringBuilder, y: StringBuilder) => {
      val xx = new StringBuilder(x)
      val yy = new StringBuilder(y)
      equal((x append y).reverse, yy.reverse append xx.reverse)
    })

  @Name("Triangulation")
  @Category(Array("Fails for demo"))
  def p6 = prop((a: Int) => Triangulation.isPositive(a) == (a != 0 && !Triangulation.isNegative(a)))

  @NoCheck
  def leave: Property = error("this should not be executed")

  /*
  Falsified after 0 passed tests with argument: 0
  OK, passed 20 tests. (Integer Addition Commutes)
  Falsified after 0 passed tests with arguments: [2147483647,1] (Natural Integer Addition yields Natural Integer)
  Falsified after 0 passed tests with argument: 0 (Triangulation)
  OK, passed 500 tests. (p5)
  OK, passed 500 tests. (p4)
  OK, passed 500 tests. (p3)
  --------------------------------------------------------------------------------
  OK, passed 20 tests. (Integer Addition Commutes)
  OK, passed 500 tests. (p5)
  OK, passed 500 tests. (p4)
  OK, passed 500 tests. (p3)
  --------------------------------------------------------------------------------
  Falsified after 1 passed test with arguments: [2147483647,2147483647] (Natural Integer Addition yields Natural Integer)
  Falsified after 0 passed tests with argument: 0 (Triangulation)
  --------------------------------------------------------------------------------
  OK, passed 20 tests. (Integer Addition Commutes)
  Falsified after 1 passed test with arguments: [2147483647,2147483647] (Natural Integer Addition yields Natural Integer)
  Falsified after 0 passed tests with argument: 0 (Triangulation)
  OK, passed 500 tests. (p5)
  OK, passed 500 tests. (p4)
  OK, passed 500 tests. (p3)
  --------------------------------------------------------------------------------
  */
  def main(args: Array[String]) {
    printResults(check(List(Reflect.getClass)));
    // execute only those in the given category
    printResults(check(List(Reflect.getClass), "Passes for demo"));
    printResults(check(List(Reflect.getClass), "Fails for demo"));
    printResults(check(List(Reflect.getClass), "fj.test.reflect demo"));
  }

  def printResults(results: List[(String, CheckResult)]) = {
    results.foreach { case (n, r) =>
      summary print r
      println(" (" + n + ')')
    }
    println("--------------------------------------------------------------------------------")
  }
}
