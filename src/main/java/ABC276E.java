import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ABC276E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] map = new char[H+2][W+2];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], '#');
        }
        int sy=0, sx=0;
        for (int i = 0; i < H; i++) {
            String s = scanner.next();;
            for (int j = 0; j < W; j++) {
                map[i+1][j+1]=s.charAt(j);
                if (map[i+1][j+1] == 'S') {
                    map[i+1][j+1] = '#';
                    sy=i+1;
                    sx=j+1;
                }
            }
        }
        int[] dy = {0,0,1,-1};
        int[] dx = {1,-1,0,0};
        boolean ok = false;
        int[][] distance = new int[H+2][W+2];
        for (int i = 0; i < 4; i++) {
            int y = sy+dy[i];
            int x = sx+dx[i];
            if (map[y][x]=='#') {
                continue;
            }
            for (int j = 0; j < distance.length; j++) {
                Arrays.fill(distance[j], INF);
            }
            distance[y][x]=1;
            Queue<int[]> q  =new LinkedList<>();
            q.add(new int[]{y, x, 1});
            while(!q.isEmpty()) {
                int[] polled = q.poll();
                int curY = polled[0];
                int curX = polled[1];
                int dist = polled[2];
                for (int j = 0; j < 4; j++) {
                    int ny = curY+dy[j];
                    int nx = curX+dx[j];
                    if(ny==sy&&nx==sx&&dist+1>=4) {
                        ok=true;
                        break;
                    }
                    if (map[ny][nx]=='#') {
                        continue;
                    }
                    if (distance[ny][nx]!=INF) {
                        continue;
                    }
                    distance[ny][nx]=dist+1;
                    q.add(new int[]{ny, nx, dist+1});
                }
            }
        }
        System.out.println(ok?"Yes":"No");
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
