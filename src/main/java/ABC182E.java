import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC182E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[][] s = new int[H+2][W+2];
        for (int i = 0; i < W+2; i++) {
            s[0][i]=INF;
            s[H+1][i]=INF;
        }
        for (int i = 0; i < H+2; i++) {
            s[i][0]=INF;
            s[i][W+1]=INF;
        }
        for (int i = 0; i < N; i++) {
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            s[y][x]=10000;
        }
        for (int i = 0; i < M; i++) {
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            s[y][x]=INF;
        }
        int[] dy = {0,0,1,-1};
        int[] dx = {1,-1,0,0};
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                if(s[i][j]==10000) {
                    for (int k = 0; k < 4; k++) {
                        int y = i+dy[k];
                        int x = j+dx[k];
                        int bit = 1<<k;
                        while((s[y][x]&bit)==0) {
                            if(s[y][x]==10000) {
                                break;
                            }
                            s[y][x]|=bit;
                            y = y+dy[k];
                            x = x+dx[k];
                        }
                    }
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < H+2; i++) {
            for (int j = 0; j < W+2; j++) {
                if(1 <= s[i][j] && s[i][j] <= 10000) {
                    sum += 1;
                }
            }
        }
        for (int i = 0; i < s.length; i++) {
            //System.out.println(Arrays.toString(s[i]));
        }
        System.out.println(sum);
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
