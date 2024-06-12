import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC219D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        int[][] ab = new int[N][2];
        for (int i = 0; i < N; i++) {
            ab[i][0]=scanner.nextInt();
            ab[i][1]=scanner.nextInt();
        }
        int size = 301;
        long[][][] dp = new long[N+1][X+1][Y+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    dp[i][j][k]=INF;
                }
            }
        }
        dp[0][0][0]=0;
        for (int i = 0; i < dp.length-1; i++) {
            //int ii = i-1;
            int a = ab[i][0];
            int b = ab[i][1];
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    /*
                    if (dp[i-1][j][k]!=INF) {
                        dp[i][j][k]=dp[i-1][j][k];
                        int jj = j + a;
                        int kk = k + b;
                        if (jj < size && kk < size) {
                            dp[i][jj][kk] = Math.min(dp[i-1][jj][kk], dp[i][j][k] + 1);
                        }
                    }
                     */
                    dp[i+1][j][k]=Math.min(dp[i+1][j][k], dp[i][j][k]);
                    int jj = Math.min(X, j+a);
                    int kk = Math.min(Y, k+b);
                    dp[i+1][jj][kk] = Math.min(dp[i+1][jj][kk], dp[i][j][k]+1);

                }
            }
            // dp[i][a][b] = 1;
        }
        long ans = dp[N][X][Y];
        if (ans < INF) {
            System.out.println(ans);
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
