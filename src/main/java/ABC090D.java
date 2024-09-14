import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC090D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        System.out.println(97/1);
        System.out.println(97/2);
        System.out.println(97/3);
        System.out.println(97/4);
        System.out.println(97/5);
        System.out.println(97/6);
        System.out.println(97/7);
        System.out.println(97/8);
        System.out.println(97/9);
        System.out.println(97/10);
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        if(K==0) {
            System.out.println((long)N*N);
            return;
        }
        long ans = 0;
        for (int a = K; a <= N; a++) {
            for (long b = 1; b * b <= a; b++) {
                long ng = a/b;
                long ok = a/(b+1)+1;
                //System.out.println(a+" "+b);
                //System.out.println(ok+" "+ng);
                while(Math.abs(ok-ng) > 1) {
                    long mid = (ok+ng)/2;
                    if(a%mid >= K) {
                        ok = mid;
                    } else {
                        ng = mid;
                    }
                }
                System.out.println(a+" "+b+" "+a/b+" "+ok);
                ans += (a/(b)) - ok;
                //System.out.println(ok+" "+ng);
                //System.out.println(a%ok+" "+a%ng);
                //System.out.println();
                //ans += (a/(b+1))-ng;
                //ans +=
            }
        }
        System.out.println(ans);
        ansTle(N, K);
    }

    static void ansTle(int N, int K) {
        int ans  = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.println(i+" "+j+" "+i/j+" "+i%j);
                if(i%j >= K) {
                    ans++;
                }
            }
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
