import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC351D {
    static int MOD = 1000000007;
    static int INF = 10000000;

    static boolean[][] visitedGlobal;
    static void run (final FastScanner scanner, final PrintWriter out) {
        //out.println(1000 + " " +1000);
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                //out.print(".");
            }
            //out.println();
        }
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        //H=W=1000;
        char[][] map = new char[H+2][W+2];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], 'x');
        }
        visitedGlobal = new boolean[map.length+2][map[0].length+2];
        for (int i = 1; i <= H; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 1; j <= W; j++) {
                map[i][j] = s[j-1];
                //map[i][j]='.';
            }
        }
        long ans = 1;
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                if (map[i][j]=='.' && !visitedGlobal[i][j]) {
                    if (map[i+1][j]=='#'||
                            map[i-1][j]=='#'||
                            map[i][j+1]=='#'||
                            map[i][j-1]=='#'
                    ) {
                        continue;
                    }
                    ans=Math.max(ans, count(map, i, j));
                    //System.out.println(ans);
                    //System.out.println(i+" "+j);
                }

            }
        }
        System.out.println(ans);
    }

    static int[] dy = {0,0,1,-1};
    static int[] dx = {1,-1,0,0};
    private static long count(char[][] map, int h, int w) {
        Set<Long> visitedLocal = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{h, w});
        long ret = 0;
        while(!q.isEmpty()) {
            int curY=q.peek()[0];
            int curX=q.poll()[1];
            /*
            if (visitedLocal.contains((long)curY*INF+curX)) {
                continue;
            }

             */
            ret++;
            visitedLocal.add((long)curY*INF+curX);
            if (map[curY+1][curX]=='#'||
                    map[curY-1][curX]=='#'||
                    map[curY][curX+1]=='#'||
                    map[curY][curX-1]=='#'
            ) {
                continue;
            }
            visitedGlobal[curY][curX]=true;
            //System.out.println((long)curY*INF+curX);
            //System.out.println("hoge");
            for (int i = 0; i < 4; i++) {
                int nY = curY+dy[i];
                int nX = curX+dx[i];
                if (map[nY][nX]!= '.') {
                    continue;
                }
                if (visitedLocal.contains((long)nY*INF+nX)) {
                    continue;
                }
                visitedLocal.add((long)nY*INF+nX);
                q.add(new int[]{nY, nX});
            }
        }
        return ret;
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
