import fj.F;
import fj.data.Array;
import static fj.data.Array.array;
import static fj.pre.Show.arrayShow;
import static fj.pre.Show.intShow;

public final class Array_filter {
  public static void main(final String[] args) {
    final Array<Integer> a = array(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42);
    final Array<Integer> b = a.filter(new F<Integer, Boolean>() {
      public Boolean f(final Integer i) {
        return i % 2 == 0; // even numbers only
      }
    });
    arrayShow(intShow).println(b); // {44,22,90,98,1078,6,64,6,42}
  }
}
