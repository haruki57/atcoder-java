import java.util.Scanner;

public class ABC235B {
    public static void main(final String[] args) {
      Scanner scanner = new Scanner(System.in);
      int N = scanner.nextInt();
      int val = 0;
      int ans = 0;
      boolean stop = false;
      for (int i = 0; i < N; i++) {
        val = scanner.nextInt();
        if (!stop && ans < val) {
          ans = val;
        } else {

          stop = true;
        }
      }
      System.out.println(ans);
    }
}