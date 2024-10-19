import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC374E_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long X = scanner.nextInt();
        int[] v1  = new int[N];
        long[] w1  = new long[N];
        int[] v2  = new int[N];
        long[] w2  = new long[N];
        for (int i = 0; i < N; i++) {
            v1[i]= scanner.nextInt();
            w1[i]= scanner.nextInt();
            v2[i]= scanner.nextInt();
            w2[i]= scanner.nextInt();
        }
        //
        int ok = 1000000009, ng = -1;
        //int ok = 100, ng = -1;
        while(Math.abs(ok-ng) > 1) {
            int mid = (ok+ng)/2;

            // wを達成する最小コストを各iで考えて、全部足す。足した合計がX以下ならok
            long x = 0;

            for (int i = 0; i < N; i++) {
                long[] dp = new long[mid+109];
                Arrays.fill(dp, Long.MAX_VALUE/4);
                dp[0] = 0;
                for (int j = 0; j < dp.length; j++) {
                    if(j+v1[i] < dp.length) {
                        dp[j+v1[i]] = Math.min(dp[j+v1[i]], dp[j]+w1[i]);
                    }
                    if(j+v2[i] < dp.length) {
                        dp[j+v2[i]] = Math.min(dp[j+v2[i]], dp[j]+w2[i]);
                    }
                }
                if(dp.length <= 1000){
                    System.out.println(Arrays.toString(dp));
                }
                long min = Long.MAX_VALUE;
                for (int j = 0; j < dp.length; j++) {
                    if(j >= mid) {
                        min = Math.min(min, dp[j]);
                    }
                }
                x += min;
            }
            System.out.println(mid+" "+x);
            if(x >= X) {
                ok = mid;
            } else {
                ng = mid;
            }
        }
        System.out.println(ok);
    }

    static long gcd(long x, long y) {
        if (x<y) {
            long tmp = x;
            x = y;
            y = tmp;
        }
        if (x%y==0) {
            return y;
        }

        return gcd(y, x % y);
    }
    static long lcm(long x, long y) {
        return x / gcd(x, y) * y;
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