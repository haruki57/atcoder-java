import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DP_J_2 {
    static int MOD = (int)Math.pow(10, 9)+ 7;
    static int INF = Integer.MAX_VALUE/2;
    static int N;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        int one = 0;
        int two = 0;
        int three = 0;
        var dp = new double[301][301][301];
        for (int i = 0; i < N; i++) {
            int a =scanner.nextInt();
            if (a==1) {
                one++;
            } else if (a==2) {
                two++;
            } else {
                three++;
            }
        }
        //dp[one][two][three] = 1;

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        System.out.println(rec(dp, one, two, three));

        /*
        for (int i = one; i >= 0; i--) {
            for (int j = two; j >= 0; j--) {
                for (int k = three; k >= 0; k--) {
                    if (i-1>=0)
                        dp[i-1][j][k]+=dp[i][j][k]/3;
                    if(j-1>=0)
                        dp[i][j-1][k]+=dp[i][j][k]/3;
                    if(k-1>=0)
                        dp[i][j][k-1]+=dp[i][j][k]/3;
                }
            }
        }
         */

    }

    private static double rec(double[][][] dp, int i, int j,int k) {
        if(i+j+k==0) {
            return 0;
        }
        if(dp[i][j][k] >= 0) {
            return dp[i][j][k];
        }
        double ret = 0;
        if(i-1>=0)
            ret += rec(dp, i-1, j, k) * i;
        if(j-1>=0)
            ret += rec(dp, i+1, j-1, k) * j;
        if(k-1>=0)
            ret += rec(dp, i, j+1, k-1) * k;
        ret += N;
        ret *= 1.0 / (i+j+k);
        return dp[i][j][k]=ret;
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
