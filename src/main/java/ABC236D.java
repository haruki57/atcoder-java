import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class ABC236D {
  static int N;
  static int[][] p;
  static int ans = 0;

  public static void main(final String[] args) {
    Scanner scanner = new Scanner(System.in);
    N = scanner.nextInt() * 2;
    p = new int[N + 1][N + 1];
    for (int i = 1; i <= N; i++) {
      for (int j = i + 1; j <= N; j++) {
        p[i][j] = scanner.nextInt();
        p[j][i] = p[i][j];
      }
    }
    rec(0, new boolean[N+1], 0);
    System.out.println(ans);
  }
    
  static void rec(int depth, boolean[] used, int val) {
    if (depth == N) {
      //System.out.println(val);
      ans = Math.max(ans, val);
      return;
    }

    int p1 = 0, p2 = 0;
    for (int i = 1; i <= N; i++) {
      if (!used[i]) {
        p1 = i;
        break;
      }
    }

    used[p1] = true;
    for (int i = 1; i < used.length; i++) {
      if (!used[i]) {
        p2 = i;
        used[p2] = true;
        rec(depth + 2, used, val ^ p[p1][p2]);
        used[p2] = false;
      }
    }
    used[p1] = false;
  }
}