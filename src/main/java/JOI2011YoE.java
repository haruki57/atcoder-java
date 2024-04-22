import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class JOI2011YoE {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int N = scanner.nextInt();
        char[][] map = new char[H][];
        for (int i = 0; i < H; i++) {
            map[i] = scanner.next().replace("S", "0").toCharArray();
        }
        int ans = 0;
        for (int cheese = 0; cheese < N; cheese++) {
            int startChar = '0' + cheese;
            int goalChar = '0' + cheese + 1;
            int startY=0, startX=0;
            int goalY=0, goalX=0;
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map[i][j] == startChar) {
                        startY = i;
                        startX = j;
                    }
                    if (map[i][j] == goalChar) {
                        goalY = i;
                        goalX = j;
                    }
                }
            }
            //System.out.println(startY + " " + startX);
            //System.out.println(goalY + " " + goalX);

            boolean[][] visited = new boolean[H][W];
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{startY, startX, 0});
            visited[startY][startX] = true;
            label: while(!q.isEmpty()) {
                int[] poll = q.poll();
                int curY = poll[0];
                int curX = poll[1];
                int curD = poll[2];

                //System.out.println(curY + " " +curX+ " " +curD);
                for (int i = 0; i < 4; i++) {
                    int nextY = curY + dirY[i];
                    int nextX = curX + dirX[i];
                    if (nextY == goalY && nextX == goalX) {
                        ans += curD + 1;
                        //System.out.println("ans:"+ans);
                        break label;
                    }

                    if (nextY < 0 || nextX < 0 || nextY >= H ||nextX >= W) {
                        continue;
                    }
                    if (map[nextY][nextX] == 'X' || visited[nextY][nextX]) {
                        continue;
                    }
                    visited[nextY][nextX] = true;
                    q.add(new int[]{nextY, nextX, curD+1});
                }
            }
        }
        System.out.println(ans);
    }
    static int[] dirY = {0,0,-1,1};
    static int[] dirX = {-1,1,0,0};
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
