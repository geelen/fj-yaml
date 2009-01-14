import fj.F;
import fj.data.Array;
import static fj.data.Array.array;

public final class Array_foldLeft {
  public static void main(final String[] args) {
    final Array<Integer> a = array(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42);
    final int b = a.foldLeft(new F<Integer, F<Integer, Integer>>() {
      public F<Integer, Integer> f(final Integer i) {
        return new F<Integer, Integer>() {
          public Integer f(final Integer j) {
            return i + j; // the accumulative sum
          }
        };
      }
    }, 0);
    System.out.println(b); // 1774
  }
}
