import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class ABC372D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] h = new int[N];
        Arrays.setAll(h, i -> scanner.nextInt());
        var seg = new SegmentTree();
        seg.init(N);
        for (int i = 0; i < h.length; i++) {
            seg.update(i+1, h[i]);
        }
        int[] imos = new int[N+2];
        l:for (int i = 1; i < N; i++) {
            int left = 1;
            int right = i+1;
            imos[i+1]--;
            //System.out.println(i+" "+h[i]+" "+left+" "+right);
            while(Math.abs(right-left) > 1) {
                int mid = (left+right) /2;
                int maxLeft = seg.query(left, mid);
                int maxRight = seg.query(mid, right);
                //System.out.println(left+" "+right+" "+mid+" "+maxLeft+" "+maxRight);
                if(maxRight > h[i]) {
                    left = mid;
                } else if(maxLeft > h[i]) {
                    right = mid;
                } else {
                    imos[left]++;
                    //System.out.println();
                    continue l;
                }
            }
            imos[right-1]++;
            //System.out.println(i+" "+h[i]+" "+right);
            //System.out.println();
        }

        for (int i = 1; i < imos.length; i++) {
            imos[i] += imos[i-1];
        }
        for (int i = 1; i <= N; i++) {
            out.print(imos[i]+" ");
        }
        out.println();
        /*
        TreeSet<Pair<Integer, Integer>> set = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            imos[i]--;
            Pair<Integer, Integer> pair = new Pair<>(h[i], -i);
            Pair<Integer, Integer> higher = set.higher(pair);
            if(higher==null) {
                imos[0]++;
                System.out.println(i+" "+h[i]+" "+0+" "+i);
                System.out.println(higher);
            } else {
                imos[-higher.second]++;
                System.out.println(i+" "+h[i]+" "+(-higher.second)+" "+i);
                System.out.println(higher);
            }

            set.add(pair);
        }
        System.out.println(Arrays.toString(imos));
        for (int i = 0; i < N; i++) {
            if(i > 0) {
                imos[i] += imos[i-1];
            }
        }
        for (int imo : imos) {
            out.print(imo+" ");
        }
        out.println();
        int[] ans = new int[N];
        int sum = 0;
        for (int i = 0; i < ans.length; i++) {
            sum += imos[i];
            ans[i]=sum;
        }
        for (int an : ans) {
            out.print(an+" ");
        }
        out.println();

         */
    }

    // 1-indexed!
    static class SegmentTree {
        public int[] dat;
        public int size = 1;

        void init(int N) {
            size = 1;
            while(size < N) {
                size *= 2;
            }
            dat = new int[size * 2 + 9];
            Arrays.fill(dat, 0);
        }

        void update(int pos, int x) {
            pos = pos + size - 1;
            dat[pos] = x;
            while(pos >= 2) {
                pos /= 2;
                dat[pos] = Math.max(dat[pos * 2], dat[pos * 2 + 1]);
            }
        }

        int query (int l, int r, int a, int b, int u) {
            if (r <= a || b <= l) {
                return -1000000000;
            }
            if (l <= a && b <= r) {
                return dat[u];
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
