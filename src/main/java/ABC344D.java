import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC344D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        String t = scanner.next();
        int N = scanner.nextInt();
        List<String>[] list = new List[N];
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();
            for (int j = 0; j < a; j++) {
                list[i].add(scanner.next());
            }
        }
        int[][] dp = new int[N + 1][t.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i - 1][j] >= 0) {
                    StringBuilder sb = new StringBuilder(t.substring(0, j));
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                    //System.out.println(sb);
                    for (String s : list[i - 1]) {
                        sb.append(s);
                        if (t.startsWith(sb.toString())) {
                            dp[i][j + s.length()] = Math.min(dp[i][j + s.length()], dp[i - 1][j] + 1);
                        }
                        sb.delete(sb.length() - s.length(), sb.length());
                    }
                }
            }
        }
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
        }
        if (dp[N][t.length()] != INF) {
            System.out.println(dp[N][t.length()]);
        } else {
            System.out.println(-1);
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
