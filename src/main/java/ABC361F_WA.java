import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/*
重複を排除できず、WA
 */

public class ABC361F_WA {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE - 100;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextLong();
        /*
        2 -> 2 4 8 16 32 64
         */
        long ans = 0;
        Set<Long> set =new TreeSet<>();
        for (int i = 2; i < 61; i++) {
            BigInteger ok = BigInteger.ZERO;
            BigInteger ng = BigInteger.valueOf(INF);
            while(ng.subtract(ok).longValue() > 1) {
                BigInteger mid = ok.add(ng).divide(BigInteger.valueOf(2));
                if (ok(mid, N, i)) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            /*
            System.out.println(i);
            System.out.println(ok);
            System.out.println(Math.pow(ok.longValue(), i));
            System.out.println();

             */
            long okL = ok.longValue();
            /*
            for (int j = 1; j <= okL; j++) {
                set.add((long)Math.pow(j, i));
            }
             */
                    

            ans += Math.max(0, ok.longValue()-1);
        }
        System.out.println(ans);
        //System.out.println(set);
        //System.out.println(set.size());
    }

    private static boolean ok(BigInteger mid, long n, int pow) {
        BigInteger bi = mid.pow(pow);
        //System.out.println(mid+" "+bi +" "+n);
        return bi.subtract(BigInteger.valueOf(n)).compareTo(BigInteger.ZERO) <= 0;
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
