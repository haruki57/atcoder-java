import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class DP_Q {
    static int MOD = (int)1e9+7;
    static long lINF = Long.MAX_VALUE/2;

    static long[][] dp;
    static List<Integer>[] g;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[]h  = new int[N];
        int[]a  = new int[N];
        for (int i = 0; i < h.length; i++) {
            h[i]= scanner.nextInt();
        }
        for (int i = 0; i < a.length; i++) {
            a[i]= scanner.nextInt();
        }
        long ans = 0;
        var hoge = new Long[N];
        Arrays.fill(hoge, 0L);
        SegTree<Long> seg= new SegTree<>(hoge, Long::max, -1L);
        TreeMap<Integer, Long> map = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            Long max = seg.prod(0, h[i]-1);
            if(max==null || max == -1) {
                seg.set(h[i]-1, (long)a[i]);
                ans = Math.max(ans, a[i]);
            } else {
                seg.set(h[i]-1, (long)a[i]+max);
                ans = Math.max(ans, a[i]+max);
            }
        }
        System.out.println(ans);
    }
    static class SegTree<S> {
        final int MAX;

        final int N;
        final java.util.function.BinaryOperator<S> op;
        final S E;

        final S[] data;

        @SuppressWarnings("unchecked")
        public SegTree(int n, java.util.function.BinaryOperator<S> op, S e) {
            this.MAX = n;
            int k = 1;
            while (k < n) k <<= 1;
            this.N = k;
            this.E = e;
            this.op = op;
            this.data = (S[]) new Object[N << 1];
            java.util.Arrays.fill(data, E);
        }

        public SegTree(S[] dat, java.util.function.BinaryOperator<S> op, S e) {
            this(dat.length, op, e);
            build(dat);
        }

        private void build(S[] dat) {
            int l = dat.length;
            System.arraycopy(dat, 0, data, N, l);
            for (int i = N - 1; i > 0; i--) {
                data[i] = op.apply(data[i << 1 | 0], data[i << 1 | 1]);
            }
        }

        public void set(int p, S x) {
            exclusiveRangeCheck(p);
            data[p += N] = x;
            p >>= 1;
            while (p > 0) {
                data[p] = op.apply(data[p << 1 | 0], data[p << 1 | 1]);
                p >>= 1;
            }
        }

        public S get(int p) {
            exclusiveRangeCheck(p);
            return data[p + N];
        }

        public S prod(int l, int r) {
            if (l > r) {
                throw new IllegalArgumentException(
                        String.format("Invalid range: [%d, %d)", l, r)
                );
            }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            S sumLeft = E;
            S sumRight = E;
            l += N; r += N;
            while (l < r) {
                if ((l & 1) == 1) sumLeft = op.apply(sumLeft, data[l++]);
                if ((r & 1) == 1) sumRight = op.apply(data[--r], sumRight);
                l >>= 1; r >>= 1;
            }
            return op.apply(sumLeft, sumRight);
        }

        public S allProd() {
            return data[1];
        }

        public int maxRight(int l, java.util.function.Predicate<S> f) {
            inclusiveRangeCheck(l);
            if (!f.test(E)) {
                throw new IllegalArgumentException("Identity element must satisfy the condition.");
            }
            if (l == MAX) return MAX;
            l += N;
            S sum = E;
            do {
                l >>= Integer.numberOfTrailingZeros(l);
                if (!f.test(op.apply(sum, data[l]))) {
                    while (l < N) {
                        l = l << 1;
                        if (f.test(op.apply(sum, data[l]))) {
                            sum = op.apply(sum, data[l]);
                            l++;
                        }
                    }
                    return l - N;
                }
                sum = op.apply(sum, data[l]);
                l++;
            } while ((l & -l) != l);
            return MAX;
        }

        public int minLeft(int r, java.util.function.Predicate<S> f) {
            inclusiveRangeCheck(r);
            if (!f.test(E)) {
                throw new IllegalArgumentException("Identity element must satisfy the condition.");
            }
            if (r == 0) return 0;
            r += N;
            S sum = E;
            do {
                r--;
                while (r > 1 && (r & 1) == 1) r >>= 1;
                if (!f.test(op.apply(data[r], sum))) {
                    while (r < N) {
                        r = r << 1 | 1;
                        if (f.test(op.apply(data[r], sum))) {
                            sum = op.apply(data[r], sum);
                            r--;
                        }
                    }
                    return r + 1 - N;
                }
                sum = op.apply(data[r], sum);
            } while ((r & -r) != r);
            return 0;
        }

        private void exclusiveRangeCheck(int p) {
            if (p < 0 || p >= MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for the range [%d, %d).", p, 0, MAX)
                );
            }
        }

        private void inclusiveRangeCheck(int p) {
            if (p < 0 || p > MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for the range [%d, %d].", p, 0, MAX)
                );
            }
        }

        // **************** DEBUG **************** //

        private int indent = 6;

        public void setIndent(int newIndent) {
            this.indent = newIndent;
        }

        @Override
        public String toString() {
            return toSimpleString();
        }

        public String toDetailedString() {
            return toDetailedString(1, 0);
        }

        private String toDetailedString(int k, int sp) {
            if (k >= N) return indent(sp) + data[k];
            String s = "";
            s += toDetailedString(k << 1 | 1, sp + indent);
            s += "\n";
            s += indent(sp) + data[k];
            s += "\n";
            s += toDetailedString(k << 1 | 0, sp + indent);
            return s;
        }

        private static String indent(int n) {
            StringBuilder sb = new StringBuilder();
            while (n --> 0) sb.append(' ');
            return sb.toString();
        }

        public String toSimpleString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < N; i++) {
                sb.append(data[i + N]);
                if (i < N - 1) sb.append(',').append(' ');
            }
            sb.append(']');
            return sb.toString();
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
