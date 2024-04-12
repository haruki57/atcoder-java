package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class A76 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int W = scanner.nextInt();
        int min = scanner.nextInt();
        int max = scanner.nextInt();
        int[] x = new int[N+2];
        for (int i = 1; i < N + 1; i++) {
            x[i] = scanner.nextInt();
        }
        x[0] = 0;
        x[N+1] = W;
        Arrays.sort(x);
        int[] dp = new int[N+2];
        int[] dpSum = new int[N+2];
        dp[0] = 1;
        dpSum[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            /*
            int l=INF, r=-1;

            for (int j = 0; j < i; j++) {
                int diff = x[i]-x[j];
                if (min <= diff && diff <= max) {
                    l = Math.min(l, j);
                    r = Math.max(r, j);

                }
             }
             */

            int l = lowerBound(x, x[i]-max);
            int r = lowerBound(x, x[i]-min + 1) - 1;

            // out.println(i + " " + l + " " + r);
            if (0 <= r && r < dpSum.length) {
                dp[i] = dpSum[r];
            } else {
                dp[i] = 0;
            }

            if (0 <= l-1 && l-1 < dpSum.length) {
                dp[i] = dp[i] - dpSum[l - 1];
            }
            dp[i] = (dp[i] + MOD)%MOD;
            /*
            if (l == INF) {
                dp[i] = 0;
            } else {
                dp[i] = (dpSum[r]-(l-1<0?0:dpSum[l-1]))%MOD;
            }*/
            dpSum[i] = (dpSum[i-1]+dp[i])%MOD;
        }
        //out.println(Arrays.toString(dp));
        //out.println(Arrays.toString(dpSum));
        out.println(dp[dp.length-1]);
    }

    static int lowerBound(int[] A, int x) {
        int l = -1, r = A.length;
        while(Math.abs(r - l) > 1) {
            int mid = (l + r) / 2;
            if(A[mid] >= x) {
                r = mid;
            }
            else {
                l = mid;
            }
        }
        return r;
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
