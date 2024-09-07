import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC115D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static long[] burger = new long[51];
    static long[] pati = new long[51];
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long X = scanner.nextLong();
        for (int i = 0; i <= 50; i++) {
            burger[i]=burger(i);
        }
        pati(50);
        System.out.println(rec(N, X));
    }

    static long rec(int n, long x) {
        if(n==0) {
            return 1;
        }
        long minus1All = burger[n-1];
        long minus1Pati = pati[n-1];
        if(x==1) {
            return 0;
        }
        if(x <= minus1All + 1) {
            return rec(n-1, x-1);
        }
        if (x == minus1All + 2) {
            return minus1Pati + 1;
        }
        if(x <= minus1All * 2 + 2) {
            return minus1Pati + 1 + rec(n-1, x - 2 - minus1All);
        }
        return minus1Pati * 2 + 1;
    }

    static long burger(int n) {
        if(n==0) {
            return 1;
        }
        return burger[n] = 3 + burger(n-1) * 2;
    }
    static long pati(int n) {
        if(n==0) {
            return pati[n]=1;
        }
        return pati[n] = 1 + pati(n-1) * 2;
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
