import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC344D_2 {
    static int MOD = 1000000007;
    static long INF = Long.MAX_VALUE/2;

    private static String t;
    static List<String>[] list;
    private static int N;
    static void run (final FastScanner scanner, final PrintWriter out) {
        t = scanner.next();
        N = scanner.nextInt();
        list = new List[N];
        for (int i = 0; i < N; i++) {
            list[i]=new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();
            for (int j = 0; j < a; j++) {
                list[i].add(scanner.next());
            }
        }
        long[][] dp = new long[N+1][t.length()+1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0]=0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j]=Math.min(dp[i][j], dp[i-1][j]);
                for (String s : list[i - 1]) {
                    if (j+s.length() > t.length()) {
                        //continue;
                    }
                    if (t.startsWith(s, j)) {
                        dp[i][j+s.length()] = Math.min(dp[i][j+s.length()], dp[i-1][j]+1);
                    }
                }
            }
        }

        long ans = INF;
        for (int i = 0; i < dp.length; i++) {
            ans = Math.min(ans, dp[i][dp[i].length-1]);
        }
        if(ans==INF) {
            ans = -1;
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
