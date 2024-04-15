package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _012 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;


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

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int Q = scanner.nextInt();
        int[][] tile = new int[H+2][W+2];
        var uf = new UnionFind(2009*2009+2009);
        for (int i = 0; i < Q; i++) {
            if (scanner.nextInt() == 1) {
                int r = scanner.nextInt();
                int c = scanner.nextInt();
                tile[r][c]++;
                if (tile[r-1][c] > 0) {
                    uf.unite(2000*r+c, 2000*(r-1)+(c));
                }
                if (tile[r][c+1] > 0) {
                    uf.unite(2000*r+c, 2000*(r)+(c+1));
                }
                if (tile[r+1][c] > 0) {
                    uf.unite(2000*r+c, 2000*(r+1)+(c));
                }
                if (tile[r][c-1] > 0) {
                    uf.unite(2000*r+c, 2000*(r)+(c-1));
                }
            } else {
                int r0 = scanner.nextInt();
                int c0 = scanner.nextInt();
                int r1 = scanner.nextInt();
                int c1 = scanner.nextInt();
/*
                System.out.println(tile[r0][c0]);
                System.out.println(tile[r1][c1]);
                System.out.println(uf.same(r0*2000+c0, r1*2000+c1));
                System.out.println();
*/
                if (tile[r0][c0]>0&&tile[r1][c1]>0&&uf.same(r0*2000+c0, r1*2000+c1)) {
                    out.println("Yes");
                } else {
                    out.println("No");
                }

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
