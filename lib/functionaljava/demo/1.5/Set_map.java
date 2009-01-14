import fj.F;
import fj.data.Set;
import static fj.data.Set.empty;
import static fj.pre.Show.intShow;
import static fj.pre.Show.listShow;
import fj.pre.Ord;

public final class Set_map {
  public static void main(final String[] args) {
    final Set<Integer> a = empty(Ord.intOrd).insert(1).insert(2).insert(3).insert(4).insert(5).insert(6);
    final Set<Integer> b = a.map(Ord.intOrd, new F<Integer, Integer>() {
      public Integer f(final Integer i) {
        return i / 2; // divide each integer by 2
      }
    });
    listShow(intShow).println(b.toList()); // [3,2,1,0]
  }
}
