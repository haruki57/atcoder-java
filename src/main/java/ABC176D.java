import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC176D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int sx = scanner.nextInt()-1;
        int sy = scanner.nextInt()-1;
        int gx = scanner.nextInt()-1;
        int gy = scanner.nextInt()-1;
        char[][] grid = new char[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = scanner.next().toCharArray();
        }
        var uf = new UnionFind(H*W+10000);
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        boolean[][] seen = new boolean[H][W];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if(grid[i][j]=='#')continue;
                if(seen[i][j]) continue;
                seen[i][j]=true;
                Queue<int[]> q = new LinkedList<>();
                q.add(new int[]{i, j});
                while(!q.isEmpty()) {
                    int[] polled = q.poll();
                    int x = polled[0];
                    int y = polled[1];
                    for (int k = 0; k < 4; k++) {
                        int xx = x + dx[k];
                        int yy = y + dy[k];
                        if(xx < 0 || xx >= H || yy < 0 || yy >= W) {
                            continue;
                        }
                        if(seen[xx][yy]) {
                            continue;
                        }
                        if(grid[xx][yy]=='#') {
                            continue;
                        }
                        seen[xx][yy]=true;
                        uf.unite(toI(i, j, H), toI(xx, yy, H));
                        q.add(new int[]{xx, yy});
                    }
                }
            }
        }

        List<Integer>[] g = new List[H*W+10000];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if(grid[i][j]=='#') continue;
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        int x = i - k + 2;
                        int y = j - l + 2;
                        if(x < 0 || x >= H || y < 0 || y >= W) {
                            continue;
                        }
                        if(grid[x][y]=='#') continue;
                        if (uf.same(toI(i, j, H), toI(x, y, H))) {
                            continue;
                        }
                        g[uf.root(toI(i, j, H))].add(uf.root(toI(x, y, H)));
                        g[uf.root(toI(x, y, H))].add(uf.root(toI(i, j, H)));
                    }
                }
            }
        }
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                //System.out.print(uf.root(toI(i, j, H))+" ");
            }
            //System.out.println();
        }

        int start = uf.root(toI(sx, sy, H));
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{start, 0});
        boolean[] seen2 = new boolean[H*W+10000];
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int cur = polled[0];
            int dist = polled[1];
            //System.out.println(cur+" "+uf.root(toI(gx, gy, H)));
            if (cur == uf.root(toI(gx, gy, H))) {
                System.out.println(dist);
                return;
            }
            for (Integer next : g[cur]) {
                if(seen2[next]) continue;
                seen2[next] = true;
                q.add(new int[]{next, dist+1});
            }
        }
        System.out.println(-1);
    }

    private static int toI(int x,int y, int H) {
        return y*H + x;
    }


    static class UnionFind {
        int[] par;
        int[] size;

        public UnionFind(int n) {
            par = new int[n + 1];
            size = new int[n + 1];
            Arrays.fill(par, -1);
            Arrays.fill(size, 1);
        }

        int root(int x){
            while(true) {
                if(par[x] == -1) {
                    break;
                }
                x = par[x];
            }
            return x;
        }

        void unite(int u, int v) {
            int rootU = root(u);
            int rootV = root(v);
            if(rootU == rootV) {
                return;
            }
            if(size[rootU] < size[rootV]) {
                par[rootU] = rootV;
                size[rootV] += size[rootU];
            }
            else {
                par[rootV] = rootU;
                size[rootU] += size[rootV];
            }
        }

        boolean same(int u, int v) {
            return root(u) == root(v);
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
