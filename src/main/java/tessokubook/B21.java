package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class B21 {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] xy = new int[N][2];
        long[] X = new long[N];
        long[] Y = new long[N];
        for (int i = 0; i < N; i++) {
            //xy[i][0]= scanner.nextInt();
            //xy[i][1]= scanner.nextInt();
            X[i]= scanner.nextInt();
            Y[i]= scanner.nextInt();
        }
        double[][] dp = new double[1<<N][N];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0]=0;

        for (int i = 0; i < 1 << N; i++) {
            for (int j = 0; j < N; j++) {
                if (dp[i][j]>=INF) {
                    continue;
                }

                for (int k = 0; k < N; k++) {
                    if ((i / (1 << k)) % 2 == 1) {
                        continue;
                    }
                    /*
                    if((i&(1<<k))>0) {
                        continue;
                    }

                     */
                    int nbit = i+(1<<k);
                    double DIST = Math.sqrt((X[j]-X[k])*(X[j]-X[k]) + (Y[j]-
                            Y[k])*(Y[j]-Y[k]));
                    dp[nbit][k]=Math.min(dp[nbit][k], dp[i][j]+DIST);
                }
            }
        }
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println((dp[(1<<N)-1][0]));
    }

    private static int dist(int[][] xy, int i, int j) {
        return (xy[i][0]-xy[j][0])*(xy[i][0]-xy[j][0]) +
                (xy[i][1]-xy[j][1])*(xy[i][1]-xy[j][1]);
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
