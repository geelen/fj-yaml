package test

import fjs.test.Property._
import fjs.test.Bool._
import fjs.test.Arbitrary.{arbSByte, arbSChar}
import fj.test.CheckResult.summary

/*
Given the equals and hashCode implementation of MyClass, then check that if two
instances (m1 and m2) are equal, then the two hash codes are also equal.

(m1 == m2) -> (m1.hashCode == m2.hashCode)

Note this requires a custom Arbitrary to generate MyClass instances that are
more likely to be equal, since otherwise, you'd be waiting a long time for the
generator to produce values where this premise holds true. This is achieved by
using a restrictive arbitrary for bytes and Strings (the components of MyClass).
These restrictive arbitrary implementations produces a (very small) subset of
the possible byte and String values to assist in providing a true premise (that
m1 equals m2).
*/
object EqualsHashCode extends Application {
  final case class MyClass(b: Byte, s: String) {
    override def equals(o: Any) = o != null &&
                                  o.isInstanceOf[AnyRef] &&
                                  o.asInstanceOf[AnyRef].getClass == classOf[MyClass] &&
                                  b == o.asInstanceOf[MyClass].b &&
                                  s == o.asInstanceOf[MyClass].s                        

    override def hashCode = {
      val p = 419
      var result = 239
      result = p * result + b
      result = p * result + s.hashCode
      result
    }
  }


  val arbByteR = arbSByte > (b => (b % 3).asInstanceOf[Byte])

  val arbStringR = arbSChar >>= (arbSChar, arbSChar, (c1: Char) => (c2: Char) => (c3: Char) =>
    List(c1 % 2 + 'a', c2 % 3 + 'a', c3 % 2 + 'a').mkString)

  implicit val arbMyClass = arbByteR >>= (arbStringR, (b: Byte) => (s: String) => MyClass(b, s))

  val p = prop((m1: MyClass, m2: MyClass) => (m1 == m2) -> (m1.hashCode == m2.hashCode))

  summary println p(100, 10000, 0, 100) // OK, passed 100 tests (4776 discarded).
}
