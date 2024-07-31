import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class DP_P {
    static int MOD = (int)1e9+7;
    static long lINF = Long.MAX_VALUE/2;

    static long[][] dp;
    static List<Integer>[] g;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        //int N = 100000;
        g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int x = scanner.nextInt()-1;
            int y = scanner.nextInt()-1;
            //int x = i;
            //int y = i+1;
            g[x].add(y);
            g[y].add(x);
        }
        dp = new long[N][2];
        dfs(0, -1);
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println((dp[0][0]+dp[0][1])%MOD);
    }

    static void dfs(int cur, int parent) {
        dp[cur][0] = 1;
        dp[cur][1] = 1;
        for (int next : g[cur]) {
            if (next != parent) {
                dfs(next, cur);
                dp[cur][0] *= dp[next][0] + dp[next][1]; // white
                dp[cur][0] %= MOD;
                dp[cur][1] *= dp[next][0]; // black
                dp[cur][1] %= MOD;
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
