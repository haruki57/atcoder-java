package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _072 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static char[][] grid;
    static boolean[][] used;
    static int[] dirY = {0,0, -1, 1};
    static int[] dirX = {-1, 1, 0, 0};

    static int backtrack(int y, int x, int len, int startY, int startX) {
        //System.out.println(y+ " " + x);
        if (len >= 3 && y==startY && x==startX) {
            //System.out.println(len);
            return len;
        }
        int ret = -1;
        for (int i = 0; i < dirY.length; i++) {
            int nextY = y + dirY[i];
            int nextX = x + dirX[i];
            if (nextY<0||nextX<0||nextY>=grid.length||nextX>=grid[0].length) {
                continue;
            }

            if (used[nextY][nextX]) {
                continue;
            }
            if (grid[nextY][nextX] == '#') {
                continue;
            }
            used[nextY][nextX]=true;
            ret = Math.max(ret, backtrack(nextY, nextX, len+1, startY, startX));
            used[nextY][nextX]=false;
        }
        return ret;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        grid = new char[H][W];
        used=new boolean[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = scanner.next().toCharArray();
        }
        int ans = -1;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                //used[i][j]=true;
                ans = Math.max(ans, backtrack(i, j, 0, i, j));
                //used[i][j]=false;
                //System.out.println();
            }
        }
        System.out.println(ans);
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
