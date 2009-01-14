package test

import fjs.test.Property._
import fj.test.reflect.CheckParams
import fj.test.CheckResult.summary
import fjs.test.Arbitrary.{arbSList, arbString, arbSInt, arbSF, arbSLong}
import fjs.test.Coarbitrary.{coarbSInt, coarbSLong}
import fjs.F._
import fjs.test.reflect.Check.check

/*
Checks the two functor laws on List.map. These laws are:
1) The Law of Identity
forall x. map identity x == x

For any list, mapping the identity function (\x -> x) produces the same list.

2) The Law of Composition
forall f. forall g. forall x. map (f . g) x == map f (map g x)

...where (f . g) denotes composition of f with g. That is, \c -> f(g(c)).

Note that to test this second law requires the generation of arbitrary functions.
*/
@CheckParams { val minSuccessful = 1000 }
object ListFunctorLaws {
  def identity = prop((x: List[String]) => x == x.map(t => t))

  def composition = prop((f: Int => String, g: Long => Int, x: List[Long]) =>
                      (x map (f compose g)) == x.map(g).map(f))

  // identity: OK, passed 1000 tests.
  // composition: OK, passed 1000 tests.
  def main(args: Array[String]) {
    check(ListFunctorLaws.getClass) foreach {
      case (n, r) => {
        print(n + " : ")
        summary println r
      }
    }
  }
}
