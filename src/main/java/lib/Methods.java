package lib;
import java.util.Arrays;
public class Methods {

  static <T extends Comparable<T>> int lowerBound(T[] a, T n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) >= 0 ? 1 : -1);
    return ~ret;
  }

  static int upperBound(Integer[] a, int n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) > 0 ? 1 : -1);
    return ~ret;
  }
}
