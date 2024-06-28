import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC134E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

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


    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        /*
        int N = 100000;
        int[] a = new int[N];
        Random rand = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i]=rand.nextInt();
        }

         */
        a = compressAndUnique(a);


        int[] poss = new int[N*2];
        for (int i = 0; i < a.length; i++) {
            poss[a[i]] = i;
        }
        //System.out.println(Arrays.toString(a));
        //System.out.println(Arrays.toString(poss));
        var segTree = new SegmentTree();
        segTree.init(N*2);
        for (int i = 0; i < a.length; i++) {
            segTree.update(i+1, a[i]);
        }
        int ans = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i]==-1) {
                continue;
            }
            int l = i+1;
            int r = a.length+1;
            while(true) {
                int ret = segTree.query(l, r);
                if (ret==-1) {
                    break;
                }
                r = poss[ret]+1;
                segTree.update(r, -1);
                a[r-1]=-1;
//                System.out.println(l+" "+r+" "+ret);
                if (l==r) {
                    break;
                }
            }
            //System.out.println(Arrays.toString(a));
            ans++;
        }
        System.out.println(ans);
    }
    static int[] compressAndUnique(int[] a) {
        int[] copied = a.clone();
        Arrays.sort(copied);
        Map<Integer, Stack<Integer>> map = new HashMap<>();
        int cnt = 1;
        for (int i = 0; i < copied.length; i++) {
            int j = i+1;
            while(j<copied.length && copied[j]==copied[i]) {
                j++;
            }
            var q = map.getOrDefault(copied[i], new Stack<>());
            for (int k = i; k < j; k++) {
                q.add(cnt);
                cnt++;
            }
            map.put(copied[i], q);

            i=j-1;
        }
        int[] ret = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i]=map.get(a[i]).pop();
        }
        return ret;
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
