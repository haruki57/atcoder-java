import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC356D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    private static long divideWithMod(long a, long b, int mod) {
        return (a*powWithMod(b, mod-2, mod)%mod);
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

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextLong();
        long M = scanner.nextLong();

        //for (int N = 0; N < 10000; N++) {
//            for (int M = 0; M < 20; M++) {
        long ans = 0;
        for (int i = 0; i < 63; i++) {
            long pow = 1L<<i;
            long pow2 = pow * 2;
            long hoge = N-N%pow2;
            long fuga = N-hoge+1;
            long cnt = hoge/2;//divideWithMod(hoge, 2, MOD);
            cnt = cnt + Math.max(fuga-pow, 0)%MOD;
            cnt %= MOD;
            //System.out.println(hoge+" "+fuga+" "+cnt);
            //System.out.println(hoge);
            ans = ans + cnt*((M&pow)>0?1:0)%MOD;
            ans %= MOD;
            //System.out.println(ans);
            //System.out.println();
        }
//                System.out.println(ans);


//                System.out.println();
/*
                long ansTLE = 0;
                for (int k = 0; k <= N; k++) {
                    ansTLE += Long.bitCount(k & M);
                    ansTLE %= MOD;
                }

 */


        System.out.println(ans);
        //System.out.println(ans==ansTLE);


        //}
        //}


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
