import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC023D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static boolean isOk(long[][] hs, long point) {
        List<Integer> seconds = new ArrayList<>();
        for (int i = 0; i < hs.length; i++) {
            int ng = INF, ok = -1;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                if (hs[i][0] + mid * hs[i][1] <= point) {
                    ok = mid;
                } else {
                    ng = mid;
                }
                // System.out.println(ok);
            }
            // System.out.println();
            if (ok==-1) {
                return false;
            }
            seconds.add(ok);
        }
        Collections.sort(seconds);
        //System.out.println(seconds);
        for (int i = 0; i < hs.length; i++) {
            int sec = seconds.get(i);
            if (sec < i) {
                return false;
            }
        }
        return true;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[][] hs = new long[N][2];
        for (int i = 0; i < N; i++) {
            hs[i][0] = scanner.nextInt();
            hs[i][1] = scanner.nextInt();
        }
        long ok = Long.MAX_VALUE / 4, ng=-1;

        while(Math.abs(ok-ng) > 1) {
            long mid = (ok+ng) / 2;
            boolean ok1 = isOk(hs, mid);
            //System.out.println(ok1);
            if (ok1) {
                ok = mid;
            } else {
                ng = mid;
            }
            //System.out.println(ng + " "+ ok);
        }
        System.out.println(ok);

        //System.out.println(isOk(hs, 23));

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
