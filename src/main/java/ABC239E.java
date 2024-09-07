import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.*;

public class ABC239E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int[][] topK;
    static int[] x;
    static List<Integer>[] g;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i]= scanner.nextInt();
        }
        topK = new int[N][];
        g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            g[x].add(y);
            g[y].add(x);
        }
        rec(0, -1);
        for (int i = 0; i < topK.length; i++) {
            //System.out.println(Arrays.toString(topK[i]));
        }
        while(Q-->0) {
            int v = scanner.nextInt()-1;
            int k = scanner.nextInt()-1;
            out.println(topK[v][k]);
        }

    }

    private static int[] rec(int cur, int prev) {
        List<Integer> list = new ArrayList<>();
        list.add(x[cur]);
        for (Integer next : g[cur]) {
            if(next==prev) {
                continue;
            }
            int[] rec = rec(next, cur);
            for (int i : rec) {
                list.add(i);
            }
        }
        int[] ks = new int[Math.min(20, list.size())];
        Collections.sort(list);
        for (int i = 0; i < ks.length; i++) {
            int ii = list.size()-i-1;
            ks[i] = list.get(ii);
        }
        return topK[cur] = ks;
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
