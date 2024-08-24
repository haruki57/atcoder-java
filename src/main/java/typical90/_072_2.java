package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class _072_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] grid = new char[H+2][W+2];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '#');
        }
        for (int i = 0; i < H; i++) {
            String s = scanner.next();
            for (int j = 0; j < W; j++) {
                grid[i+1][j+1]=s.charAt(j);
            }
        }
        long ans = -1;
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                HashSet<Integer> visited = new HashSet<>();
                visited.add(toPos(i, j));
                if(grid[i][j]=='#') {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
                    int sx = i + dx[k];
                    int sy = j + dy[k];
                    if(grid[sx][sy]=='#') {
                        continue;
                    }
                    for (int l = k+1; l < 4; l++) {
                        int gx = i + dx[l];
                        int gy = j + dy[l];
                        if(grid[gx][gy]=='#') {
                            continue;
                        }
                        visited.add(toPos(sx, sy));
                        ans = Math.max(ans, maxLen(grid, sx, sy, gx, gy, visited));
                        visited.remove(toPos(sx, sy));
                    }
                }
            }
        }
        System.out.println(ans);
    }

    private static long maxLen(char[][] grid, int sx, int sy, int gx, int gy, HashSet<Integer> visited) {
        if(sx==gx && sy==gy ) {
            return visited.size();
        }
        long ret = -1;
        for (int i = 0; i < 4; i++) {
            int nx = sx + dx[i];
            int ny = sy + dy[i];
            if(grid[nx][ny]=='#') {
                continue;
            }
            int pos = toPos(nx, ny);
            if (visited.contains(pos)) {
                continue;
            }
            visited.add(pos);
            ret = Math.max(ret, maxLen(grid, nx, ny, gx, gy, visited));
            visited.remove(pos);
        }
        return ret;
    }

    private static int toPos(int x, int y) {
        return x * 10000 + y;
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
