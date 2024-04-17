import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Square8691204B {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[] original = new int[N];
        Arrays.setAll(original, i -> scanner.nextInt());
        long ans = (long)INF * INF;
        for (int bit = 0; bit < 1<<N; bit++) {
            if (Integer.bitCount(bit) != K) {
                continue;
            }
            // System.out.println(Integer.toBinaryString(bit));
            int[] a = original.clone();
            long cost = 0;
            int prev = -1;
            for (int i = 0; i < N; i++) {
                if (((1 << i) & bit) == 0) {
                    continue;
                }
                if (prev >= a[i]) {
                    cost += (prev - a[i]) + 1;
                    a[i] = prev + 1;
                }
                prev = a[i];
            }
            //System.out.println(Arrays.toString(a));
            for (int i = 0; i < N; i++) {
                if (((1 << i) & bit) != 0) {
                    continue;
                }
                int currentH = a[i];
                int firstDiff = -1;
                for (int j = i + 1; j < N; j++) {
                    if (((1 << j) & bit) == 0) {
                        continue;
                    }
                    if (a[j] <= currentH) {
                        if (firstDiff == -1) {
                            firstDiff = currentH - a[j];
                        }
                        a[j] += firstDiff;
                        cost += firstDiff;
                    }
                    //System.out.println(j);
                }
            }
            /*
            System.out.println(Arrays.toString(a));
            System.out.println(cost);
            System.out.println();
             */
            ans = Math.min(ans, cost);
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
