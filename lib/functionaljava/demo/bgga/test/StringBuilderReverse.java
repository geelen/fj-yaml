package test;

import static fj.data.List.list;
import static fj.pre.Equal.stringBuilderEqual;
import static fj.test.Arbitrary.arbCharacter;
import static fj.test.Arbitrary.arbStringBuilder;
import static fj.test.CheckResult.summary;
import fj.test.Property;
import static fj.test.Property.prop;
import static fj.test.Property.property;

/*
Tests three properties about the StringBuilder.reverse method:
1) Calling reverse, then reverse again results in the same value.
2) Calling reverse on a StringBuilder with only one character results in the same value.
3) Appending two StringBuilders, x and y, then reversing, is the same as reversing the second
   StringBuilder (y) then appending the reverse of the first StringBuilder (x).
*/
public final class StringBuilderReverse {
  public static void main(final String[] args) {
    final Property p1 = property(arbStringBuilder,
      {StringBuilder sb => prop(stringBuilderEqual.eq(new StringBuilder(sb), sb.reverse().reverse()))});

    final Property p2 = property(arbCharacter,
      {char c => prop(stringBuilderEqual.eq(new StringBuilder().append(c), new StringBuilder().append(c).reverse()))});

    final Property p3 = property(arbStringBuilder, arbStringBuilder,
      {StringBuilder x, StringBuilder y =>
        // copy the string builders before performing updates on x and y.
        final StringBuilder xx = new StringBuilder(x);
        final StringBuilder yy = new StringBuilder(y);
        prop(stringBuilderEqual.eq(x.append(y).reverse(), yy.reverse().append(xx.reverse())))});

    //OK, passed 100 tests.
    //OK, passed 100 tests.
    //OK, passed 100 tests.
    list(p1, p2, p3).foreach({Property p => summary.println(p.check())});
  }
}
