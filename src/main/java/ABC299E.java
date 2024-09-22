import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC299E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            g[x].add(y);
            g[y].add(x);
        }
        int K = scanner.nextInt();
        int[] p = new int[K];
        int[] d = new int[K];

        for (int i = 0; i < K; i++) {
            p[i]= scanner.nextInt()-1;
            d[i]= scanner.nextInt();
        }
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i],INF);
            dist[i][i]=0;
        }
        for (int i = 0; i < N; i++) {
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{i, 0});
            while(!q.isEmpty()) {
                int[] polled = q.poll();
                int cur = polled[0];
                int nDist = polled[1]+1;
                for (Integer next : g[cur]) {
                    if(dist[i][next]>nDist) {
                        dist[i][next]=dist[next][i]=nDist;
                        q.add(new int[]{next, nDist});
                    }
                }
            }
        }
        Set<Integer> mustWhite = new HashSet<>();
        for (int i = 0; i < K; i++) {
            int cur = p[i];
            for (int j = 0; j < N; j++) {
                if (dist[cur][j]<d[i]) {
                    mustWhite.add(j);
                }
            }
        }

        if(mustWhite.size() == N) {
            out.println("No");
            return;
        }
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            if(!mustWhite.contains(i)) {
                ans[i]=1;
            }
        }

        for (int i = 0; i < K; i++) {
            boolean hasBlack = false;
            for (int j = 0; j < N; j++) {
                if (dist[p[i]][j]==d[i] && ans[j]==1) {
                    hasBlack = true;
                    break;
                }
            }
            if(!hasBlack) {
                out.println("No");
                return;
            }
        }

        out.println("Yes");
        for (int an : ans) {
            out.print(an);
        }
        out.println();
        /*
        for (Integer black : mustBlack) {
            if(mustWhite.contains(black)) {
                out.println("No");
                return;
            }
        }
        for (Integer white : mustWhite) {
            if(mustBlack.contains(white)) {
                out.println("No");
                return;
            }
        }
        if(mustWhite.size() == N) {
            out.println("No");
            return;
        }
        int[] ans = new int[N];

        // black node must be at least 1
        for (int i = 0; i < N; i++) {
            if(!mustWhite.contains(i)) {
                ans[i]=1;
                break;
            }
        }

        for (Integer i : mustBlack) {
            ans[i]=1;
        }

         */
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
