import fj.data.Array;
import static fj.data.Array.array;
import static fj.pre.Show.arrayShow;
import static fj.pre.Show.intShow;

public final class Array_map {
  public static void main(final String[] args) {
    final Array<Integer> a = array(1, 2, 3);
    final Array<Integer> b = a.map({int i => i + 42});
    arrayShow(intShow).println(b); // {43,44,45}
  }
}
