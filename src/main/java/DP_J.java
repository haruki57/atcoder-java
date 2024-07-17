import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DP_J {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static double rec(int ii, int jj, int kk) {
        if (dp[ii][jj][kk]>0){
            return dp[ii][jj][kk];
        }
        if (ii+jj+kk==0) {
            return 0;
        }
        double ret = 0;
        if (ii > 0) {
            ret += rec(ii-1,jj, kk)*ii;
        }
        if (jj > 0) {
            ret += rec(ii+1, jj-1, kk)*jj;
        }
        if (kk > 0) {
            ret += rec(ii, jj+1, kk-1)*kk;
        }
        ret += N;
        ret *= 1.0 / (ii+jj+kk);
        return dp[ii][jj][kk]=ret;
    }
    static int N;

    static double[][][] dp;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < a.length; i++) {
            a[i]= scanner.nextInt();
        }
        dp = new double[N+1][N+1][N+1];
        int[] counts = new int[4];
        for (int i = 0; i < a.length; i++) {
            counts[a[i]]++;
        }
        System.out.println(rec(counts[1], counts[2],counts[3]));
        /*
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                for (int k = 0; k <= N; k++) {
                    if(i>0) {
                        dp[i][j][k]+=dp[i-1][j][k]*i/N;
                    }
                    if (j>0) {
                        dp[i][j][k]+=dp[i][j-1][k]*j/N;
                    }
                    if (k>0){
                        dp[i][j][k]+=dp[i][j][k-1]*k/N;
                    }
                }
            }
        }
         */

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
