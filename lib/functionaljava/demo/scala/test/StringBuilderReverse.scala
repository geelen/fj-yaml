package test

import fjs.test.Property._
import fjs.test.Arbitrary.{arbSChar, arbStringBuilder}
import fj.test.CheckResult.summary
import java.lang.StringBuilder
import fjs.pre.Equal.{equal, stringBuilderEqual}

/*
Tests three properties about the StringBuilder.reverse method:
1) Calling reverse, then reverse again results in the same value.
2) Calling reverse on a StringBuilder with only one character results in the same value.
3) Appending two StringBuilders, x and y, then reversing, is the same as reversing the second
   StringBuilder (y) then appending the reverse of the first StringBuilder (x).
*/
object StringBuilderReverse extends Application {
  val p1 = prop((sb: StringBuilder) => equal(sb.reverse.reverse, new StringBuilder(sb)))

  val p2 = prop((c: Char) => equal(new StringBuilder append c, (new StringBuilder append c).reverse))

  val p3 = prop((x: StringBuilder, y: StringBuilder) => {
      val xx = new StringBuilder(x)
      val yy = new StringBuilder(y)
      equal((x append y).reverse, yy.reverse append xx.reverse)
    })

  //OK, passed 100 tests.
  //OK, passed 100 tests.
  //OK, passed 100 tests.
  List(p1, p2, p3) foreach (summary println +_)
}
