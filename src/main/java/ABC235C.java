import java.util.Arrays;
import java.util.Scanner;

public class ABC235C {
  public static void main(final String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int Q = scanner.nextInt();
    Pair[] a = new Pair[N];
    for (int i = 0; i < N; i++) {
      int val = scanner.nextInt();
      a[i] = new Pair();
      a[i].val = val;
      a[i].pos = i + 1;
    }
    Arrays.sort(a);
    for (int i = 0; i < Q; i++) {
      int x = scanner.nextInt();
      int k = scanner.nextInt();
      Pair p = new Pair();
      p.val = x;
      int pos = lowerBound(a, p);
      //System.out.println("pos:" + pos);
      if (pos + k - 1 < N && a[pos + k - 1].val == x) {
        System.out.println(a[pos + k - 1].pos);
      } else {
        System.out.println(-1);
      }
    }
  }

  static <T extends Comparable<T>> int lowerBound(T[] a, T n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) >= 0 ? 1 : -1);
    return ~ret;
  }
  
  static class Pair implements Comparable<Pair>{
    int pos;
    int val;
    @Override
    public int compareTo(Pair arg0) {
      return val - arg0.val;
    }
  }
}