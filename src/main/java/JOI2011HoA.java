import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class JOI2011HoA {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        int k = scanner.nextInt();
        char[][] map = new char[M][];
        for (int i = 0; i < M; i++) {
            map[i] = scanner.next().toCharArray();
        }

        int[][] sumJ = new int[M+1][N+1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                sumJ[i][j] = sumJ[i-1][j]+sumJ[i][j-1]-sumJ[i-1][j-1] + (map[i-1][j-1]=='J'?1:0);
            }
        }
        int[][] sumO = new int[M+1][N+1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                sumO[i][j] = sumO[i-1][j]+sumO[i][j-1]-sumO[i-1][j-1] + (map[i-1][j-1]=='O'?1:0);
            }
        }
        int[][] sumI = new int[M+1][N+1];
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                sumI[i][j] = sumI[i-1][j]+sumI[i][j-1]-sumI[i-1][j-1] + (map[i-1][j-1]=='I'?1:0);
            }
        }

        for (int i = 0; i < k; i++) {
            int y1 = scanner.nextInt()-1;
            int x1 = scanner.nextInt()-1;
            int y2 = scanner.nextInt();
            int x2 = scanner.nextInt();
            out.print(sumJ[y2][x2]-sumJ[y2][x1]-sumJ[y1][x2]+sumJ[y1][x1]+" ");
            out.print(sumO[y2][x2]-sumO[y2][x1]-sumO[y1][x2]+sumO[y1][x1]+" ");
            out.println(sumI[y2][x2]-sumI[y2][x1]-sumI[y1][x2]+sumI[y1][x1]);
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
