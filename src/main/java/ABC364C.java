import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ABC364C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long X = scanner.nextLong();
        long Y = scanner.nextLong();
        long[][] xy = new long[N][2];
        for (int i = 0; i < N; i++) {
            xy[i][0]= scanner.nextLong();
        }
        for (int i = 0; i < N; i++) {
            xy[i][1]= scanner.nextLong();
        }
        Arrays.sort(xy, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o2[0], o1[0]);
            }
        });
        long sumX = 0;
        long sumY = 0;
        int cnt1 = 0;
        for (int i = 0; i < N; i++) {
            //System.out.println(Arrays.toString(xy[i]));
        }

        for (int i = 0; i <N; i++) {
            sumX += xy[i][0];
            sumY += xy[i][1];
            cnt1++;
            if (sumX > X  || sumY > Y) {
                break;
            }
        }

        Arrays.sort(xy, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o2[1], o1[1]);
            }
        });
        for (int i = 0; i < N; i++) {
            //System.out.println(Arrays.toString(xy[i]));
        }
        sumX=sumY=0;
        int cnt2=0;
        for (int i = 0; i <N; i++) {
            sumX += xy[i][0];
            sumY += xy[i][1];
            cnt2++;
            if (sumX > X || sumY > Y) {
                break;
            }
        }
        System.out.println(Math.min(cnt1, cnt2));
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
