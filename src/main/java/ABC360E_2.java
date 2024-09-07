import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC360E_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextInt();
        int K = scanner.nextInt();

        // dp[i]: i番目の走査が終わったときにボールが一番左にある確率
        long[] dp = new long[(int)K+1];
        dp[0] = 1;

        // 2*(N-1)/N*N
        // ボールが左にあるときに、左以外に行く確率
        long moveOthers = pq((2*(N-1+MOD))%MOD, N*N%MOD, MOD);

        // 2/N*N
        // ボールが左以外にあるときに、左に来る確率。
        long moveMostLeft = pq(2, N*N%MOD, MOD);
        for (int i = 1; i < dp.length; i++) {
            dp[i] = (
                    (1-dp[i-1]+MOD)*moveMostLeft%MOD +
                            (dp[i-1]*(1-moveOthers+MOD)%MOD)
            )%MOD;
        }

        // 一番左以外にある確率
        long othersP = pq((1-dp[K]+MOD), N-1, MOD);

        // 2 + 3 + ... N
        long sum = (N*(N+1)/2-1)%MOD;
        System.out.println((dp[K] + othersP*sum)%MOD);
    }

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
