import java.util.Scanner;

public class ABC341B {
    public static void main(final String[] args) {
      Scanner scanner = new Scanner(System.in);
      int n = scanner.nextInt();
      long[] a = new long[n];
      long[][] rate = new long[n][2];
      for (int i = 0; i < n; i++) {
          a[i] = scanner.nextInt();
      }
      for (int i = 0; i < n - 1; i++) {
          int s = scanner.nextInt();
          int t = scanner.nextInt();
          rate[i][0] = s;
          rate[i][1] = t;
      }
      for (int i = 0; i < n- 1; i++) {
          a[i + 1] += (a[i] / rate[i][0]) * rate[i][1];
      }
      System.out.println(a[n-1]);
    }
}