import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC145D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        int xCnt = -1, yCnt = 1;
        for (int i = 0; i <= X; i++) {
            int x = i;
            int y = i * 2;
            int ok = -1, ng = INF;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                int xx = x + mid * 2;
                int yy = y + mid * 1;
                if (xx <= X && yy <= Y) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            x += ok * 2;
            y += ok;
            if (x == X && y == Y) {
                xCnt = i;
                yCnt = ok;
                break;
            }
        }
        if (xCnt == -1) {
            System.out.println(0);
            return;
        }
        long a = 1, b=1;
        for (int i = 1; i <= xCnt + yCnt; i++) {
            a = (a*i) % MOD;
        }
        for (int i = 1; i <= xCnt; i++) {
            b = (b*i) % MOD;
        }
        for (int i = 1; i <= yCnt; i++) {
            b = (b*i) % MOD;
        }
        System.out.println(divideWithMod(a, b, MOD));
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
