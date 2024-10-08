import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC364E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long X = scanner.nextLong();
        long Y = scanner.nextLong();
        int MAX_X=10000;
        int MAX_Y=10000;
        int[][] xy = new int[N][2];
        for (int i = 0; i < N; i++) {
            xy[i][0]= scanner.nextInt();
            xy[i][1]= scanner.nextInt();
        }
        long[][][] dp = new long[N+1][10009][N+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j],INF);
            }
        }
        dp[0][0][0]=0;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k <= i; k++) {
                    dp[i][j][k]=dp[i-1][j][k];
                    int jj = j - xy[i-1][0];
                    if (jj >= 0 && k-1>=0) {
                        dp[i][j][k]=Math.min(dp[i][j][k], dp[i-1][jj][k-1]+xy[i-1][1]);
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k <= i; k++) {
                    if(j <= X && dp[i][j][k]<=Y) {
                        ans = Math.max(ans, k);
                    }
                }
            }
        }
        System.out.println(Math.min(ans+1, N));
    }


    // Atcoder上ではREとなる
    private static void mle(int N, int X, int Y, int[][] xy) {
        long[][][] dp = new long[N][X+1][Y+1];
        for (int i = xy[0][0]; i < dp[0].length; i++) {
            for (int j = xy[0][1]; j < dp[0][i].length; j++) {
                dp[0][i][j]=1;
            }
        }
        long ans = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    dp[i][j][k]=dp[i-1][j][k];
                    int jj = j- xy[i][0];
                    int kk = k- xy[i][1];
                    if(jj>=0&&kk>=0) {
                        dp[i][j][k]=Math.max(dp[i][j][k], dp[i-1][jj][kk]+1);
                    }
                    ans=Math.max(ans, dp[i][j][k]);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int k = 0; k < dp[i][j].length; k++) {
                    //System.out.print(dp[i][j][k]+" ");
                }
                //System.out.println();
            }
            //System.out.println();
        }

        System.out.println(Math.min(ans+1, N));
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
