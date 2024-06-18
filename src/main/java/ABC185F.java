import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC185F {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    // 1-indexed!
    static class SegmentTree {
        public int[] dat = new int[3000000];
        public int size = 1;

        void init(int N) {
            size = 1;
            while(size < N) {
                size *= 2;
            }
            Arrays.fill(dat, 0);
        }

        void update(int pos, int x) {
            pos = pos + size - 1;
            dat[pos] ^= x;
            while(pos >= 2) {
                pos /= 2;
                dat[pos] = dat[pos * 2] ^ dat[pos * 2 + 1];
            }
        }

        int query (int l, int r, int a, int b, int u) {
            if (r <= a || b <= l) {
                return 0;
            }
            if (l <= a && b <= r) {
                return dat[u];
            }
            int m = (a + b) / 2;
            int answerL = query(l, r, a, m, u*2);
            int answerR = query(l, r, m, b, u*2 + 1);
            return answerL ^ answerR;
        }

        int query(int l, int r) {
            return query(l, r, 1, size + 1, 1);
        }
    }


    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        //int[] a = new int[N];
        //Arrays.setAll(a, i -> scanner.nextInt());
        var seg = new SegmentTree();
        seg.init(N+1);
        for (int i = 0; i < N; i++) {
            seg.update(i+1, scanner.nextInt());
        }
        /*
        int[][] xorsum = new int[31][N+1];
        for (int i = 0; i < xorsum.length; i++) {
            xorsum[i][0]=0;
            for (int j = 0; j < N; j++) {
                xorsum[i][j+1] = xorsum[i][j] ^ ((1<<i)&a[j]);
            }
        }
        */
        while(Q-->0) {
            int t = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (t==1) {

                //seg.update(x, seg.query(y-1, y)^y);
                seg.update(x, y);
                continue;
            }
            System.out.println(seg.query(x, y+1));
        }

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