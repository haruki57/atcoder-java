import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC324D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static Set<char[]> set = new HashSet<>();
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        for (long i = 0; i * i <= (long)Math.pow(10, N); i++) {
            set.add(pad(String.valueOf(i*i), '0', N));
        }
        int ans = 0;
        int[] cntS = new int[10];
        for (char c : s) {
            cntS[c-'0']++;
        }
        int[] cntT = new int[10];
        for (char[] t : set) {
            if (t.length != N) {
                continue;
            }
            Arrays.fill(cntT, 0);
            for (char c : t) {
                cntT[c-'0']++;
            }
            if (Arrays.equals(cntS, cntT)) {
                ans++;
            }
        }
        System.out.println(ans);
    }
    static char[] pad(String s, char c, int len) {
        if (s.length() >= len) {
            return s.toCharArray();
        }
        char[] ret = new char[len];
        for (int i = 0; i < len - s.length(); i++) {
            ret[i] = c;
        }
        for (int i = 0; i < s.length(); i++) {
            ret[i + len - s.length()] = s.charAt(i);
        }
        return ret;
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
