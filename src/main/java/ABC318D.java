import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC318D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                graph[i][j]=graph[j][i]= scanner.nextInt();
            }
        }
        long[] dp = new long[(1<<N)];
        long max = 0;
        for (int prevBit = 0; prevBit < dp.length; prevBit++) {
            if (Integer.bitCount(prevBit) % 2 == 1) {
                continue;
            }
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    int currentBit = prevBit + (1<<j) + (1<<k);
                    if (Integer.bitCount(prevBit) +2!= Integer.bitCount(currentBit)) {
                        continue;
                    }
                    /*
                    System.out.println(Integer.toBinaryString(prevBit));
                    System.out.println(Integer.toBinaryString(currentBit));
                    System.out.println(Integer.toBinaryString(prevBit)+" "+j+" "+k);
                    System.out.println(dp[prevBit]+" "+graph[j][k]);
                     */
                    dp[currentBit] = Math.max(dp[currentBit], dp[prevBit]+graph[j][k]);
                    max = Math.max(dp[currentBit],max);
                }
            }

        }
        System.out.println(max);
    }

    static long bt(int depth, int[][] g, int bit, long cost) {
        if (g.length - depth <= 1) {
            return cost;
        }
        long ret = 0;
        for (int i = 0; i < g.length; i++) {
            for (int j = i+1; j < g.length; j++) {
                if (i==j) {
                    continue;
                }
                if (((1<<i)&bit) > 0) {
                    continue;
                }
                if (((1<<j)&bit) > 0) {
                    continue;
                }
                ret = Math.max(ret, bt(depth+2, g, bit + (1<<i) + (1<<j), cost + g[i][j]));
            }
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
