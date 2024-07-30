import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ALPC_E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static final long M = 1000000000L;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();;
        int K = scanner.nextInt();;
        long[][] s = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s[i][j]= scanner.nextInt();
            }
        }
        var minFlow = new MinCostFlow(N*N+2);

        MinCostFlow g = new MinCostFlow(N * 2 + 2);
        int start = N * 2;
        int goal = N * 2 + 1;
        g.addEdge(start, goal, INF, 0);
        for (int i = 0; i < N; i++) {
            g.addEdge(start, i, K, 0);
            g.addEdge(N + i, goal, K, 0);
            for (int j = 0; j < N; j++) {
                g.addEdge(i, N + j, 1,  -s[i][j]);
            }
        }

        long f = g.minCostFlow(start, goal, K * N);
        System.out.println(-f);
        for (int i = 0; i < N; i++) {
            boolean[] sel = new boolean[N];
            for (int j = 0; j < g.edge[i].size(); j++) {
                MinCostFlow.Edge e = g.edge[i].get(j);
                if ( e.to < N || e.to >= N * 2 ) continue;
                if ( e.cap == 0 ) {
                    sel[e.to - N] = true;
                }
            }
            for (int j = 0; j < N; j++) {
                System.out.print(sel[j] ? 'X' : '.');
            }
            System.out.println();
        }
    }

    static class MinCostFlow {
        // https://atcoder.jp/contests/practice2/submissions/27178418
        class Edge {
            int to = 0;
            int cap = 0;
            long cost = 0;
            int rev = 0;
            Edge(int to, int cap, long cost, int rev) {
                this.to = to;
                this.cap = cap;
                this.cost = cost;
                this.rev = rev;
            }
        }
        static class Step implements Comparable<Step> {
            long d = 0;
            int pos = 0;
            Step(int pos, long d) {
                this.pos = pos;
                this.d = d;
            }
            @Override
            public int compareTo(MinCostFlow.Step o) {
                if ( d != o.d ) {
                    return d < o.d ? -1 : 1;
                } else {
                    return pos - o.pos;
                }
            }
        }

        int V = 0;
        ArrayList<Edge>[] edge = null;
        long[] h = null;
        long[] dist = null;
        int[] prevv = null;
        int[] preve = null;

        public MinCostFlow(int V) {
            this.V = V;
            h = new long[V];
            dist = new long[V];
            prevv = new int[V];
            preve = new int[V];
            edge = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                edge[i] = new ArrayList<>();
            }
        }

        public void addEdge(int from, int to, int cap, long cost) {
            edge[from].add(new Edge(to, cap, cost, edge[to].size()));
            edge[to].add(new Edge(from, 0, -cost, edge[from].size() - 1));
        }

        public long minCostFlow(int s, int t, int f) {
            long res = 0;
            Arrays.fill(h, 0);
            while ( f > 0 ) {
                PriorityQueue<Step> queue = new PriorityQueue<>();
                Arrays.fill(dist, Long.MAX_VALUE);
                dist[s] = 0;
                queue.add(new Step(s, 0));
                while ( queue.size() > 0 ) {
                    Step st = queue.poll();
                    int pos = st.pos;
                    if ( dist[pos] < st.d ) continue;
                    for (int i = 0; i < edge[pos].size() ; i++) {
                        Edge e = edge[pos].get(i);
                        if ( e.cap > 0 && dist[e.to] > dist[pos] + e.cost + h[pos] - h[e.to] ) {
                            dist[e.to] = dist[pos] + e.cost + h[pos] - h[e.to];
                            prevv[e.to] = pos;
                            preve[e.to] = i;
                            queue.add(new Step(e.to, dist[e.to]));
                        }
                    }
                }
                if ( dist[t] == Long.MAX_VALUE) return -1;
                for (int pos = 0; pos < V; pos++) h[pos] += dist[pos];

                int d = f;
                for (int pos = t; pos != s ; pos = prevv[pos]) {
                    d = Math.min(d, edge[prevv[pos]].get(preve[pos]).cap);
                }
                f -= d;
                res += d * h[t];
                for (int pos = t; pos != s ; pos = prevv[pos]) {
                    Edge e = edge[prevv[pos]].get(preve[pos]);
                    e.cap -= d;
                    edge[pos].get(e.rev).cap += d;
                }
            }
            return res;
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
