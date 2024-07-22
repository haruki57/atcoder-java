import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ABC245E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[N];
        Arrays.setAll(b, i -> scanner.nextInt());
        int[] c = new int[M];
        Arrays.setAll(c, i -> scanner.nextInt());
        int[] d = new int[M];
        Arrays.setAll(d, i -> scanner.nextInt());

        int[][] chocos = new int[N][2];
        for (int i = 0; i < N; i++) {
            chocos[i][0]=a[i];
            chocos[i][1]=b[i];
        }
        int[][] boxes = new int[M][2];
        for (int i = 0; i < M; i++) {
            boxes[i][0]=c[i];
            boxes[i][1]=d[i];
        }


        Arrays.sort(chocos, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0]==o2[0]) {
                    return o2[1]-o1[1];
                }
                return o2[0]-o1[0];
            }
        });
        Arrays.sort(boxes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0]==o2[0]) {
                    return o1[1]-o2[1];
                }
                return o1[0]-o2[0];
            }
        });
        int pt = M-1;
        TreeMap<Integer, Integer> lengths = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            while(pt >= 0 && boxes[pt][0] >= chocos[i][0]) {
                lengths.put(boxes[pt][1], lengths.getOrDefault(boxes[pt][1], 0) + 1);
                pt--;
            }
            Integer bestPair = lengths.ceilingKey(chocos[i][1]); // chocos[i][1]以上で一番小さいやつ
            if (bestPair == null) {
                out.println("No");
                return;
            } else {
                if(lengths.get(bestPair) == 1) lengths.remove(bestPair);
                else lengths.put(bestPair, lengths.get(bestPair) - 1);
            }
        }
        out.println("Yes");
    }

    static int lowerBound(int[][] A, int x, int zeroOrOne) {
        int l = -1, r = A.length;
        while(Math.abs(r - l) > 1) {
            int mid = (l + r) / 2;
            if(A[mid][zeroOrOne] >= x) {
                r = mid;
            }
            else {
                l = mid;
            }
        }
        return r;
    }
    static int upperBound(int[][] A, int x, int zeroOrOne) {
        int l = -1, r = A.length;
        while(Math.abs(r - l) > 1) {
            int mid = (l + r) / 2;
            if(A[mid][zeroOrOne] > x) {
                r = mid;
            }
            else {
                l = mid;
            }
        }
        return r;
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
                dat[pos] = Math.min(dat[pos * 2], dat[pos * 2 + 1]);
            }
        }

        int query (int l, int r, int a, int b, int u) {
            if (r <= a || b <= l) {
                return 1000000000;
            }
            if (l <= a && b <= r) {
                return dat[u];
            }
            int m = (a + b) / 2;
            int answerL = query(l, r, a, m, u*2);
            int answerR = query(l, r, m, b, u*2 + 1);
            return Math.min(answerL, answerR);
        }

        int query(int l, int r) {
            return query(l, r, 1, size + 1, 1);
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
