package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _056 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int S = scanner.nextInt();
        int[][] ab = new int[N][2];
        for (int i = 0; i < N; i++) {
            ab[i][0]= scanner.nextInt();
            ab[i][1]= scanner.nextInt();
        }
        boolean[][] dp = new boolean[N+1][S+1];
        dp[0][0]=true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < S+1; j++) {
                if(!dp[i][j]){
                    continue;
                }
                int a = ab[i][0]+j;
                int b = ab[i][1]+j;
                if(a<dp[i].length) {
                    dp[i+1][a]=true;
                }
                if(b<dp[i].length) {
                    dp[i+1][b]=true;
                }
            }
        }
        if(!dp[N][S]) {
            System.out.println("Impossible");
            return;
        }
        var sb = new StringBuilder();
        for (int i = 0; i < dp.length; i++) {
            //System.out.println(Arrays.toString(dp[i]));
        }
        int j = S;
        for (int i = N; i > 0; i--) {
            int ii = i-1;
            int ja = j-ab[ii][0];
            int jb = j-ab[ii][1];
            if(ja >= 0 && dp[i-1][ja]) {
                sb.append("A");
                j = ja;
            } else {
                sb.append("B");
                j = jb;
            }
        }
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