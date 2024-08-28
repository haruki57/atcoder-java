package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class B65 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int[] level;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int T = scanner.nextInt()-1;
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            g[a].add(b);
            g[b].add(a);
        }
        Queue<Integer> q = new LinkedList<>();
        q.add(T);
        level = new int[N];
        rec(g, T, -1);
        for (int i = 0; i < N; i++) {
            out.print(level[i]+" ");
        }
        out.println();

        /*
        int[] parent = new int[N];
        Arrays.fill(level, -1);
        parent[T]=-1;
        level[T]=0;
        int maxLevel = 0;
        while(!q.isEmpty()) {
            int cur = q.poll();
            int nextLevel = level[cur]+1;
            maxLevel = Math.max(maxLevel, nextLevel);
            for (Integer next : g[cur]) {
                if(level[next]!=-1) {
                    continue;
                }
                parent[next]=cur;
                level[next]=nextLevel;
                q.add(next);
            }
        }
        for (int i = 0; i < N; i++) {
            out.print((maxLevel-level[i]-1)+" ");
        }

         */
    }
    static int rec(List<Integer>[] g, int cur, int prev) {
        int ret = 0;
        for (Integer next : g[cur]) {
            if(next==prev) {
                continue;
            }
            ret = Math.max(ret, rec(g, next, cur)+1);
        }
        return level[cur]=ret;
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
