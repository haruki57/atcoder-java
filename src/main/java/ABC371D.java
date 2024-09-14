import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ABC371D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] a = new int[N][2];
        for (int i = 0; i < N; i++) {
            a[i][0]= scanner.nextInt();
        }
        for (int i = 0; i < N; i++) {
            a[i][1]= scanner.nextInt();
        }
        Arrays.sort(a, (o1, o2) -> o1[0]-o2[0]);
        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i]=a[i][0];
        }
        long[] sum = new long[N+1];
        for (int i = 0; i < N; i++) {
            sum[i+1]=sum[i]+a[i][1];
        }
        int Q = scanner.nextInt();
        while(Q-->0) {
            int l = scanner.nextInt();
            int r = scanner.nextInt();
            int i = lowerBound(x, l);
            int j = lowerBound(x, r+1);
            //System.out.println(i+" "+j);
            out.println(sum[j]-sum[i]);
        }
    }

    static int lowerBound(int[] A, int x) {
        int l = -1, r = A.length;
        while(Math.abs(r - l) > 1) {
            int mid = (l + r) / 2;
            if(A[mid] >= x) {
                r = mid;
            }
            else {
                l = mid;
            }
        }
        return r;
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
