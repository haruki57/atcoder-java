import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class ABC353D_BI {
    static long MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) {
            a[N-i-1] = scanner.nextInt();
        }
        BigInteger[][] sumByDigit = new BigInteger[13][N+1];
        for (int i = 0; i < sumByDigit.length; i++) {
            for (int j = 0; j < sumByDigit[i].length; j++) {
                sumByDigit[i][j]=BigInteger.ZERO;
            }
        }
        for (int i = 0; i < sumByDigit.length; i++) {
            for (int j = 0; j < sumByDigit[i].length-1; j++) {
                sumByDigit[i][j+1]=sumByDigit[i][j].add(BigInteger.valueOf(a[j]).multiply(BigInteger.valueOf((long)Math.pow(10, i))));
            }
            // System.out.println(Arrays.toString(sumByDigit[i]));
        }

        BigInteger ans = BigInteger.ZERO;
        BigInteger bMod = BigInteger.valueOf(MOD);
        for (int i = 0; i < N; i++) {
            int digit = String.valueOf(a[i]).length();
            var x = sumByDigit[digit][N].subtract(sumByDigit[digit][i+1]);
            ans = ans.add(x).mod(bMod);
            //System.out.println(ans);
            ans = ans.add(BigInteger.valueOf(a[i]*(N-i-1)).mod(bMod));
            //System.out.println(ans);
            //System.out.println();
        }
        System.out.println(ans);

        /*
        BigInteger ansSlow = BigInteger.ZERO;

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                int ii = N-i-1;
                int jj = N-j-1;
                ansSlow = ansSlow.add(new BigInteger(String.valueOf(a[ii]) + String.valueOf(a[jj]))).mod(bMod);
            }
        }
        System.out.println(ansSlow);


        long ansSlow2 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                int ii = N-i-1;
                int jj = N-j-1;
                ansSlow2 = (ansSlow2 + Long.parseLong(String.valueOf(a[ii]) + String.valueOf(a[jj]))) % MOD;
            }
        }
        System.out.println(ansSlow2);

         */
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
