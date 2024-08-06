import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC365D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] map = new int[1000];
        map['R'] = 0;
        map['S'] = 1;
        map['P'] = 2;
        char[] s = scanner.next().toCharArray();
        int[][] dp = new int[N][3]; // R S P
        if(s[0]=='R') {
            dp[0][0] = 0;
            dp[0][1] = -INF;
            dp[0][2] = 1;
        } else if(s[0]=='S') {
            dp[0][0] = 1;
            dp[0][1] = 0;
            dp[0][2] = -INF;
        } else {
            dp[0][0] = -INF;
            dp[0][1] = 1;
            dp[0][2] = 0;
        }
        for (int i = 1; i < N; i++) {
            char c = s[i];
            int idx = map[c];
            int idxToWin = -1;
            int idxToLose = -1;
            if(c=='R') {
                idxToWin=2;
                idxToLose=1;
            } else if(c=='S') {
                idxToWin=0;
                idxToLose=2;
            } else {
                idxToWin=1;
                idxToLose=0;
            }
            dp[i][idx]=Math.max(dp[i-1][idxToLose], dp[i-1][idxToWin]);
            dp[i][idxToWin]=Math.max(dp[i-1][idx], dp[i-1][idxToLose])+1;
            dp[i][idxToLose]=-INF;
        }
        System.out.println(Math.max(dp[N-1][0], Math.max(dp[N-1][1],dp[N-1][2])));
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
