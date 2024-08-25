import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC368D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int x = scanner.nextInt()-1;
            int y = scanner.nextInt()-1;
            g[x].add(y);
            g[y].add(x);
        }
        int[] v = new int[K];
        Arrays.setAll(v, i -> scanner.nextInt()-1);
        Set<Integer> set = new HashSet<>();
        int root = v[0];
        set.add(root);
        int[] parent = new int[N];
        parent[root]=-1;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{root, -1});
        while(!q.isEmpty()) {
            int[] poll = q.poll();
            int cur = poll[0];
            int prev = poll[1];
            for (Integer next : g[cur]) {
                if(next==prev) {
                    continue;
                }
                parent[next]=cur;
                q.add(new int[]{next, cur});
            }
        }
        for (int i = 1; i < K; i++) {
            int cur = v[i];
            if(set.contains(cur)) {
                continue;
            }
            set.add(cur);
            while(true) {
                if(cur==root) {
                    break;
                }
                cur = parent[cur];
                if(set.contains(cur)) {
                    break;
                }
                set.add(cur);
            }
            //System.out.println(i+" "+set);
        }
        //System.out.println(set);
        System.out.println(set.size());
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
