package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class A69 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static class Edge {
        int to, cap, rev;
        public Edge(int to, int cap, int rev) {
            this.to = to;
            this.cap = cap;
            this.rev = rev;
        }
    }
    static class MaximumFlow {
        int size;
        boolean[] used;
        ArrayList<Edge>[] G;
        MaximumFlow(int N) {
            size = N + 9;
            used = new boolean[N+9];
            G = new ArrayList[N+9];
            for (int i = 0; i < size; i++) {
                G[i] = new ArrayList<>();
            }
        }

        void addEdge(int a,int b,int c) {
            int currentGa = G[a].size();
            int currentGb = G[b].size();
            G[a].add(new Edge(b, c, currentGb));
            G[b].add(new Edge(a, 0, currentGa));
        }

        int dfs(int pos, int goal, int F) {
            if (pos == goal) return F;
            used[pos] = true;

            for (int i = 0; i < G[pos].size(); i++) {
                if (G[pos].get(i).cap == 0) {
                    continue;
                }
                if (used[G[pos].get(i).to]) {
                    continue;
                }
                int flow = dfs(G[pos].get(i).to, goal, Math.min(F, G[pos].get(i).cap));
                if (flow >= 1) {
                    G[pos].get(i).cap -= flow;
                    G[G[pos].get(i).to].get(G[pos].get(i).rev).cap += flow;
                    return flow;
                }
            }
            return 0;
        }
        int maxFlow(int s, int t) {
            int totalFlow = 0;
            while(true) {
                for (int i = 0; i < size; i++) {
                    used[i] = false;
                }
                int F = dfs(s, t, INF);
                if (F == 0) {
                    break;
                }
                totalFlow += F;
            }

            return totalFlow;
        }
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        var mf = new MaximumFlow(N*3);
        for (int i = 1; i <= N; i++) {
            String s = scanner.next();
            for (int j = 1; j <= s.length(); j++) {
                if (s.charAt(j-1) == '#') {
                    mf.addEdge(i, N+j, 1);
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            mf.addEdge(0, i, 1);
            mf.addEdge(N +i, N*2+2, 1);
        }
        System.out.println(mf.maxFlow(0, N*2+2));

    }

    public static void main(final String[] args) {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner scanner = new FastScanner();
        run(scanner, out);
        out.flush();
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
