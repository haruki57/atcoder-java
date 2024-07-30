import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class ALPC_J {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static final BinaryOperator<SegmentTreeNode> op =
            (a, b) -> new SegmentTreeNode((a.val + b.val) % MOD, a.size + b.size);
    static final BiFunction<LazyTag, SegmentTreeNode, SegmentTreeNode> mapping = (lazyTag, segmentTreeNode) -> {
        long a = (segmentTreeNode.val * lazyTag.b % MOD + lazyTag.c * segmentTreeNode.size % MOD) % MOD;
        return new SegmentTreeNode(a, segmentTreeNode.size);
    };
    static final BinaryOperator<LazyTag> composition =
            (x, y) -> new LazyTag(x.b * y.b % MOD , (x.b * y.c % MOD + x.c) % MOD);
    static final SegmentTreeNode E = new SegmentTreeNode(0, 0);
    static final LazyTag T = new LazyTag(1, 0);


    public static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();

        SegmentTreeNode[] data = new SegmentTreeNode[N];
        for(int i = 0; i < N; i++) {
            data[i] = new SegmentTreeNode(scanner.nextLong(), 1);
        }
        LazySegTree<SegmentTreeNode, LazyTag> sgt = new LazySegTree<>(
                data,
                op,
                E,
                mapping,
                composition,
                T
        );
        while(Q-->0) {
            int type = scanner.nextInt();
            int l = scanner.nextInt();
            int r = scanner.nextInt();
            if(type==0) {
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                sgt.apply(l, r, new LazyTag(b, c));
                //sgt.update
            } else {
                out.println(sgt.prod(l, r).val);
            }
        }
    }


    static class LazySegTree<S, F> {
        final int MAX;

        final int N;
        final int Log;
        final java.util.function.BinaryOperator<S> Op;
        final S E;
        final java.util.function.BiFunction<F, S, S> Mapping;
        final java.util.function.BinaryOperator<F> Composition;
        final F Id;

        final S[] Dat;
        final F[] Laz;

        @SuppressWarnings("unchecked")
        public LazySegTree(int n, java.util.function.BinaryOperator<S> op,
                           S e, java.util.function.BiFunction<F, S, S> mapping,
                           java.util.function.BinaryOperator<F> composition, F id) {
            this.MAX = n;
            int k = 1;
            while (k < n) k <<= 1;
            this.N = k;
            this.Log = Integer.numberOfTrailingZeros(N);
            this.Op = op;
            this.E = e;
            this.Mapping = mapping;
            this.Composition = composition;
            this.Id = id;
            this.Dat = (S[]) new Object[N << 1];
            this.Laz = (F[]) new Object[N];
            java.util.Arrays.fill(Dat, E);
            java.util.Arrays.fill(Laz, Id);
        }

        public LazySegTree(S[] dat, java.util.function.BinaryOperator<S> op,
                           S e, java.util.function.BiFunction<F, S, S> mapping,
                           java.util.function.BinaryOperator<F> composition, F id) {
            this(dat.length, op, e, mapping, composition, id);
            build(dat);
        }

        private void build(S[] dat) {
            int l = dat.length;
            System.arraycopy(dat, 0, Dat, N, l);
            for (int i = N - 1; i > 0; i--) {
                Dat[i] = Op.apply(Dat[i << 1 | 0], Dat[i << 1 | 1]);
            }
        }

        private void push(int k) {
            if (Laz[k] == Id) return;
            int lk = k << 1 | 0, rk = k << 1 | 1;
            Dat[lk] = Mapping.apply(Laz[k], Dat[lk]);
            Dat[rk] = Mapping.apply(Laz[k], Dat[rk]);
            if (lk < N) Laz[lk] = Composition.apply(Laz[k], Laz[lk]);
            if (rk < N) Laz[rk] = Composition.apply(Laz[k], Laz[rk]);
            Laz[k] = Id;
        }

        private void pushTo(int k) {
            for (int i = Log; i > 0; i--) push(k >> i);
        }

        private void pushTo(int lk, int rk) {
            for (int i = Log; i > 0; i--) {
                if (((lk >> i) << i) != lk) push(lk >> i);
                if (((rk >> i) << i) != rk) push(rk >> i);
            }
        }

        private void updateFrom(int k) {
            k >>= 1;
            while (k > 0) {
                Dat[k] = Op.apply(Dat[k << 1 | 0], Dat[k << 1 | 1]);
                k >>= 1;
            }
        }

        private void updateFrom(int lk, int rk) {
            for (int i = 1; i <= Log; i++) {
                if (((lk >> i) << i) != lk) {
                    int lki = lk >> i;
                    Dat[lki] = Op.apply(Dat[lki << 1 | 0], Dat[lki << 1 | 1]);
                }
                if (((rk >> i) << i) != rk) {
                    int rki = (rk - 1) >> i;
                    Dat[rki] = Op.apply(Dat[rki << 1 | 0], Dat[rki << 1 | 1]);
                }
            }
        }

        public void set(int p, S x) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            Dat[p] = x;
            updateFrom(p);
        }

        public S get(int p) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            return Dat[p];
        }

        public S prod(int l, int r) {
            if (l > r) {
                throw new IllegalArgumentException(
                        String.format("Invalid range: [%d, %d)", l, r)
                );
            }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            if (l == r) return E;
            l += N; r += N;
            pushTo(l, r);
            S sumLeft = E, sumRight = E;
            while (l < r) {
                if ((l & 1) == 1) sumLeft = Op.apply(sumLeft, Dat[l++]);
                if ((r & 1) == 1) sumRight = Op.apply(Dat[--r], sumRight);
                l >>= 1; r >>= 1;
            }
            return Op.apply(sumLeft, sumRight);
        }

        public S allProd() {
            return Dat[1];
        }

        public void apply(int p, F f) {
            exclusiveRangeCheck(p);
            p += N;
            pushTo(p);
            Dat[p] = Mapping.apply(f, Dat[p]);
            updateFrom(p);
        }

        public void apply(int l, int r, F f) {
            if (l > r) {
                throw new IllegalArgumentException(
                        String.format("Invalid range: [%d, %d)", l, r)
                );
            }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            if (l == r) return;
            l += N; r += N;
            pushTo(l, r);
            for (int l2 = l, r2 = r; l2 < r2;) {
                if ((l2 & 1) == 1) {
                    Dat[l2] = Mapping.apply(f, Dat[l2]);
                    if (l2 < N) Laz[l2] = Composition.apply(f, Laz[l2]);
                    l2++;
                }
                if ((r2 & 1) == 1) {
                    r2--;
                    Dat[r2] = Mapping.apply(f, Dat[r2]);
                    if (r2 < N) Laz[r2] = Composition.apply(f, Laz[r2]);
                }
                l2 >>= 1; r2 >>= 1;
            }
            updateFrom(l, r);
        }

        public int maxRight(int l, java.util.function.Predicate<S> g) {
            inclusiveRangeCheck(l);
            if (!g.test(E)) {
                throw new IllegalArgumentException("Identity element must satisfy the condition.");
            }
            if (l == MAX) return MAX;
            l += N;
            pushTo(l);
            S sum = E;
            do {
                l >>= Integer.numberOfTrailingZeros(l);
                if (!g.test(Op.apply(sum, Dat[l]))) {
                    while (l < N) {
                        push(l);
                        l = l << 1;
                        if (g.test(Op.apply(sum, Dat[l]))) {
                            sum = Op.apply(sum, Dat[l]);
                            l++;
                        }
                    }
                    return l - N;
                }
                sum = Op.apply(sum, Dat[l]);
                l++;
            } while ((l & -l) != l);
            return MAX;
        }

        public int minLeft(int r, java.util.function.Predicate<S> g) {
            inclusiveRangeCheck(r);
            if (!g.test(E)) {
                throw new IllegalArgumentException("Identity element must satisfy the condition.");
            }
            if (r == 0) return 0;
            r += N;
            pushTo(r - 1);
            S sum = E;
            do {
                r--;
                while (r > 1 && (r & 1) == 1) r >>= 1;
                if (!g.test(Op.apply(Dat[r], sum))) {
                    while (r < N) {
                        push(r);
                        r = r << 1 | 1;
                        if (g.test(Op.apply(Dat[r], sum))) {
                            sum = Op.apply(Dat[r], sum);
                            r--;
                        }
                    }
                    return r + 1 - N;
                }
                sum = Op.apply(Dat[r], sum);
            } while ((r & -r) != r);
            return 0;
        }

        private void exclusiveRangeCheck(int p) {
            if (p < 0 || p >= MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d is not in [%d, %d).", p, 0, MAX)
                );
            }
        }

        private void inclusiveRangeCheck(int p) {
            if (p < 0 || p > MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d is not in [%d, %d].", p, 0, MAX)
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

        private S[] simulatePushAll() {
            S[] simDat = java.util.Arrays.copyOf(Dat, 2 * N);
            F[] simLaz = java.util.Arrays.copyOf(Laz, 2 * N);
            for (int k = 1; k < N; k++) {
                if (simLaz[k] == Id) continue;
                int lk = k << 1 | 0, rk = k << 1 | 1;
                simDat[lk] = Mapping.apply(simLaz[k], simDat[lk]);
                simDat[rk] = Mapping.apply(simLaz[k], simDat[rk]);
                if (lk < N) simLaz[lk] = Composition.apply(simLaz[k], simLaz[lk]);
                if (rk < N) simLaz[rk] = Composition.apply(simLaz[k], simLaz[rk]);
                simLaz[k] = Id;
            }
            return simDat;
        }

        public String toDetailedString() {
            return toDetailedString(1, 0, simulatePushAll());
        }

        private String toDetailedString(int k, int sp, S[] dat) {
            if (k >= N) return indent(sp) + dat[k];
            String s = "";
            s += toDetailedString(k << 1 | 1, sp + indent, dat);
            s += "\n";
            s += indent(sp) + dat[k];
            s += "\n";
            s += toDetailedString(k << 1 | 0, sp + indent, dat);
            return s;
        }

        private String indent(int n) {
            StringBuilder sb = new StringBuilder();
            while (n --> 0) sb.append(' ');
            return sb.toString();
        }

        public String toSimpleString() {
            S[] dat = simulatePushAll();
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < N; i++) {
                sb.append(dat[i + N]);
                if (i < N - 1) sb.append(',').append(' ');
            }
            sb.append(']');
            return sb.toString();
        }
    }

    static class SegmentTreeNode {
        long val;
        int size;
        public SegmentTreeNode(long val, int size) {
            this.val = val;
            this.size = size;
        }
    }

    static class LazyTag {
        long b, c;
        public LazyTag(long b, long c) {
            this.b = b;
            this.c = c;
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


}
