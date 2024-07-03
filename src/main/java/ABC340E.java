import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC340E {
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[M];
        Arrays.setAll(b, i -> scanner.nextInt());
        BinaryIndexedTree2 bit = new BinaryIndexedTree2(N);

        // init
        for (int i = 0; i < a.length; i++) {
            bit.add(i, a[i]);
        }
        for (int i = 0; i < M; i++) {
            int idx = b[i];
            long x = bit.get(idx);
            bit.rangeAdd(b[i], b[i]+1, -x);
            long all = x/N;
            int rest = (int)(x%N);
            bit.rangeAdd(0, N, all);
            if (rest < (N-idx)) {
                bit.rangeAdd(idx+1, idx+1+rest, 1);
            } else {
                bit.rangeAdd(idx+1, N, 1);
                bit.rangeAdd(0, idx+1-(N-rest), 1);
            }

        }
        bit.print();
    }

    // 0-indexed
    static class BinaryIndexedTree2 {
        long[] data;
        long[] tree;
        int N;

        BinaryIndexedTree2(int N) {
            this.data = new long[N];
            this.N = data.length;
            this.tree = new long[N + 1]; // index range is from 1 to N
            buildTree();
        }

        private void buildTree() {
            for (int i = 0; i < N; i++) {
                _add(i + 1, data[i]);
            }
        }

        private void _add(int index, long value) {
            for (int i = index; i <= N; i += i & -i) {
                tree[i] += value;
            }
        }

        private long sum(int from, int to) {
            return sum(to) - sum(from - 1);
        }

        private long sum(int index) {
            long sum = 0;
            for (int i = index; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }

        void add(int i, long val) {
            rangeAdd(i, i+1, val);
        }

        void rangeAdd(int l, int r, long val) {
            this._add(l+1, val);
            this._add(r+1, -val);
        }

        long get(int idx) {
            return this.sum(idx + 1);
        }

        void print() {
            for (int i = 0; i < N; i++) {
                System.out.print(get(i) +" ");
            }
            System.out.println();
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
