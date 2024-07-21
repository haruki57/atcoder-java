import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC178C {
    static int MOD = (int)Math.pow(10, 9)+7;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long a = powWithMod(10, N, MOD);
        long b = powWithMod(9, N, MOD);
        long c = powWithMod(9, N, MOD);
        long d = powWithMod(8, N, MOD);
        System.out.println((a-b-c+d+MOD+MOD+MOD)%MOD);
        /*
        long[][] dp = new long[N+1][3];
        dp[1][0]=1;
        dp[1][1]=1;
        dp[1][2]=0;
        for (int i = 2; i <= N; i++) {
            //dp[i][1]=dp[i][0]=powWithMod(10, i-1,MOD)*i;
            // 0 or 9
            dp[i][2]=dp[i-1][0]+dp[i-1][1];
            // 1-8
            dp[i][2]+=dp[i-1][2]*8;

            dp[i][0]%=MOD;
            dp[i][1]%=MOD;
            dp[i][2]%=MOD;
        }
        System.out.println(Arrays.toString(dp[N-1]));
        System.out.println(Arrays.toString(dp[N]));
        System.out.println(dp[N][2]);



        long ans = 0;
        for (int i = 0; i <= Math.pow(10, N); i++) {
            int ii = i;
            boolean zero = false;
            boolean nine = false;
            for (int j = 0; j < N; j++) {
                int mod = ii%10;
                if (mod==0) {
                    zero=true;
                }
                if(mod==9) {
                    nine=true;
                }
                ii/=10;
            }
            if(zero&&nine) {
                System.out.println(i);
                ans++;
            }
        }
        System.out.println(ans);

         */
    }

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
