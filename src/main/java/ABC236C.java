import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
public class ABC236C {
    public static void main(final String[] args) {
      Scanner scanner = new Scanner(System.in);
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      String[] stations = new String[N];
      for (int i = 0; i < stations.length; i++) {
        stations[i] = scanner.next();
      }
      Set<String> set = new HashSet<>();
      for (int i = 0; i < M; i++) {
        set.add(scanner.next());
      }
      for (int i = 0; i < stations.length; i++) {
        if (set.contains(stations[i])) {
          System.out.println("Yes");
        } else {
          System.out.println("No");
        }
      }
    }
}