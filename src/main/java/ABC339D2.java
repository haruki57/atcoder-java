import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

// AC

public class ABC339D2 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static char[][] map;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        map = new char[N+2][N+2];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], '#');
        }
        int y1=INF, x1=INF, y2=INF, x2=INF;
        for (int i = 1; i <= N; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 1; j <= N; j++) {
                map[i][j]=s[j-1];
                if (map[i][j]=='P') {
                    if (y1==INF) {
                        y1 = i;
                        x1 = j;
                    } else {
                        y2 = i;
                        x2 = j;
                    }
                }
            }
        }
        int[][][][] shortest = new int[N+2][N+2][N+2][N+2];
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                for (int k = 0; k < N + 2; k++) {
                    for (int l = 0; l < N + 2; l++) {
                        shortest[i][j][k][l]=INF;
                    }
                }
            }
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y1, x1, y2, x2, 0});
        int[] dirY = {0,0,-1,1};
        int[] dirX = {-1,1,0,0};

        Queue<int[]> q2 = new LinkedList<>();
        int[][] shortest2 = new int[N+2][N+2];
        q2.add(new int[]{y1, x1, 0});
        for (int i = 0; i < shortest2.length; i++) {
            Arrays.fill(shortest2[i], INF);
        }
        boolean reachable = false;
        while(!q2.isEmpty()) {
            int yy = q2.peek()[0];
            int xx = q2.peek()[1];
            int cost = q2.poll()[2];
            if(yy==y2&&xx==x2) {
                reachable=true;
                break;
            }
            for (int i = 0; i < 4; i++) {
                int yn = yy+dirY[i];
                int xn = xx+dirX[i];
                if (map[yn][xn]=='#') {
                    continue;
                }
                if (shortest2[yn][xn]!=INF) {
                    continue;
                }
                shortest2[yn][xn]=cost+1;
                q2.add(new int[]{yn, xn, cost+1});
            }
        }
        if (!reachable) {
            System.out.println(-1);
            return;
        }

        /*
        System.out.println(y1+" "+x1+ " "+y2+" "+x2);
        System.out.println(toLong(y1, x1, y2, x2));
        System.out.println(Long.toBinaryString(toLong(y1, x1, y2, x2)));
        System.out.println(Arrays.toString(fromLong(toLong(y1, x1, y2, x2))));
        */
        long bitMask = 0b1111111111;
        while(!q.isEmpty()){
            int[] poll = q.poll();
            int yy1 = poll[0];
            int xx1 = poll[1];
            int yy2 = poll[2];
            int xx2 = poll[3];
            int curCost = poll[4];
            if (yy1==yy2&&xx1==xx2) {
                //System.out.println(shortest);
                System.out.println(curCost);
                return;
            }
            for (int i = 0; i < 4; i++) {
                int yy1n = yy1 + dirY[i];
                int xx1n = xx1 + dirX[i];
                int yy2n = yy2 + dirY[i];
                int xx2n = xx2 + dirX[i];
                if (map[(int)yy1n][(int)xx1n]=='#') {
                    yy1n -= dirY[i];
                    xx1n -= dirX[i];
                }
                if (map[(int)yy2n][(int)xx2n]=='#') {
                    yy2n -= dirY[i];
                    xx2n -= dirX[i];
                }
                // System.out.println(yy1n+" "+xx1n+" "+yy2n+" "+xx2n);
                if (shortest[yy1n][xx1n][yy2n][xx2n] > curCost + 1) {
                    shortest[yy1n][xx1n][yy2n][xx2n] = curCost + 1;
                    q.add(new int[]{yy1n, xx1n, yy2n, xx2n, curCost+1});
                }
            }
        }
        System.out.println(-1);
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
