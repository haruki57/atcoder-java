import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC302D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        long D = scanner.nextLong();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextLong());
        long[] b = new long[M];
        Arrays.setAll(b, i -> scanner.nextLong());
        Arrays.sort(a);
        Arrays.sort(b);
        long ans = -1;
        for (int i = 0; i < a.length; i++) {
            // a-bがD 以下
            int ok = M;
            int ng = -1;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                if (a[i]-b[mid] <= D) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }

            // b-aがD 以下
            ok = -1;
            ng = M;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                //System.out.println(mid+" "+a[i]+" "+b[mid]);
                if (b[mid]-a[i] <= D) {
                    ok = mid;
                    //System.out.println("ok");
                } else {
                    ng = mid;
                    //System.out.println("ng");
                }
            }
            if (ok!=-1 && Math.abs(a[i]-b[ok])<=D) {
                ans = Math.max(ans, a[i]+b[ok]);
            }
            //System.out.println(ok+" "+ng);
            //System.out.println();

        }
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
