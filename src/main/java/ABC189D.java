import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC189D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        String[] s = new String[N];
        for (int i = 0; i < N; i++) {
            s[i]= scanner.next();
        }

        long[][] dp = new long[N+1][2];
        dp[0][0]=1;
        dp[0][1]=0;
        for (int i = 1; i < dp.length; i++) {
            String ss = s[i-1];
            if (ss.equals("AND")) {
                dp[i][0]=dp[i-1][0]+dp[i-1][1];
                dp[i][1]=0;
            } else {
                dp[i][0]=(long)Math.pow(2, i);
                dp[i][1]=dp[i-1][0]+dp[i-1][1];
            }
        }
        long sum = 0;
        for (int i = 0; i < dp.length; i++) {
//            System.out.println(Arrays.toString(dp[i]));
            sum += dp[i][0]+dp[i][1];
        }
        System.out.println(dp[N][0]+dp[N][1]);

        //System.out.println(tle(s));
    }

    static long tle(String[] s) {
        long ret = 0;
        for (int bit = 0; bit < 1<<(s.length + 1); bit++) {
            boolean cur = (bit&1) > 0;
            //System.out.println(Integer.toBinaryString(bit));
            for (int i = 0; i < s.length; i++) {
                //System.out.println((bit&(1<<(i+1) )));
                if (s[i].equals("AND")) {
                    cur = cur & (  (bit&(1<<(i+1) )) > 0);
                } else {
                    cur = cur | (  (bit&(1<<(i+1) )) > 0);
                }
            }
            //System.out.println();
            if (cur) ret++;
        }
        return ret;

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
