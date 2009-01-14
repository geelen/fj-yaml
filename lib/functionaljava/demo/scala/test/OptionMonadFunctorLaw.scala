package test

import fjs.test.Property._
import fjs.test.Arbitrary.{arbString, arbSF}
import fjs.test.Coarbitrary.coarbSInt
import fj.test.CheckResult.summary

/*
For any Option (o) and any function (f), then calling flatMap on o with a
function that puts its result in Some is equivalent to calling map on o with f.
*/
object OptionMonadFunctorLaw extends Application {
  val unitMap = prop((o: Option[Int], f: Int => String) =>
          o.flatMap(f andThen (Some(_: String))) == (o map f))

  summary println unitMap.minSuccessful(500) // OK, passed 500 tests.
}
