import fj.F;
import fj.data.List;
import static fj.data.List.list;
import static fj.pre.Show.intShow;
import static fj.pre.Show.listShow;

public final class List_map {
  public static void main(final String[] args) {
    final List<Integer> a = list(1, 2, 3);
    final List<Integer> b = a.map(new F<Integer, Integer>() {
      public Integer f(final Integer i) {
        return i + 42; // add 42 to each integer
      }
    });
    listShow(intShow).println(b); // [43,44,45]
  }
}
