import java.util.Scanner;

public class ABC236B {
    public static void main(final String[] args) {
      Scanner scanner = new Scanner(System.in);
      int N = scanner.nextInt();
      int[] a = new int[N];
      for (int i = 0; i < N * 4 - 1; i++) {
        int num = scanner.nextInt();
        a[num - 1]++;
      }
      for (int i = 0; i < a.length; i++) {
        if (a[i] == 3) {
          System.out.println(i + 1);
        }
      }
    }
}