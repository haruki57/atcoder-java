package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class _043_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int rs = scanner.nextInt();
        int cs = scanner.nextInt();
        int rt = scanner.nextInt();
        int ct = scanner.nextInt();
        char[][] a = new char[H+2][W+2];
        int[][] minTurn = new int[H+2][W+2];
        for (int i = 0; i < a.length; i++) {
            Arrays.fill(a[i], '#');
            Arrays.fill(minTurn[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < H; i++) {
            String s = scanner.next();
            for (int j = 0; j < W; j++) {
                a[i+1][j+1]=s.charAt(j);
            }
        }
        int[] dy = {1,-1,0,0};
        int[] dx = {0,0,1,-1};
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{rs, cs, 0, -1});
        minTurn[rs][cs]=1;
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int y = polled[0];
            int x = polled[1];
            int turn = polled[2];
            int prevDir = polled[3];
            if(turn > minTurn[y][x]) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                int yy = y+dy[i];
                int xx = x+dx[i];
                if(a[yy][xx]=='#'){
                    continue;
                }
                int nTurn = turn + (prevDir == i ? 0 : 1);
                if(minTurn[yy][xx]<nTurn) {
                    continue;
                }
                minTurn[yy][xx]=nTurn;
                q.add(new int[]{yy, xx, nTurn, i});
            }
        }
        System.out.println(minTurn[rt][ct]-1);
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
