package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class _085 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static List<Long> yakusuu(long N) {
        List<Long> ret = new ArrayList<>();
        for (long i = 1; i*i <= N ; i++) {
            if (N%i==0) {
                ret.add(i);
                if (N/i != i) {
                    ret.add(N/i);
                }
            }
        }
        return ret;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextLong();
        List<Long> yakusuu = yakusuu(N);
        Collections.sort(yakusuu);

        Set<String> set = new HashSet<>();
        long ans = 0;
        //System.out.println((yakusuu));
        for (int i = 0; i < yakusuu.size(); i++) {
            for (int j = i; j < yakusuu.size(); j++) {
                long a = yakusuu.get(i);
                long b = yakusuu.get(j);
                long c = N/a/b;
                // System.out.println(a + " " + b);
                if (N / a < b) {
                    continue;
                }
                if (N%(a*b)!=0) {
                    continue;
                }
                if (c < a || c < b){
                    continue;
                }
                ans++;
            }
        }
        System.out.println( ans);
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
