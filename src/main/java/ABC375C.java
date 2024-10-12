import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC375C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[][] grid = new char[N][];
        for (int i = 0; i < N; i++) {
            grid[i]=scanner.next().toCharArray();
        }
        for (int iter = 0; iter < N / 2; iter++) {
            int k = (iter+1)%4;
            int base = iter;
            int NN = N-iter*2;
            char[][] tmp = new char[4][N-iter*2];
            for (int i = 0; i < tmp[0].length; i++) {
                tmp[k][i]=grid[iter][iter+i];
            }
            for (int i = 0; i < tmp[0].length; i++) {
                tmp[(k+1)%4][i]=grid[iter+i][N-iter-1];
            }
            for (int i = 0; i < tmp[0].length; i++) {
                tmp[(k+2)%4][i]=grid[N-iter-1][N-iter-1-i];
            }
            for (int i = 0; i < tmp[0].length; i++) {
                tmp[(k+3)%4][i]=grid[N-iter-1-i][iter];
            }
            for (int i = 0; i < NN; i++) {
                grid[base][base+i]=tmp[0][i];
            }
            for (int i = 0; i < NN; i++) {
                grid[iter+i][N-iter-1]=tmp[1][i];
            }
            for (int i = 0; i < NN; i++) {
                grid[N-iter-1][N-iter-1-i]=tmp[2][i];
            }
            for (int i = 0; i < NN; i++) {
                grid[N-iter-1-i][iter]=tmp[3][i];
            }
            /*
            System.out.println(tmp[0]);
            System.out.println(tmp[1]);
            System.out.println(tmp[2]);
            System.out.println(tmp[3]);
            System.out.println();

            for (int i = 0; i < grid.length; i++) {
                System.out.println(grid[i]);
            }
            System.out.println();

             */

        }

        for (int i = 0; i < grid.length; i++) {
            System.out.println(grid[i]);
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
