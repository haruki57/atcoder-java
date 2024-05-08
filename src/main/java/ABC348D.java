import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ABC348D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] map = new char[H+2][W+2];
        int[][] med = new int[H+2][W+2];
        int[][] shortest = new int[H+2][W+2];
        for (int i = 0; i < shortest.length; i++) {
            Arrays.fill(shortest[i], INF);
        }
        int startY = 1, startX = 1;
        int goalY = 1, goalX = 1;
        for (int i = 0; i < H; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 0; j < W; j++) {
                map[i+1][j+1]=s[j];
                if (s[j]=='S') {
                    startY = i+1;
                    startX = j+1;
                }
                if (s[j]=='T') {
                    goalY = i+1;
                    goalX = j+1;
                }
            }
        }
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int e = scanner.nextInt();
            med[r][c] = e;
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{startY, startX, med[startY][startX]});
        shortest[startY][startX] = 0;
        int[] dirY = {0,0,1,-1};
        int[] dirX = {1,-1,0,0};
        while(!q.isEmpty()) {
            int[] poll = q.poll();
            int y = poll[0];
            int x = poll[1];
            int e = poll[2];
            if (med[y][x] > 0 && med[y][x] > e) {
                e = med[y][x];
            }
            if (e==0) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                int yy = y+dirY[i];
                int xx = x+dirX[i];
                int ee = e - 1;
                if (map[yy][xx]==0 || map[yy][xx] == '#') {
                    continue;
                }
                if (shortest[yy][xx] == INF || ee > shortest[yy][xx]) {
                    shortest[yy][xx] = ee;
                    q.add(new int[]{yy, xx, ee});
                }
            }
        }
        for (int i = 0; i < shortest.length; i++) {
            //System.out.println(Arrays.toString(shortest[i]));
        }
        if (shortest[goalY][goalX] != INF) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
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
