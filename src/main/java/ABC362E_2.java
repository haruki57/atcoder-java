import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC362E_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;


    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long[][][] dp = new long[N][N][N + 1];
        for (int i = N-1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                dp[i][j][2] += 1;
                for (int l = 2; l < N; l++) {
                    for (int k = j + 1; k < N; k++) {
                        if (a[j] - a[i] == a[k] - a[j]) {
                            dp[i][j][l+1] += dp[j][k][l];
                            dp[i][j][l+1] %= MOD;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                //System.out.println(Arrays.toString(dp[i][j]));
            }
            //System.out.println();
        }


        long[] ans = new long[N + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                for (int l = 2; l <= N; l++) {
                    ans[l]+=dp[i][j][l];
                    ans[l]%=MOD;
                }
            }
        }
        ans[1]=N;
        for (int i = 1; i <= N; i++) {
            out.print(ans[i]+" ");
        }
        out.println();
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
