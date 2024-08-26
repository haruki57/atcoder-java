import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public class ABC343F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        var seg = new SegTree<S>(N, op, new S(0,0,0,0));

        int[] a = new int[N];
        for (int i = 0; i < a.length; i++) {
            a[i]= scanner.nextInt();
            seg.set(i, new S(a[i], 1, 0, 0));
        }
        while(Q-->0) {
            int type = scanner.nextInt();
            if(type==1) {
                int p = scanner.nextInt()-1;
                int x = scanner.nextInt();
                seg.set(p, new S(x, 1, 0, 0));
            } else {
                int l = scanner.nextInt()-1;
                int r = scanner.nextInt();
                out.println(seg.prod(l, r).c2);
            }
        }
    }

    static class S{
        int v1;
        int c1;
        int v2;
        int c2;

        public S(int v1, int c1, int v2, int c2) {
            this.v1 = v1;
            this.c1 = c1;
            this.v2 = v2;
            this.c2 = c2;
        }
    }

    static BinaryOperator<S> op = (s1, s2) -> {
        S res = new S(0, 0, 0, 0);

        if(s1.v1 > s2.v1) {
            res.v1 = s1.v1;
            res.c1 = s1.c1;

            if(s1.v2 > s2.v1) {
                res.v2 = s1.v2;
                res.c2 = s1.c2;
            } else if(s1.v2 == s2.v1) {
                res.v2 = s1.v2;
                res.c2 = s1.c2 + s2.c1;
            } else {
                res.v2 = s2.v1;
                res.c2 = s2.c1;
            }
        } else if(s1.v1 == s2.v1) {
            res.v1 = s1.v1;
            res.c1 = s1.c1 + s2.c1;

            if(s1.v2 > s2.v2) {
                res.v2 = s1.v2;
                res.c2 = s1.c2;
            } else if(s1.v2 == s2.v2) {
                res.v2 = s1.v2;
                res.c2 = s1.c2 + s2.c2;
            } else {
                res.v2 = s2.v2;
                res.c2 = s2.c2;
            }
        } else {
            res.v1 = s2.v1;
            res.c1 = s2.c1;

            if(s2.v2 > s1.v1) {
                res.v2 = s2.v2;
                res.c2 = s2.c2;
            } else if(s2.v2 == s1.v1) {
                res.v2 = s2.v2;
                res.c2 = s2.c2 + s1.c1;
            } else {
                res.v2 = s1.v1;
                res.c2 = s1.c1;
            }
        }
        return res;
    };

    static class SegTree<S> {

        final int MAX;

        final int N;
        final java.util.function.BinaryOperator<S> op;
        final S E;

        final S[] data;

        @SuppressWarnings("unchecked")
        public SegTree(int n, java.util.function.BinaryOperator<S> op, S e) {
            MAX = n;
            int k = 1;
            while (k < n) k <<= 1;
            N = k;
            E = e;
            this.op = op;
            data = (S[]) new Object[N << 1];
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

        public void set(int p, java.util.function.UnaryOperator<S> f) {
            exclusiveRangeCheck(p);
            data[p += N] = f.apply(data[p]);
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
            if (l > r) { throw new IllegalArgumentException(String.format("Invalid range: [%d, %d)", l, r)); }
            inclusiveRangeCheck(l);
            inclusiveRangeCheck(r);
            S sumLeft = E;
            S sumRight = E;
            l += N;
            r += N;
            while (l < r) {
                if ((l & 1) == 1) sumLeft = op.apply(sumLeft, data[l++]);
                if ((r & 1) == 1) sumRight = op.apply(data[--r], sumRight);
                l >>= 1;
                r >>= 1;
            }
            return op.apply(sumLeft, sumRight);
        }

        public S allProd() {
            return data[1];
        }

        public int maxRight(int l, java.util.function.Predicate<S> f) {
            inclusiveRangeCheck(l);
            if (!f.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
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
            if (!f.test(E)) { throw new IllegalArgumentException("Identity element must satisfy the condition."); }
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
                        String.format("Index %d out of bounds for the range [%d, %d).", p, 0, MAX));
            }
        }

        private void inclusiveRangeCheck(int p) {
            if (p < 0 || p > MAX) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for the range [%d, %d].", p, 0, MAX));
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0;i < N;++ i) {
                if (i != 0) sb.append(", ");
                sb.append(data[i + N]);
            }
            sb.append(']');
            return sb.toString();
        }
    }
    /*
    static class SegmentTree {
        public int[] first;
        public int[] firstNum;
        public int[] second;
        public int[] secondNum;
        public int size = 1;

        void init(int N) {
            size = 1;
            while(size < N) {
                size *= 2;
            }
            first = new int[size * 2 + 9];
            firstNum = new int[size * 2 + 9];
            Arrays.fill(first, 0);
            Arrays.fill(firstNum, 0);
            second = new int[size * 2 + 9];
            secondNum = new int[size * 2 + 9];
            Arrays.fill(second, 0);
            Arrays.fill(secondNum, 0);
        }

        void update(int pos, int x) {
            pos = pos + size - 1;
            if(first[pos]==x) {
                firstNum[pos]++;
            } else if(x == second[pos]) {
                secondNum[pos]++;
            } else if(first[pos] < x) {
                secondNum[pos]=firstNum[pos];
                second[pos]=first[pos];
                firstNum[pos]=1;
                first[pos]=x;
            }

            second[pos] = x;
            while(pos >= 2) {
                pos /= 2;
                second[pos] = Math.max(second[pos * 2], second[pos * 2 + 1]);
            }
        }

        int query (int l, int r, int a, int b, int u) {
            if (r <= a || b <= l) {
                return -1000000000;
            }
            if (l <= a && b <= r) {
                return second[u];
            }
            int m = (a + b) / 2;
            int answerL = query(l, r, a, m, u*2);
            int answerR = query(l, r, m, b, u*2 + 1);
            return Math.max(answerL, answerR);
        }

        int query(int l, int r) {
            return query(l, r, 1, size + 1, 1);
        }
    }

     */


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
