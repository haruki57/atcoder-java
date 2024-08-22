package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _086 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int Q = scanner.nextInt();
        long[][] condition = new long[Q][4];
        for (int i = 0; i < Q; i++) {
            condition[i][0]= scanner.nextInt()-1;
            condition[i][1]= scanner.nextInt()-1;
            condition[i][2]= scanner.nextInt()-1;
            condition[i][3]= scanner.nextLong();
        }

        long ans = 1;
        for (int bitShift = 0; bitShift < 60; bitShift++) {
            int ok = 0;
            for (int bit = 0; bit < 1 << N; bit++) {
                if(ok(bitShift, bit, condition)) {
                    ok++;
                }
            }
            //System.out.println(ok);
            ans *= ok;
            ans %= MOD;
        }
        System.out.println(ans);
    }

    private static boolean ok(long bitShift, long bit, long[][] condition) {
        for (int i = 0; i < condition.length; i++) {
            long w = (condition[i][3] >> bitShift) & 1;
            if (w == 0) {
                if ((bit & (1L<<condition[i][0])) > 0 ||(bit & (1L<<condition[i][1])) > 0 ||(bit & (1L<<condition[i][2])) > 0) {
                    return false;
                }
            } else {
                if ((bit & (1L<<condition[i][0])) == 0 && (bit & (1L<<condition[i][1])) == 0 && (bit & (1L<<condition[i][2])) == 0) {
                    return false;
                }
            }
        }

        return true;
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
