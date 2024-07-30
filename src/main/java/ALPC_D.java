import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ALPC_D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();;
        int W = scanner.nextInt();;
        var mf = new MaxFlow(H*W+2);
        char[][] s = new char[H][];
        for (int i = 0; i < H; i++) {
            s[i]=scanner.next().toCharArray();
        }
        int start = H*W;
        int goal = H*W+1;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int point = pointsToIdx(i, j, W);
                if ((i+j)%2==0) {
                    // WA!
                    //int[] dy = {0,0,-1, -1};
                    //int[] dx = {-1,1,0,0};

                    // AC
                    final int[] dy = {0, 0, -1, 1};
                    final int[] dx = {1, -1, 0, 0};

                    for (int k = 0; k < 4; k++) {
                        int ii = i + dy[k];
                        int jj = j + dx[k];
                        if (ii<0||jj<0||ii>=H||jj>=W) {
                            continue;
                        }
                        if(s[i][j]=='.'&&s[ii][jj]=='.') {
                            int next = pointsToIdx(ii, jj , W);
                            mf.addEdge(point, next, 1);
                        }
                    }
                }

                if((i+j)%2==0 && s[i][j]=='.') {
                    mf.addEdge(start, point, 1);
                } else if ((i+j)%2==1 && s[i][j]=='.'){
                    mf.addEdge(point, goal, 1);
                }

            }
        }
        System.out.println(mf.maxFlow(start, goal));
        for (MaxFlow.CapEdge e : mf.getEdges()) {
            int from = e.from;
            int to = e.to;
            //System.out.println(from+" "+to+" "+e.cap+" "+e.flow);
            //System.out.println(Arrays.toString(idxToPoints(from, H))+" "+Arrays.toString(idxToPoints(to, H)));
            if(e.flow==0) {
                continue;
            }
            if(from==start||to==start||from==goal||to==goal) {
                continue;
            }
            int[] p1 = idxToPoints(from, W);
            int y1=p1[0];
            int x1=p1[1];
            int[] p2 = idxToPoints(to, W);
            int y2=p2[0];
            int x2=p2[1];
            if(!(s[y1][x1]=='.'&&s[y2][x2]=='.')) {
                continue;
            }

            if(y1+1==y2) {
                s[y1][x1]='v';
                s[y2][x2]='^';
            } else if(y1-1==y2) {
                s[y1][x1]='^';
                s[y2][x2]='v';
            } else if(x1+1==x2) {
                s[y1][x1]='>';
                s[y2][x2]='<';
            } else if(x1-1==x2) {
                s[y1][x1]='<';
                s[y2][x2]='>';
            }
        }

        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }

    }

    static int pointsToIdx(int x, int y, int W) {
        return x*W+y;
    }
    static int[] idxToPoints(int point, int W) {
        return new int[]{point/W, point%W};
    }




    static class MaxFlow {
        private static final class InternalCapEdge {
            final int to;
            final int rev;
            long cap;
            InternalCapEdge(int to, int rev, long cap) { this.to = to; this.rev = rev; this.cap = cap; }
        }
        public static final class CapEdge {
            public final int from, to;
            public final long cap, flow;
            public CapEdge(int from, int to, long cap, long flow) { this.from = from; this.to = to; this.cap = cap; this.flow = flow; }
            @Override
            public boolean equals(Object o) {
                if (o instanceof CapEdge) {
                    CapEdge e = (CapEdge) o;
                    return from == e.from && to == e.to && cap == e.cap && flow == e.flow;
                }
                return false;
            }
        }
        private static final class IntPair {
            final int first, second;
            IntPair(int first, int second) { this.first = first; this.second = second; }
        }

        public static final long INF = Long.MAX_VALUE;

        private final int n;
        private final java.util.ArrayList<IntPair> pos;
        private final java.util.ArrayList<InternalCapEdge>[] g;

        @SuppressWarnings("unchecked")
        public MaxFlow(int n) {
            this.n = n;
            this.pos = new java.util.ArrayList<>();
            this.g = new java.util.ArrayList[n];
            for (int i = 0; i < n; i++) {
                this.g[i] = new java.util.ArrayList<>();
            }
        }

        public int addEdge(int from, int to, long cap) {
            rangeCheck(from, 0, n);
            rangeCheck(to, 0, n);
            nonNegativeCheck(cap, "Capacity");
            int m = pos.size();
            pos.add(new IntPair(from, g[from].size()));
            int fromId = g[from].size();
            int toId = g[to].size();
            if (from == to) toId++;
            g[from].add(new InternalCapEdge(to, toId, cap));
            g[to].add(new InternalCapEdge(from, fromId, 0L));
            return m;
        }

        private InternalCapEdge getInternalEdge(int i) {
            return g[pos.get(i).first].get(pos.get(i).second);
        }

        private InternalCapEdge getInternalEdgeReversed(InternalCapEdge e) {
            return g[e.to].get(e.rev);
        }

        public CapEdge getEdge(int i) {
            int m = pos.size();
            rangeCheck(i, 0, m);
            InternalCapEdge e = getInternalEdge(i);
            InternalCapEdge re = getInternalEdgeReversed(e);
            return new CapEdge(re.to, e.to, e.cap + re.cap, re.cap);
        }

        public CapEdge[] getEdges() {
            CapEdge[] res = new CapEdge[pos.size()];
            java.util.Arrays.setAll(res, this::getEdge);
            return res;
        }

        public void changeEdge(int i, long newCap, long newFlow) {
            int m = pos.size();
            rangeCheck(i, 0, m);
            nonNegativeCheck(newCap, "Capacity");
            if (newFlow > newCap) {
                throw new IllegalArgumentException(
                        String.format("Flow %d is greater than the capacity %d.", newCap, newFlow)
                );
            }
            InternalCapEdge e = getInternalEdge(i);
            InternalCapEdge re = getInternalEdgeReversed(e);
            e.cap = newCap - newFlow;
            re.cap = newFlow;
        }

        public long maxFlow(int s, int t) {
            return flow(s, t, INF);
        }

        public long flow(int s, int t, long flowLimit) {
            rangeCheck(s, 0, n);
            rangeCheck(t, 0, n);
            long flow = 0L;
            int[] level = new int[n];
            int[] que = new int[n];
            int[] iter = new int[n];
            while (flow < flowLimit) {
                bfs(s, t, level, que);
                if (level[t] < 0) break;
                java.util.Arrays.fill(iter, 0);
                while (flow < flowLimit) {
                    long d = dfs(t, s, flowLimit - flow, iter, level);
                    if (d == 0) break;
                    flow += d;
                }
            }
            return flow;
        }

        private void bfs(int s, int t, int[] level, int[] que) {
            java.util.Arrays.fill(level, -1);
            int hd = 0, tl = 0;
            que[tl++] = s;
            level[s] = 0;
            while (hd < tl) {
                int u = que[hd++];
                for (InternalCapEdge e : g[u]) {
                    int v = e.to;
                    if (e.cap == 0 || level[v] >= 0) continue;
                    level[v] = level[u] + 1;
                    if (v == t) return;
                    que[tl++] = v;
                }
            }
        }

        private long dfs(int cur, int s, long flowLimit, int[] iter, int[] level) {
            if (cur == s) return flowLimit;
            long res = 0;
            int curLevel = level[cur];
            for (int itMax = g[cur].size(); iter[cur] < itMax; iter[cur]++) {
                int i = iter[cur];
                InternalCapEdge e = g[cur].get(i);
                InternalCapEdge re = getInternalEdgeReversed(e);
                if (curLevel <= level[e.to] || re.cap == 0) continue;
                long d = dfs(e.to, s, Math.min(flowLimit - res, re.cap), iter, level);
                if (d <= 0) continue;
                e.cap += d;
                re.cap -= d;
                res += d;
                if (res == flowLimit) break;
            }
            return res;
        }

        public boolean[] minCut(int s) {
            rangeCheck(s, 0, n);
            boolean[] visited = new boolean[n];
            int[] stack = new int[n];
            int ptr = 0;
            stack[ptr++] = s;
            visited[s] = true;
            while (ptr > 0) {
                int u = stack[--ptr];
                for (InternalCapEdge e : g[u]) {
                    int v = e.to;
                    if (e.cap > 0 && !visited[v]) {
                        visited[v] = true;
                        stack[ptr++] = v;
                    }
                }
            }
            return visited;
        }

        private void rangeCheck(int i, int minInclusive, int maxExclusive) {
            if (i < 0 || i >= maxExclusive) {
                throw new IndexOutOfBoundsException(
                        String.format("Index %d out of bounds for length %d", i, maxExclusive)
                );
            }
        }

        private void nonNegativeCheck(long cap, String attribute) {
            if (cap < 0) {
                throw new IllegalArgumentException(
                        String.format("%s %d is negative.", attribute, cap)
                );
            }
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
