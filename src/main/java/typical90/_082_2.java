package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class _082_2 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long L = scanner.nextLong();
        long R = scanner.nextLong();
        long ans = 0;
        for (long i = (long)Math.pow(10, Long.toString(L).length()-1); i <= R; i*=10) {
            if(i == 1000000000000000000L) {
                ans += 1000000000000000000L%MOD*Long.toString(1000000000000000000L).length();
                break;
            }
            long digit = Long.toString(i).length();
            ans += wa(Math.max(i, L), Math.min(i*10-1, R), MOD) * digit;
            ans %= MOD;
        }
        System.out.println(ans%MOD);
    }

    // https://atcoder.jp/contests/typical90/submissions/56480590
    // https://www.try-it.jp/chapters-5324/sections-5325/lessons-5342/
    // a + (a+1) + (a+2) + ... + (b-1) + b

    // (a + (a+1) + (a+2) + ... + (b-1) + b) % mod
    static long wa(long a,long b, long mod) {
        long n = b-a+1;
        BigInteger A = BigInteger.valueOf(a%mod);
        BigInteger N = BigInteger.valueOf(n%mod);
        BigInteger MOD = BigInteger.valueOf(mod);
        var hoge = A.multiply(BigInteger.TWO).add(N.add(BigInteger.valueOf(mod-1))).multiply(N).mod(MOD).longValue();
        //return (2*a+(n-1))*n/2;
        return hoge * modInv(2, mod)%mod;
    }

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
