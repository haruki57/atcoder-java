import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class JOI2013YoD {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int D = scanner.nextInt();
        int N = scanner.nextInt();
        int[] temp = new int[D];
        Arrays.setAll(temp, i -> scanner.nextInt());
        int[][] cloth = new int[N][3];
        for (int i = 0; i < N; i++) {
            cloth[i][0] = scanner.nextInt();
            cloth[i][1] = scanner.nextInt();
            cloth[i][2] = scanner.nextInt();
        }
        long[][] dp = new long[D+1][N];
        long ans = 0;

        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        for (int i = 0; i < dp[1].length; i++) {
            if (!(cloth[i][0] <= temp[0] && temp[0] <= cloth[i][1])) {
                continue;
            }
            dp[1][i] = 0;
        }

        for (int i = 2; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (!(cloth[j][0] <= temp[i-1] && temp[i-1] <= cloth[j][1])) {
                    continue;
                }
                for (int k = 0; k < dp[i].length; k++) {
                    if (dp[i-1][k]==-1)continue;
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][k] + Math.abs(cloth[j][2]-cloth[k][2]));
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
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
