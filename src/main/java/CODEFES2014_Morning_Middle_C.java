import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class CODEFES2014_Morning_Middle_C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        double p = scanner.nextDouble();
        long N = scanner.nextLong();
        if (p==1.0) {
            System.out.println(N%2);
            return;
        }
        if (N >= 100000000000L) {
            if(p==0.0) {
                System.out.println(0);
            } else if (p==1.0) {
                System.out.println(N%2);
            } else {
                System.out.println(0.5);
            }
            return;
        }
        BigDecimal[][] mat = {
                {BigDecimal.valueOf(1-p), BigDecimal.valueOf(p)},
                {BigDecimal.valueOf(p), BigDecimal.valueOf(1-p)}};
        mat = exp(mat, N);
        System.out.println(mat[0][1].setScale(30, RoundingMode.HALF_EVEN));
    }

    private static BigDecimal[][] mul(BigDecimal[][] a, BigDecimal[][] b) {
        int n = a.length;
        BigDecimal[][] c = zeroMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]).setScale(100, RoundingMode.HALF_EVEN)).setScale(100, RoundingMode.HALF_EVEN);
                }
            }
        }
        return c;
    }

    private static BigDecimal[][] exp(BigDecimal[][] a, long b) {
        int n = a.length;
        BigDecimal[][] res = zeroMatrix(n);
        for (int i = 0; i < n; i++) {
            res[i][i] = BigDecimal.ONE;
        }
        while (b > 0) {
            if ((b & 1) > 0) res = mul(res, a);
            a = mul(a, a);
            b >>= 1;
        }
        return res;
    }
    private static BigDecimal[][] zeroMatrix(int n) {
        BigDecimal[][] c = new BigDecimal[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j]=BigDecimal.ZERO;
            }
        }
        return c;
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
