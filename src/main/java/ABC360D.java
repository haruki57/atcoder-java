import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC360D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
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

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long T = scanner.nextLong()*10;
        char[] s = scanner.next().toCharArray();
        int rCnt = 0;
        int lCnt = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]=='1') {
                rCnt++;
            } else {
                lCnt++;
            }
        }
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextLong()*10);
        long[] rs = new long[rCnt];
        long[] ls = new long[lCnt];
        int idx = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]=='1') {
                rs[idx] = a[i];
                idx++;
            }
        }
        idx = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]=='0') {
                ls[idx] = a[i];
                idx++;
            }
        }
        Arrays.sort(rs);
        Arrays.sort(ls);
        //System.out.println(Arrays.toString(rs));
        //System.out.println(Arrays.toString(ls));
        long ans = 0;
        for (int i = 0; i < rs.length; i++) {
            int lb1 = lowerBound(ls, rs[i]);
            int lb2 = lowerBound(ls, rs[i]+(T*2)+1L);
            //System.out.println(lb1+" "+lb2);
            //System.out.println(lb2-lb1);
            ans += (lb2-lb1);
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
