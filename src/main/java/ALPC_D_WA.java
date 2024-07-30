import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ALPC_D_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();;
        int W = scanner.nextInt();;
        var mf = new MaximumFlow(H*W+9);
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
                    // WA
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
        for (int i = 0; i < mf.G.length; i++) {
            for (int j = 0; j < mf.G[i].size(); j++) {
                var e = mf.G[i].get(j);
                int from = i;
                int to = e.to;
                //System.out.println(from+" "+to+" "+e.cap+" "+e.rev);
                //System.out.println(Arrays.toString(idxToPoints(from, H))+" "+Arrays.toString(idxToPoints(to, H)));
                if(e.cap==0) {
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

        private int dfs(int pos, int goal, int F) {
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

    static class Edge {
        int to, cap, rev;
        public Edge(int to, int cap, int rev) {
            this.to = to;
            this.cap = cap;
            this.rev = rev;
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
