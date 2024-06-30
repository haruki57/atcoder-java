import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC360E {
    static long MOD = 998244353L;
    static int INF = Integer.MAX_VALUE/2;

    private static long powWithMod(long a, long b, long mod) {
        String binaryString = Long.toBinaryString(b);
        int len = binaryString.length();
        long ret = 1;
        for (int i = 0; i < len; i++) {
            if (binaryString.charAt(len-i-1) == '1') {
                ret = (ret * a) % mod;
            }
            a = (a*a) % mod;
        }
        return ret;
    }

    static long pq(long p,long q, long mod) {
        long denominator = powWithMod(q, mod-2, mod);
        return p * denominator % mod;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextInt();
        int K = scanner.nextInt();
        /*
        long[][][] dp = new long[K+1][2][2];
        dp[0][0][0]=1;
        dp[0][0][1]=1;
        dp[0][1][0]=0;
        dp[0][1][1]=1;
        for (int i = 1; i <= K; i++) {
            dp[i][0][0]=dp[i-1][0][0]*(N+(N-1)*(N-2))+dp[i-1][1][0]*2*(N-1);
            dp[i][0][1]=dp[i][0][1]*N;
            dp[i][1][0]=dp[i-1][1][0]*(N+(N-1)*(N-2))+dp[i-1][0][0]*2*(N-1);
            dp[i][1][1]=dp[i][1][1]*N;
        }*/

        long[][] dp = new long[K+1][2];
        dp[0][0]=1;
        dp[0][1]=0;
        for (int i = 1; i <= K; i++) {
            dp[i][0]=dp[i-1][0]*(N*(N-1))+dp[i-1][1]*(N-1)*2;
            dp[i][0]%=MOD;
            dp[i][1]=dp[i-1][1]*(N*(N-1))+dp[i-1][0]*2*(N-1);
            dp[i][1]%=MOD;
        }

        for (int i = 0; i <= K; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }

        // 153(33 + 24*2+24*3) / 81
        System.out.println(pq(17,9, MOD));// 554580198
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
