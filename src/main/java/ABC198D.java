import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC198D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static String NO = "UNSOLVABLE";

    static char[] s1, s2, s3;
    static int[] map = new int[256];
    static void run (final FastScanner scanner, final PrintWriter out) {
        s1 = scanner.next().toCharArray();
        s2 = scanner.next().toCharArray();
        s3 = scanner.next().toCharArray();
        var allCharacters = new TreeSet<Character>();
        for (char[] chars : new char[][]{s1, s2, s3}) {
            for (char c : chars) {
                allCharacters.add(c);
            }
        }
        if (allCharacters.size() > 10) {
            System.out.println("UNSOLVABLE");
            return;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int[] perm : new Permutations(10).perms) {
            int ii = 0;
            for (Character c : allCharacters) {
                map.put(c, perm[ii]);
                ii++;
            }
            //System.out.println(map);
            char[] a1= s1;
            char[] a2= s2;
            char[] a3= s3;
            if (map.get(a1[0])==0 || map.get(a2[0])==0 || map.get(a3[0])==0){
                continue;
            }
            long num1 = toNum(a1, map);
            long num2 = toNum(a2, map);
            long num3 = toNum(a3, map);
            if (num1+num2==num3) {
                System.out.println(num1);
                System.out.println(num2);
                System.out.println(num3);
                return;
            }
            map = new HashMap<>();
        }
        System.out.println("UNSOLVABLE");
    }

    private static long toNum(char[] a1, Map<Character, Integer> map) {
        long ret = 0;
        for (int i = 0; i < a1.length; i++) {
            int num = map.get(a1[i]);
            ret *= 10;
            ret += num;
        }
        return ret;
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

    public static void main(final String[] args) {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner scanner = new FastScanner();
        try {
            run(scanner, out);
        } catch (Throwable e) {
            throw e;
        } finally {
            out.flush();
        }
    }

    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;
        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            }else{
                ptr = 0;
                try {
                    buflen = in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (buflen <= 0) {
                    return false;
                }
            }
            return true;
        }
        private int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
        private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
        public boolean hasNext() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++; return hasNextByte();}
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while(isPrintableChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }
        public long nextLong() {
            if (!hasNext()) throw new NoSuchElementException();
            long n = 0;
            boolean minus = false;
            int b = readByte();
            if (b == '-') {
                minus = true;
                b = readByte();
            }
            if (b < '0' || '9' < b) {
                throw new NumberFormatException();
            }
            while(true){
                if ('0' <= b && b <= '9') {
                    n *= 10;
                    n += b - '0';
                }else if(b == -1 || !isPrintableChar(b)){
                    return minus ? -n : n;
                }else{
                    throw new NumberFormatException();
                }
                b = readByte();
            }
        }
        public int nextInt() {
            long nl = nextLong();
            if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
            return (int) nl;
        }
        public double nextDouble() { return Double.parseDouble(next());}
    }
}
