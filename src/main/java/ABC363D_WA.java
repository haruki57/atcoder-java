import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC363D_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static long N;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextLong();
        if (N<=1000) {
            long ansTLE = 0;
            for (int i = 0; i < 10000000; i++) {
                if(isKaibun(i)) {
                    ansTLE++;
                    if (ansTLE == N) {
                        System.out.println(i);
                        return;
                    }
                }
            }
        }
        long[] cnt = new long[70];
        cnt[1]=10;
        cnt[2]=9;
        cnt[3]=10*9;

        for (int i = 4; i < cnt.length; i++) {
            int hoge = (i+3)/2-2;
            cnt[i]=9 * (long)Math.pow(10, hoge);
        }
        long sum = cnt[1]+cnt[2];
        System.out.println(rec(3, cnt, sum));

        System.out.println(Arrays.toString(cnt));
        long[] cntTLE = new long[100];

        long ansTLE = 0;
        for (int i = 0; i < 100000000; i++) {
            if(isKaibun(i)) {
                cntTLE[String.valueOf(i).length()]++;
                ansTLE++;
                if (ansTLE == N) {
                    System.out.println(i);
                    return;
                }
            }
        }
        //System.out.println(Arrays.toString(cntTLE));
    }

    private static String rec(int digit, long[] cnt, long cur) {
        for (int i = 0; i < 10; i++) {
            if (cur + cnt[digit] > N) {
                return rec(digit-1, cnt, cur);
            }
            if (cur + cnt[digit] == N) {
                return "Yes";
            }
        }

        return "";
    }

    static boolean isKaibun(long a) {
        String s = ""+a;
        for (int i = 0; i <= s.length()/2; i++) {
            if (s.charAt(i) != s.charAt(s.length()-i-1)) {
                return false;
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
