import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC353C {
    static long MOD = 100000000;
    //static long MOD = 10;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        Arrays.sort(a);
        long[] sum = new long[N+1];
        long[] sumMod = new long[N+1];
        for (int i = 0; i < N; i++) {
            sum[i+1] = sum[i] + a[i];
            sumMod[i+1] = sumMod[i] + (a[i]-MOD);
        }
        long ans = 0;
        for (int i = 0; i < N-1; i++) {
            int ng = N, ok=i;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                if (a[mid]+a[i] < MOD) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            //System.out.println(ok+" "+a[i]+" "+a[ok]);
            ans += (long)a[i] * (ok-i) + (sum[ok+1]-sum[i+1]);
            //ans += Math.abs(sumMod[N]-sumMod[ok+1]);
            int len = N-(ok+1);
            ans += (long)a[i]*len + (sum[N]-sum[ok+1]) - MOD * len;
            //System.out.println(ans);
            //System.out.println();
        }
        /*
        MOD 10
        -7 -6 -3 -2 -1
        3   4  7  8  9
            6  10 11 12

         */

        /*
        long ansSlow = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                ansSlow += (a[i]+a[j]) % MOD;
            }
            //System.out.println(ansSlow);
        }
        System.out.println(ansSlow);

         */
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
