import com.sun.security.jgss.GSSUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC281E_BIT {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static long sum = 0;
    static int leftSize = 0, rightSize = 0;
    static int N,M,K;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        M = scanner.nextInt();
        K = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        Pair<Integer, Integer>[] pairs = new Pair[N];
        for (int i = 0; i < pairs.length; i++) {
            pairs[i]=new Pair(a[i],i);
        }
        Arrays.sort(pairs);
        int[] order = new int[N];
        for (int i = 0; i < N; i++) {
            order[pairs[i].second] = i;
        }

        long[] empty = new long[N+1];
        long[] empty2 = new long[N+1];
        var bit = new BinaryIndexedTree(empty);
        var sum = new BinaryIndexedTree(empty2);
        for (int i = 0; i < M; i++) {
            push(a[i], order[i], bit, sum);
        }
        out.println(get(K, bit, sum));
        for (int i = M; i < N; i++) {
            push(a[i], order[i], bit, sum);
            pop(a[i-M], order[i-M], bit, sum);
            out.println(get(K, bit, sum));
        }
    }

    static void push(int x, int idx, BinaryIndexedTree bit, BinaryIndexedTree sum) {
        bit.add(idx+1, 1);
        sum.add(idx+1, x);
    }

    static void pop(int x, int idx, BinaryIndexedTree bit, BinaryIndexedTree sum) {
        bit.add(idx+1, -1);
        sum.add(idx+1, -x);
    }

    static long get(int k, BinaryIndexedTree bit, BinaryIndexedTree sum) {
        int kthIdx = kth(k, bit);
        return sum.sum(kthIdx);
    }

    static int kth(int k, BinaryIndexedTree bit) {
        int ok = bit.data.length, ng = 0;
        while(Math.abs(ok-ng) > 1) {
            int mid = (ok+ng)/2;
            if (bit.sum(mid) >= k) {
                ok = mid;
            } else {
                ng = mid;
            }
        }
        return ok;
    }

    static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A,B>>{
        A first;
        B second;
        public Pair(A a, B b) {
            this.first=a;
            this.second=b;
        }

        @Override
        public int compareTo(Pair<A,B> o) {
            int compareFirst = this.first.compareTo(o.first);
            if (compareFirst==0) {
                return this.second.compareTo(o.second);
            }
            return compareFirst;
        }

        @Override
        public String toString() {
            return "[" + this.first + ", " + this.second + "]";
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
