package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class _043 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int sr = scanner.nextInt()-1;
        int sc = scanner.nextInt()-1;
        int gr = scanner.nextInt()-1;
        int gc = scanner.nextInt()-1;
        char[][] grid = new char[H][];
        int[][] shortest = new int[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = scanner.next().toCharArray();
            Arrays.fill(shortest[i], INF);
        }
        int[] x = {0,1,-1,0};
        int[] y = {1,0,0,-1};
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[2]-o2[2]);
        q.add(new int[]{sr, sc, -1, -1});
        shortest[sr][sc] = -1;
        while(!q.isEmpty()) {
            int currY = q.peek()[0];
            int currX = q.peek()[1];
            int currDist = q.peek()[2];
            int currDir = q.poll()[3];
//            System.out.println(currY + " "+ currX+ " "+ currDist);
            for (int i = 0; i < 4; i++) {
                int nextY = currY + y[i];
                int nextX = currX + x[i];
                if (nextY < 0 || nextX < 0 || nextY >= H || nextX >= W || grid[nextY][nextX] == '#') {
                    continue;
                }
                int nextDist = currDist;
                if (currDir != i) {
                    nextDist++;
                }
                if (shortest[nextY][nextX] < nextDist) {
                    continue;
                }
                shortest[nextY][nextX] = nextDist;
                q.add(new int[]{nextY, nextX, nextDist, i});
            }
        }
        for (int i = 0; i < H; i++) {
            //System.out.println(Arrays.toString(shortest[i]));
        }
        System.out.println(shortest[gr][gc]);
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
