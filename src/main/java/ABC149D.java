import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC149D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int R = scanner.nextInt();
        int S = scanner.nextInt();
        int P = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        long ans = 0;
        for (int i = 0; i < K; i++) {
            StringBuilder sb =new StringBuilder();
            for (int j = i; j <N; j+=K) {
                sb.append(s[j]);
            }
            for (int j = 0; j < sb.length(); j++) {
                char cur = sb.charAt(j);
                int k = j;
                for (; k < sb.length(); k++) {
                    if (cur != sb.charAt(k)) {
                        break;
                    }
                }
                long win = cur=='r' ? P : cur=='s' ? R : S;
                ans += win*((k-j+1)/2);
                j=k-1;
            }
        }
        System.out.println(ans);

        /*
        long[][] dp = new long[N+1][3];
        for (int i = 0; i < K; i++) {
            long max = Math.max(dp[i][0], Math.max(dp[i][1], dp[i][2]));
            long win = s[i]=='r' ? P : s[i]=='s' ? R : S;
            dp[i+1][0] = max+win;
            dp[i+1][1] = max+win;
            dp[i+1][2] = max+win;
        }
        for (int i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println();
        for (int i = K; i < N; i++) {
            long max = Math.max(dp[i][0], Math.max(dp[i][1], dp[i][2]));
            dp[i+1][0] = Math.max(dp[i-K+1][1],dp[i-K+1][2])+(s[i]=='s'?R:0);
            dp[i+1][1] = Math.max(dp[i-K+1][0],dp[i-K+1][2])+(s[i]=='p'?S:0);
            dp[i+1][2] = Math.max(dp[i-K+1][0],dp[i-K+1][1])+(s[i]=='r'?P:0);
        }
        for (int i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(Math.max(dp[N][0], Math.max(dp[N][1],dp[N][2])));



        for (int i = 0; i < K; i++) {
            long prevMax = Math.max(dp[i][0], Math.max(dp[i][1], dp[i][2]));
            dp[i+1][0] = prevMax+(s[i]=='s'?R:0);
            dp[i+1][1] = prevMax+(s[i]=='p'?S:0);
            dp[i+1][2] = prevMax+(s[i]=='r'?P:0);
        }
        for (int i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println();
        for (int i = K; i < N; i++) {
            long win = s[i]=='r' ? P : s[i]=='s' ? R : S;
            long prevMax = Math.max(dp[i][0], Math.max(dp[i][1], dp[i][2]));
            dp[i+1][0] = Math.max(dp[i-K+1][1],dp[i-K+1][2])+win;
            dp[i+1][1] = Math.max(dp[i-K+1][0],dp[i-K+1][2])+win;
            dp[i+1][2] = Math.max(dp[i-K+1][0],dp[i-K+1][1])+win;
        }
        for (int i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(Math.max(dp[N][0], Math.max(dp[N][1],dp[N][2])));
         */
    }
    static long max(long a,long b,long c) {
        return Math.max(a, Math.max(b,c));
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
