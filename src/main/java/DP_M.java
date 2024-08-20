import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DP_M {
    static int MOD = (int)Math.pow(10, 9)+ 7;
    static int INF = Integer.MAX_VALUE/2;

    static int[] a;
    static long[][] dp;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long[][] dp = new long[N+1][K+1];
        dp[0][0]=1;
        long[] sum = new long[K+2];
        //Arrays.fill(dp[1], 1);
        for (int i = 0; i < dp.length-1; i++) {
            Arrays.fill(sum, 0);
            for (int j = 0; j < sum.length - 1; j++) {
                sum[j+1]=sum[j]+dp[i][j];
                sum[j+1]%=MOD;
            }
            for (int j = 0; j < dp[i].length; j++) {
                dp[i+1][j]=(sum[j+1]-sum[Math.max(j-a[i], 0)]+MOD)%MOD;
            }
        }
        System.out.println(dp[N][K]);
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