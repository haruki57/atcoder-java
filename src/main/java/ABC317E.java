import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ABC317E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] map = new char[H][];
        for (int i = 0; i < map.length; i++) {
            map[i]=scanner.next().toCharArray();
        }
        boolean[][] monitored = new boolean[H][W];
        // >
        boolean eye = false;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j]=='>') {
                    eye = true;
                } else if (map[i][j]=='.' && eye) {
                    monitored[i][j]=true;
                } else {
                    eye = false;
                }
            }
            eye=false;
            // <
            for (int j = W-1; j >= 0; j--) {
                if (map[i][j]=='<') {
                    eye = true;
                } else if (map[i][j]=='.' && eye) {
                    monitored[i][j]=true;
                } else {
                    eye = false;
                }
            }
            eye=false;
        }

        for (int j = 0; j < W; j++) {
            eye=false;
            // v
            for (int i = 0; i < H; i++) {
                if (map[i][j]=='v') {
                    eye = true;
                } else if (map[i][j]=='.' && eye) {
                    monitored[i][j]=true;
                } else {
                    eye = false;
                }
            }
            eye=false;
            // ^
            for (int i = H-1; i >= 0; i--) {
                if (map[i][j]=='^') {
                    eye = true;
                } else if (map[i][j]=='.' && eye) {
                    monitored[i][j]=true;
                } else {
                    eye = false;
                }
            }
        }
        int[] dy = {0,0,-1,1};
        int[] dx = {-1,1,0,0};
        int[][] shortest = new int[H][W];
        for (int i = 0; i < shortest.length; i++) {
            Arrays.fill(shortest[i], INF);
        }
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j]=='S') {
                    shortest[i][j]=0;
                    q.add(new int[]{i,j,0});
                    break;
                }

            }
        }
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int y = polled[0];
            int x = polled[1];
            int dist = polled[2];
            //System.out.println(y+" "+x);
            for (int i = 0; i < 4; i++) {
                int yy =y + dy[i];
                int xx =x + dx[i];
                if (yy < 0 || xx < 0 || yy >= H || xx >= W) {
                    continue;
                }
                if ((map[yy][xx]=='.' || map[yy][xx]=='G') && !monitored[yy][xx] && dist + 1 < shortest[yy][xx]) {
                    shortest[yy][xx] = dist + 1;
                    q.add(new int[]{yy, xx, dist+1});
                }
            }
        }
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j]=='G') {
                    if (shortest[i][j]==INF) {
                        System.out.println(-1);
                    } else {
                        System.out.println(shortest[i][j]);
                    }
                    return;
                }
            }
        }

        /*
        var uf = new UnionFind(H*W);
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!(map[i][j]=='.' && !monitored[i][j])) {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
                    int ii =i + dy[k];
                    int jj =j + dx[k];
                    if (ii < 0 || jj < 0 || ii >= H || jj >= W) {
                        continue;
                    }
                    if (map[ii][jj]=='.' && !monitored[ii][j]) {
                        uf.unite(i*H+j, ii*H+jj);
                    }
                }
                if (monitored[i][j]) {
                    System.out.print('!');
                } else {
                    System.out.print(map[i][j]);
                }
            }
            //System.out.println();
        }


        if (uf.same(0+0, (H-1)*H+(W-1))) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
         */

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
