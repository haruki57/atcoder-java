import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC360E {
    static long MOD = 998244353L;
    static int INF = Integer.MAX_VALUE/2;

    static long modInv(long a, long mod) {
        long x0 = 1;
        long y0 = 0;
        long x1 = 0;
        long y1 = 1;
        long b = mod;
        while ( b != 0 ) {
            long q = a / b;
            long tmp = b;
            b = a % b;
            a = tmp;

            tmp = x1;
            x1 = x0 - q * x1;
            x0 = tmp;

            tmp = y1;
            y1 = y0 - q * y1;
            y0 = tmp;
        }
        return (x0 + mod) % mod;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextInt();
        int K = scanner.nextInt();
        long x = 1;
        long p = 2 * modInv(N*N, MOD);
        for (int i = 0; i < K; i++) {
            x = (((x * (1-(p*(N-1+MOD)%MOD))) % MOD
                    + (1-x+MOD)%MOD * p)) % MOD
                    % MOD;
        }
        // 1-x / N-1
        long xx = (1-x+MOD)%MOD * modInv(N-1, MOD) % MOD;
        long sum = (N*(N+1%MOD)%MOD*modInv(2, MOD) - 1 + MOD)%MOD;
        System.out.println((x + sum*xx)%MOD);

        // 153(=33 + 24*2+24*3) / 81
        //System.out.println(pq(17,9, MOD));// 554580198
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
