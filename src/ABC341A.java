import java.util.Scanner;

public class ABC341A {
    public static void main(final String[] args) {
      Scanner scanner = new Scanner(System.in);
      int n = scanner.nextInt();
        for (int i = 0; i < n + (n + 1); i++) {
            if (i %2 == 0) {
                System.out.print("1");
            } else {
                System.out.print("0");
            }
        }
        System.out.println();
    }
}