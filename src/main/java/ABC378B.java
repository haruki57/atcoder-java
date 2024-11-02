import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class ABC378B {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        /*
        while (true) {
            Random rand = new Random();
            int q = rand.nextInt(4)+4;
            int r = rand.nextInt(q);
            int d = rand.nextInt(100);
            System.out.println(q+" "+r+" "+d);
            if(ans(q,r,d)!=ansTLE(q,r,d)) {
                System.out.println(q+" "+r+" "+d);
                System.out.println(ans(q,r,d)+" "+ansTLE(q,r,d));
                break;
            }
        }


         */
        int N = scanner.nextInt();
        long[] q = new long[N];
        long[] r = new long[N];
        for (int i = 0; i < N; i++) {
            q[i]= scanner.nextInt();
            r[i]= scanner.nextInt();
        }
        int Q = scanner.nextInt();
        while(Q-->0) {
            int i = scanner.nextInt()-1;
            long d = scanner.nextInt();
            out.println(ans(q[i], r[i], d));
        }
    }

    static long ans(long q, long r, long d) {
        long base = ((d)/q*q);
        if ((d)%q <= r) {
            return (base + r);
        } else {
            return (base + q + r);
        }

    }



    static long ansTLE(long q, long r, long d) {
        for (long i = d;; i++) {
            if(i%q==r) {
                return i;
            }
        }
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
