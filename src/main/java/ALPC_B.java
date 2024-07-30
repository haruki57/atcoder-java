import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ALPC_B {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        var bit = new BinaryIndexedTree(new long[N+9]);
        for (int i = 0; i < N; i++) {
            bit.add(i+1, scanner.nextInt());
        }
        while(Q-->0) {
            int t = scanner.nextInt();
            if(t==0) {
                int p = scanner.nextInt();
                int x = scanner.nextInt();
                bit.add(p+1, x);
            } else {
                int l = scanner.nextInt()+1;
                int r = scanner.nextInt()+1;
                out.println(bit.sum(l, r-1));
            }
        }
    }

    // 1-indexed
    static class BinaryIndexedTree {
        long[] data;
        long[] tree;
        int N;

        BinaryIndexedTree(long[] data) {
            this.data = data;
            this.N = data.length;
            this.tree = new long[N + 1]; // index range is from 1 to N
            buildTree();
        }

        void buildTree() {
            for (int i = 0; i < N; i++) {
                add(i + 1, data[i]);
            }
        }

        void add(int index, long value) {
            for (int i = index; i <= N; i += i & -i) {
                tree[i] += value;
            }
        }

        long sum(int from, int to) {
            return sum(to) - sum(from - 1);
        }

        long sum(int index) {
            long sum = 0;
            for (int i = index; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
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
