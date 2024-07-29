import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ABC364D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        Arrays.sort(a);
        for (int i = 0; i < Q; i++) {
            long b = scanner.nextLong();
            long k = scanner.nextLong();
            long ok = INF, ng = -1;
            while(Math.abs(ok-ng)>1) {
                long mid = (ok+ng)/2;
                if(ok(a,b,k,mid)) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            out.println(ok);
        }

        /*
        long[][] bk = new long[Q][4]; // b, k, originalIdx, ans
        for (int i = 0; i < bk.length; i++) {
            bk[i][0]= scanner.nextLong();
            bk[i][1]= scanner.nextLong();
            bk[i][2]= i;
        }
        Arrays.sort(bk, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o1[0], o2[0]);
            }
        });
        int lowRight = -1;
        for (int i = 0; i < a.length; i++) {
            if(a[i]<bk[0][0]) {
                lowRight = i;
            } else {
                break;
            }
        }

        Arrays.sort(bk, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o1[2], o2[2]);
            }
        });
        for (int i = 0; i < bk.length; i++) {
            out.println(bk[i][3]);
        }

         */
    }

    private static boolean ok(long[] a, long b, long k, long mid) {
        long l = b-mid;
        long h = b+mid;
        int lb = lowerBound(a, l);
        int ub = upperBound(a, h);
        //System.out.println(b+" "+k+" "+mid+" "+lb+" "+ub);
        return ub-lb>=k;
    }


    // Verified https://atcoder.jp/contests/tessoku-book/submissions/52257983
    static int lowerBound(long[] A, long x) {
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

    // Verified https://atcoder.jp/contests/abc331/submissions/56089805
    static int upperBound(long[] A, long x) {
        int l = -1, r = A.length;
        while(Math.abs(r - l) > 1) {
            int mid = (l + r) / 2;
            if(A[mid] > x) {
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
