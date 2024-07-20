import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ABC363E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int Y = scanner.nextInt();
        int[][] a = new int[H+2][W+2];
        for (int i = 0; i < a.length; i++) {
            Arrays.fill(a[i], INF);
        }
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                a[i+1][j+1]= scanner.nextInt();
            }
        }
        PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });

        for (int i = 0; i < H; i++) {
            q.add(new int[]{a[i+1][1], i+1, 1});
            q.add(new int[]{a[i+1][W], i+1, W});
        }
        for (int i = 0; i < W; i++) {
            q.add(new int[]{a[1][i+1], 1, i+1});
            q.add(new int[]{a[H][i+1], H, i+1});
        }
        /*
        for (int[] ints : q) {
            System.out.println(Arrays.toString(ints));
        }
         */
        boolean[][] sink = new boolean[H+2][W+2];
        long sum = H*W;
        int[] dy = {0,0,1,-1};
        int[] dx = {1,-1,0,0};
        for (int i = 1; i <= Y; i++) {
            while(!q.isEmpty()) {
                int[] peek = q.peek();
                int h = peek[0];
                int y = peek[1];
                int x = peek[2];
                if (h > i) {
                    break;
                }
                q.poll();
                if (sink[y][x]){
                    continue;
                }
                sink[y][x]=true;
                sum -= 1;
                for (int j = 0; j < 4; j++) {
                    int yy = y + dy[j];
                    int xx = x + dx[j];
                    if (sink[yy][xx]) {
                        continue;
                    }
                    q.add(new int[]{a[yy][xx], yy, xx});
                }
            }
            out.println(sum);
        }
        for (int i = 0; i < sink.length; i++) {
            //System.out.println(Arrays.toString(sink[i]));
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
