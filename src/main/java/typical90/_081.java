package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _081 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        //int[][] ab = new int[N][2];
        int MAX = 5001;
        int[][] map = new int[MAX][MAX];
        for (int i = 0; i < N; i++) {
            //ab[i][0]= scanner.nextInt();
            //ab[i][1]= scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            map[x][y]++;
        }
        int[][] sum = new int[MAX+1][MAX+1];
        for (int i = 1; i < sum.length; i++) {
            for (int j = 1; j < sum.length; j++) {
                sum[i][j]=sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1]+map[i-1][j-1];
            }
        }
        for (int i = 0; i < map.length; i++) {
            //System.out.println(Arrays.toString(map[i]));
        }
        //System.out.println();
        for (int i = 0; i < sum.length; i++) {
            //System.out.println(Arrays.toString(sum[i]));
        }
        int ans = 1;
        for (int i = 1; i < sum.length; i++) {
            if(i+K>=sum.length) {
                break;
            }

            for (int j = 1; j < sum.length; j++) {
                if(j+K>=sum.length) {
                    break;
                }
                ans = Math.max(ans, sum[i+K][j+K] - sum[i-1][j+K] - sum[i+K][j-1] + sum[i-1][j-1]);
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
