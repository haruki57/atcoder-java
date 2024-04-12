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

  // Verified https://atcoder.jp/contests/tessoku-book/submissions/52257983
  int lowerBound(int[] A, int x) {
    int l = -1, r = A.length;
    while(Math.abs(r - l) > 1) {
      int mid = (l + r) / 2;
      if(A[mid] >= x) {
        r = mid;
      }
      else {
        l = mid;
      }
    }
    return r;
  }
}
