package lib;
import java.util.Arrays;
public class Methods {

  <T extends Comparable<T>> int lowerBound(T[] a, T n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) >= 0 ? 1 : -1);
    return ~ret;
  }

  int upperBound(Integer[] a, int n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) > 0 ? 1 : -1);
    return ~ret;
  }

  // Not Verified
  int binarySearch(int[] a, int n) {
    int ok = a.length + 1, ng = -1;
    while (ok - ng > 1) {
      int mid = (ok + ng) / 2;
      if(a[mid] >= n) {
        ok = mid;
      } else {
        ng = mid;
      }
    }
    return ok;
  }
}
