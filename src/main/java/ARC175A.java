import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ARC175A {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt()-1);
        char[] s = scanner.next().toCharArray();
        boolean[] spoon = new boolean[N];
        Arrays.fill(spoon, true);
        long ans = 0;
        long sum = 1;
        // take all left spoon
        for (int i = 0; i < N; i++) {
            int person = a[i];
            char c = s[person];
            int lsi = a[i]; // left spoon idx
            int rsi = (a[i]+1)%N; // right spoon idx
            boolean lSpoon = spoon[lsi];
            boolean rSpoon = spoon[rsi];
            if(lSpoon && rSpoon) {
                if(c=='R') {
                    sum = 0;
                    break;
                }
            } else if(rSpoon) {
                sum = 0;
                break;
            } else if(lSpoon) {
                if(c=='?') {
                    sum *= 2;
                }
            } else {
                sum = 0;
                break;
            }
            sum %= MOD;
            spoon[lsi] = false;
        }
        ans += sum;

        Arrays.fill(spoon, true);
        sum = 1;

        // take all right spoon
        for (int i = 0; i < N; i++) {
            int person = a[i];
            char c = s[person];
            int lsi = a[i]; // left spoon idx
            int rsi = (a[i]+1)%N; // right spoon idx
            boolean lSpoon = spoon[lsi];
            boolean rSpoon = spoon[rsi];
            if(lSpoon && rSpoon) {
                if(c=='L') {
                    sum = 0;
                    break;
                }
            } else if(lSpoon) {
                sum = 0;
                break;
            } else if(rSpoon) {
                if(c=='?') {
                    sum *= 2;
                }
            } else {
                sum = 0;
                break;
            }
            sum %= MOD;
            spoon[rsi] = false;
        }
        ans += sum;
        ans %= MOD;
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
