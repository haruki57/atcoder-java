import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC335A {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        System.out.println(findMin(new int[]{1}, 0, 0));
        System.out.println(findMin(new int[]{1,2}, 0, 1));
        System.out.println(findMin(new int[]{2,1}, 0, 1));
        System.out.println(findMin(new int[]{3,5,7,1,2}, 0, 4));
        System.out.println(findMin(new int[]{7, 1, 2, 3, 5}, 0, 4));
        System.out.println(findMin(new int[]{1, 2, 3, 5, 7}, 0, 4));
        System.out.println(findMin(new int[]{2, 3, 5, 7,1 }, 0, 4));
        System.out.println(findMin(new int[]{5,7,1,2,3 }, 0, 4));
        char[] s = scanner.next().toCharArray();
        s[s.length-1]++;
        System.out.println(s);

    }

    static int findMin(int[] a, int left, int right) {
        if (a[left] < a[right]) {
            return a[left];
        }
        if(right-left <= 1) {
            return Math.min(a[left], a[right]);
        }
        int mid = (left+right)/2;
        return Math.min(findMin(a, left, mid), findMin(a, mid, right));
        /*
        if(a[left] > a[mid]) {
            return findMin(a, left, mid);
        } else {
            return findMin(a, mid, right);
        }
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
