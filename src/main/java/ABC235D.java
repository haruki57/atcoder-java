import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ABC235D {
  static int a, N;
  static Map<Integer, Integer> map = new HashMap<>();

  public static void main(final String[] args) {
    Scanner scanner = new Scanner(System.in);
     a = scanner.nextInt();
     N = scanner.nextInt();
     Queue<int[]> q = new LinkedList<>();
     q.add(new int[] { 1, 0 });
     while (!q.isEmpty()) {
       int[] pop = q.poll();
       int val = pop[0];
       int depth = pop[1];
       if (val > N * 10) {
         continue;
       }
       if (map.containsKey(val)) {
         continue;
       }
       map.put(val, depth);
       if (can(val)) {
         int firstDigit = val % 10;
         int next = val / 10;
         String nextStr = ("" + firstDigit) + next;
         next = Integer.parseInt(nextStr);
         if (!map.containsKey(next)) {
           q.add(new int[] { next, depth + 1 });
         }
       }
       if (!map.containsKey(val * a)) {
         q.add(new int[] { val * a, depth + 1 });
       }
     }
     if (!map.containsKey(N)) {
       System.out.println(-1);
     } else {
       System.out.println(map.get(N));
     }
  }
  
  static void rec(int val, int depth) {
    if (map.containsKey(val) && map.get(val) >= depth) {
      return;
    }
    if (val > N * 11) {
      return;
    }
    map.put(val, depth);
    rec(val * a, depth + 1);
    if (can(val)) {
      int firstDigit = val % 10;
      int next = val / 10;
      String nextStr = ("" + firstDigit) + next;
      rec(Integer.parseInt(nextStr), depth + 1);
    }
  }

  static boolean can(int val) {
    if (val % 10 == 0) {
      return false;
    }
    if (val < 10) {
      return false;
    }
    return true;
  }
}