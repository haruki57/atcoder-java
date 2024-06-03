import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC129D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] map = new char[H+2][W+2];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], '#');
        }
        for (int i = 0; i < H; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 0; j < s.length; j++) {
                map[i+1][j+1]=s[j];
            }
        }
        int[][][] sum = new int[H+2][W+2][2];
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                int jj = j;
                while(j<=W&&map[i][j]=='.') {
                    j++;
                }
                for (int k = jj; k < j; k++) {
                    sum[i][k][0]=j-jj;
                }
            }
        }

        for (int i = 1; i <= W; i++) {
            for (int j = 1; j <= H; j++) {
                int jj = j;
                while(j<=H&&map[j][i]=='.') {
                    j++;
                }
                for (int k = jj; k < j; k++) {
                    sum[k][i][1]=j-jj;
                }
            }
        }
/*
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                System.out.print(sum[i][j][0]+" ");
            }
            System.out.println();
        }
        System.out.println();

        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                System.out.print(sum[i][j][1]+" ");
            }
            System.out.println();
        }

 */

        int ans = 0;
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                ans = Math.max(ans, sum[i][j][0]+sum[i][j][1]-1);
            }
        }
        System.out.println(ans);
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
