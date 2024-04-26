import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ABC021D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static long ans = 0;
    static Map<String, Long> memo = new HashMap<>();
    static long rec(int n, int k, int depth) {
        if (k==0){
            return 1;
        }
        long ret = 0;
        for (int i = depth; i <= n; i++) {
            ret = (ret + rec(n, k-1, i)) % MOD;
        }
        return ret;
    }
    private static long divideWithMod(long a, long b, int mod) {
        return (a*powWithMod(b, mod-2, mod)%mod);
    }

    private static long powWithMod(long a, long b, int mod) {
        String binaryString = Long.toBinaryString(b);
        int len = binaryString.length();
        long ret = 1;
        for (int i = 0; i < len; i++) {
            if (binaryString.charAt(len-i-1) == '1') {
                ret = (ret * a) % mod;
            }
            a = (a*a) % mod;
        }
        return ret;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        // (N-1+k)C(n-1)
        int left = N-1+K;
        int right = N-1;
        long a = 1, b = 1;
        for (int i = 1; i <= left; i++) {
            a = (a*i)%MOD;
        }
        for (int i = 1; i <= right; i++) {
            b = (b*i)%MOD;
        }
        for (int i = 1; i <= left-right; i++) {
            b = (b*i)%MOD;
        }
        System.out.println(divideWithMod(a, b, MOD));
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
