import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DP_F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static long lINF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        char[] t = scanner.next().toCharArray();
        long[][] dp = new long[s.length+1][t.length+1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                if (s[i-1]==t[j-1]) {
                    dp[i][j]=dp[i-1][j-1]+1;
                } else {
                    dp[i][j]=Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        // 復元
        StringBuilder sb = new StringBuilder();
        int ii = s.length, jj = t.length;
        while (ii > 0 && jj > 0)
        {
            // (ii-1, jj) -> (ii, jj) と更新されていた場合
            if (dp[ii][jj] == dp[ii-1][jj]) {
                --ii; // DP の遷移を遡る
            }
            // (ii, jj-1) -> (ii, jj) と更新されていた場合
            else if (dp[ii][jj] == dp[ii][jj-1]) {
                --jj; // DP の遷移を遡る
            }
            // (ii-1, jj-1) -> (ii, jj) と更新されていた場合
            else {
                sb.append(s[ii-1]);
                --ii; --jj; // DP の遷移を遡る
            }
        }
        /*
        int slen = s.length;
        int tlen = t.length;
        long len=dp[slen][tlen];
        int ii=slen-1;
        int jj=tlen-1;
        StringBuilder sb = new StringBuilder();
        while(len>0){
            if(s[ii]==t[jj]){
                sb.append(s[ii]);
                ii--;
                jj--;
                len--;
            }else if(dp[ii][jj]==dp[ii-1][jj]){
                ii--;
            }else{
                jj--;
            }
            if (ii < 0 || jj < 0) {
                break;
            }
        }

         */
        for (int i = 0; i <dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));

        }
        System.out.println(dp[s.length][t.length]);
        System.out.println(sb.reverse());

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
