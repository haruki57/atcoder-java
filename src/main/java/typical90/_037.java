package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _037 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int W = scanner.nextInt();
        int N = scanner.nextInt();
        long[][] v = new long[N][3];
        for (int i = 0; i < N; i++) {
            v[i][0]= scanner.nextInt();
            v[i][1]= scanner.nextInt();
            v[i][2]= scanner.nextInt();
        }
        long[][] dp = new long[N+1][10000 + 9];
        //long[][] dp = new long[N+1][200];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -INF);
        }
        dp[0][0]=0;
        for (int i = 1; i <= N; i++) {
            int len = dp[i].length;
            var seg = new SegmentTree();
            seg.init(len + 1);
            for (int j = 0; j < len; j++) {
                seg.update(j+1, dp[i-1][j]);
            }

            for (int j = 0; j < len; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j]);
                int l = j-(int)v[i-1][1]+1;
                l = Math.max(l, 1);
                int r = j-(int)v[i-1][0]+2;
                r = Math.max(r, 1);
                //if(l<0 || r > len) {
//                    continue;
//                }
                long max = seg.query(l, r);
                if(max != -INF) {
                    dp[i][j]=Math.max(dp[i][j], max+v[i-1][2]);
                }
                /*
                for (int k = (int) v[i][0]; k <= v[i][1]; k++) {
                    if(j+k>=dp[i].length) {
                        break;
                    }
                    dp[i+1][j+k] = Math.max(dp[i+1][j+k], dp[i][j] + v[i][2]);
                }
                 */
            }
            /*
            for (int j = 0; j < len; j++) {
                dp[i][j]=dp[i-1][j];
                for (int k = (int)v[i-1][0]; k <= v[i-1][1]; k++) {
                    if(j-k >= 0) {
                        dp[i][j]=Math.max(dp[i][j], dp[i-1][j-k]+v[i-1][2]);
                    }
                }
            }

             */
        }
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
        }
        if(dp[N][W]<0){
            dp[N][W]=-1;
        }
        System.out.println(dp[N][W]);
    }

    // 1-indexed!
    static class SegmentTree {
        public long[] dat;
        public int size = 1;

        void init(int N) {
            size = 1;
            while(size < N) {
                size *= 2;
            }
            dat = new long[size * 2 + 9];
            Arrays.fill(dat, 0);
        }

        void update(int pos, long x) {
            pos = pos + size - 1;
            dat[pos] = x;
            while(pos >= 2) {
                pos /= 2;
                dat[pos] = Math.max(dat[pos * 2], dat[pos * 2 + 1]);
            }
        }

        long query (int l, int r, int a, int b, int u) {
            if (r <= a || b <= l) {
                return -INF;
            }
            if (l <= a && b <= r) {
                return dat[u];
            }
            int m = (a + b) / 2;
            long answerL = query(l, r, a, m, u*2);
            long answerR = query(l, r, m, b, u*2 + 1);
            return Math.max(answerL, answerR);
        }

        long query(int l, int r) {
            return query(l, r, 1, size + 1, 1);
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
