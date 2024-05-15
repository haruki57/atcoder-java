import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC353D {
    static long MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] a = new long[N];
        int[] digit = new int[N];
        for (int i = 0; i < N; i++) {
            //a[N-i-1] = scanner.nextInt();
            a[i] = scanner.nextInt();
            digit[i] = String.valueOf(a[i]).length();
        }
        int[][] cntPerDigit = new int[11][N+1];
        for (int i = 0; i < cntPerDigit.length; i++) {
            for (int j = 0; j < N; j++) {
                cntPerDigit[i][j+1]=cntPerDigit[i][j] + (digit[j]==i?1:0);
            }
            //System.out.println(Arrays.toString(cntPerDigit[i]));
        }
        long ans = 0;
        for (int i = 0; i < N; i++) {
            // int digit = String.valueOf(a[i]).length();
            ans = (ans + i*a[i]) % MOD;
            for (int j = 0; j < cntPerDigit.length; j++) {
                long pow10 = (long)Math.pow(10, j)%MOD;
                ans = (ans + (a[i]*pow10)%MOD*(cntPerDigit[j][N]-cntPerDigit[j][i+1]))%MOD;
                //System.out.println(ans);
            }
            //System.out.println();
        }
        System.out.println(ans);

        /*
        BigInteger ansSlow = BigInteger.ZERO;
        BigInteger bMod = BigInteger.valueOf(MOD);
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                ansSlow = ansSlow.add(new BigInteger(String.valueOf(a[i]) + String.valueOf(a[j]))).mod(bMod);
            }
        }
        System.out.println(ansSlow);

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
