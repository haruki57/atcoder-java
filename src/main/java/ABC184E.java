import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC184E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] map = new char[H+2][W+2];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], '#');
        }
        int sy=1, sx=1, gy=1, gx=1;
        for (int i = 0; i < H; i++) {
            String s = scanner.next();
            for (int j = 0; j < W; j++) {
                map[i+1][j+1]= s.charAt(j);
                if (map[i+1][j+1]=='S') {
                    sy=i+1;
                    sx=j+1;
                } else if (map[i+1][j+1]=='G') {
                    gy=i+1;
                    gx=j+1;
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            //System.out.println(map[i]);
        }
        List<int[]>[] lists = new List[26];
        for (int i = 0; i < lists.length; i++) {
            lists[i]=new ArrayList<>();
        }
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                if ('a' <= map[i][j] && map[i][j] <= 'z') {
                    lists[map[i][j]-'a'].add(new int[]{i, j});
                }
            }
        }
        /*
        for (List<int[]> list : lists) {
            for (int[] ints : list) {
                System.out.println(Arrays.toString(ints));
            }
            System.out.println();
        }
         */
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sy, sx, 0});
        int[][] shortest = new int[H+2][W+2];
        for (int i = 0; i < shortest.length; i++) {
            Arrays.fill(shortest[i], INF);
        }
        shortest[sy][sx] = 0;
        int[] dy = {0,0,1,-1};
        int[] dx = {1,-1,0,0};
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int y = polled[0];
            int x = polled[1];
            int dist = polled[2];
            for (int i = 0; i < 4; i++) {
                int yy = y+dy[i];
                int xx = x+dx[i];
                if (map[yy][xx]=='#') {
                    continue;
                }
                if (shortest[yy][xx]!=INF) {
                    continue;
                }
                shortest[yy][xx]=dist+1;
                q.add(new int[]{yy, xx, dist+1});
            }
            if ('a' <= map[y][x] && map[y][x] <= 'z') {
                int c = map[y][x]-'a';
                for (int[] ints : lists[c]) {
                    int yy = ints[0];
                    int xx = ints[1];
                    if (shortest[yy][xx]!=INF) {
                        continue;
                    }
                    shortest[yy][xx]=dist+1;
                    q.add(new int[]{yy, xx, dist+1});
                }
                lists[c].clear();
            }
        }
        if (shortest[gy][gx]==INF) {
            shortest[gy][gx]=-1;
        }
        System.out.println(shortest[gy][gx]);

        for (int i = 0; i < shortest.length; i++) {
            //System.out.println(Arrays.toString(shortest[i]));
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
