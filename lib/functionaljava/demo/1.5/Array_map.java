import fj.F;
import fj.data.Array;
import static fj.data.Array.array;
import static fj.pre.Show.arrayShow;
import static fj.pre.Show.intShow;

public final class Array_map {
  public static void main(final String[] args) {
    final Array<Integer> a = array(1, 2, 3);
    final Array<Integer> b = a.map(new F<Integer, Integer>() {
      public Integer f(final Integer i) {
        return i + 42; // add 42 to each integer
      }
    });
    arrayShow(intShow).println(b); // {43,44,45}
  }
}
