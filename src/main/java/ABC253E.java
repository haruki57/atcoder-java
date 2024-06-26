import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC253E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    private static long powWithMod(long a, long b, int mod) {
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

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();
        if (K==0) {
            System.out.println(powWithMod(M, N, MOD));
            return;
        }
        long[][] dp = new long[N][M+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j]=0;
            }
        }
        long[] sum = new long[M+2];
        for (int i = 1; i < M + 1; i++) {
            dp[0][i]=1;
            sum[i+1]=sum[i]+1;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M + 1; j++) {
                int jj = j-K+1;
                int jjj = j+K;
                if (jj>=0) {
                    dp[i][j] += sum[jj];
                    dp[i][j]%=MOD;
                }
                if (jjj<sum.length) {
                    dp[i][j] += (sum[sum.length-1] - sum[jjj]+MOD);
                    dp[i][j]%=MOD;
                }
            }
            Arrays.fill(sum,0);
            for (int j = 1; j < M + 1; j++) {
                sum[j+1]=sum[j]+dp[i][j];
                sum[j+1]%=MOD;
            }
        }
        long ans = 0;
        for (int i = 0; i < dp[0].length; i++) {
            ans += dp[dp.length-1][i];
            ans %= MOD;
        }
        System.out.println(ans);
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
