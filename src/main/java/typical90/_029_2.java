package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;

public class _029_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int W = scanner.nextInt();
        int N = scanner.nextInt();
        var lazyTree = new LazyLongSegmentTree(W+1, Math::max);
        for (int i = 0; i < N; i++) {
            int l = scanner.nextInt();
            int r = scanner.nextInt()+1;
            long max = lazyTree.get(l, r);
            out.println(max+1);
            lazyTree.set(l, r, max+1);
        }
    }

    private static class LazyLongSegmentTree {
        long[] data;
        Long[] lazy;
        int size;
        LongBinaryOperator operator;
        LazyLongSegmentTree(int size, LongBinaryOperator operator) {
            this.size = 2;
            while(this.size < size) {
                this.size *= 2;
            }
            this.size *= 2;
            this.size -= 1;
            this.data = new long[this.size];
            this.lazy = new Long[this.size];
            this.operator = operator;
        }
        LazyLongSegmentTree(int size) {
            this(size, (a, b) -> Math.min(a, b));
        }
        void set(int idx, long value) {
            idx += ((size + 1) / 2) - 1;
            data[idx] = value;
            while(idx > 0) {
                idx = (idx - 1) / 2;
                data[idx] = operator.applyAsLong(data[idx * 2 + 1], data[idx * 2 + 2]);
            }
        }
        void set(int l, int r, long value) { // [l, r)
            setPart(l, r, value, 0, 0, (size + 1) / 2);
        }
        void setPart(int l, int r, long value, int pos, int pl, int pr) {
            lazyUpdate(pos);
            if(pr <= l || r <= pl) {
                return;
            } else if(l <= pl && pr <= r) {
                lazy[pos] = value;
                lazyUpdate(pos);
            } else {
                setPart(l, r, value, pos * 2 + 1, pl, (pl + pr) / 2);
                setPart(l, r, value, pos * 2 + 2, (pl + pr) / 2, pr);
                data[pos] = operator.applyAsLong(data[2 * pos + 1], data[2 * pos + 2]);
            }
        }
        void lazyUpdate(int pos) {
            if(lazy[pos] == null) return;
            if(pos < size / 2) {
                lazy[2 * pos + 1] = lazy[pos];
                lazy[2 * pos + 2] = lazy[pos];
            }
            data[pos] = lazy[pos];
            lazy[pos] = null;
        }
        long get(int l, int r) { // [l, r)
            OptionalLong ret = getPart(l, r, 0, 0, (size + 1) / 2);
            if(ret.isPresent()) {
                return ret.getAsLong();
            } else {
                return 0;
            }
        }
        private OptionalLong getPart(int l, int r, int pos, int pl, int pr) {
            lazyUpdate(pos);
            if(pr <= l || r <= pl) {
                return OptionalLong.empty();
            } else if(l <= pl && pr <= r) {
                return OptionalLong.of(data[pos]);
            } else {
                OptionalLong partL = getPart(l, r, pos * 2 + 1, pl, (pl + pr) / 2);
                OptionalLong partR = getPart(l, r, pos * 2 + 2, (pl + pr) / 2, pr);
                if(partL.isPresent() && partR.isPresent()) {
                    return OptionalLong.of(operator.applyAsLong(partL.getAsLong(), partR.getAsLong()));
                } else if(partL.isPresent()) {
                    return partL;
                } else if(partR.isPresent()) {
                    return partR;
                } else {
                    return OptionalLong.empty();
                }
            }
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
