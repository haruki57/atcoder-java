package lib;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Methods {

  static <T extends Comparable<T>> int lowerBound(T[] a, T n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) >= 0 ? 1 : -1);
    return ~ret;
  }
  static <T extends Comparable<T>> int upperBound(T[] a, T n) {
    int ret = Arrays.binarySearch(a, n, (x, y) -> x.compareTo(y) > 0 ? 1 : -1);
    return ~ret;
  }

  // Verified https://atcoder.jp/contests/tessoku-book/submissions/52257983
  static int lowerBound(int[] A, int x) {
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

  // Verified https://atcoder.jp/contests/abc331/submissions/56089805
  static int upperBound(int[] A, int x) {
    int l = -1, r = A.length;
    while(Math.abs(r - l) > 1) {
      int mid = (l + r) / 2;
      if(A[mid] > x) {
        r = mid;
      }
      else {
        l = mid;
      }
    }
    return r;
  }

  static class Permutations {
    private int[] perm;
    private boolean[] used;
    private int N;
    ArrayList<int[]> perms = new ArrayList<>();

    public Permutations(int n) {
      this.N=n;
      used=new boolean[N];
      perm=new int[N];
      perm(0);
    }
    private void perm(int depth) {
      if (depth == N) {
        int[] idxArr = new int[N];
        for (int i = 0; i < N; i++) {
          idxArr[i] = perm[i];
        }
        perms.add(idxArr);
        return;
      }
      for (int i = 0; i < N; i++) {
        if (used[i]) {
          continue;
        }
        used[i] = true;
        perm[depth] = i;
        perm(depth + 1);
        used[i] = false;
      }
    }
  }

  /*
        TreeMap<Long, Integer> map = new TreeMap<>();
        map.put(1L, 0);
        map.put(2L, 0);
        map.put(4L, 0);
        map.put(6L, 0);
        System.out.println(map.ceilingKey(1L));// 1
        System.out.println(map.ceilingKey(2L));// 2
        System.out.println(map.ceilingKey(3L));// 4
        System.out.println(map.ceilingKey(4L));// 4
        System.out.println(map.ceilingKey(5L));// 6
        System.out.println(map.ceilingKey(6L));// 6
        System.out.println(map.ceilingKey(7L));// null
        System.out.println();
        System.out.println(map.floorKey(0L));// null
        System.out.println(map.floorKey(1L));// 1
        System.out.println(map.floorKey(2L));// 2
        System.out.println(map.floorKey(3L));// 2
        System.out.println(map.floorKey(4L));// 4
        System.out.println(map.floorKey(5L));// 4
        System.out.println(map.floorKey(6L));// 6
        System.out.println(map.floorKey(7L));// 6
        System.out.println();

        System.out.println(map.higherKey(1L));// 2
        System.out.println(map.higherKey(2L));// 4
        System.out.println(map.higherKey(3L));// 4
        System.out.println(map.higherKey(4L));// 6
        System.out.println(map.higherKey(5L));// 6
        System.out.println(map.higherKey(6L));// null
        System.out.println(map.higherKey(7L));// null
        System.out.println();
        System.out.println(map.lowerKey(0L));// null
        System.out.println(map.lowerKey(1L));// null
        System.out.println(map.lowerKey(2L));// 1
        System.out.println(map.lowerKey(3L));// 2
        System.out.println(map.lowerKey(4L));// 2
        System.out.println(map.lowerKey(5L));// 4
        System.out.println(map.lowerKey(6L));// 4
        System.out.println(map.lowerKey(7L));// 6
   */
}
