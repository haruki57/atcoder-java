import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class ABC234E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long x = scanner.nextLong();
        if (ok(x)) {
            System.out.println(x);
            return;
        }
        int digit = String.valueOf(x).length();
        if (digit >= 11) {
            for (int i = 1; i <= 9; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < digit; j++) {
                    sb.append(i);
                }
                long y = Long.parseLong(sb.toString());
                if (x <= y) {
                    System.out.println(y);
                    return;
                }
            }
        }
        if (digit >= 8) {
            TreeSet<Long> set = new TreeSet<>();
            long[] precalc = {1111111111l,
                    2222222222l,
                    3333333333l,
                    4444444444l,
                    5555555555l,
                    6666666666l,
                    7777777777l,
                    8888888888l,
                    9999999999l,
                    9876543210l,

                    111111111,
                    123456789,
                    222222222,
                    333333333,
                    444444444,
                    555555555,
                    666666666,
                    777777777,
                    876543210,
                    888888888,
                    987654321,
                    999999999,

                    11111111,
                    12345678,
                    22222222,
                    23456789,
                    33333333,
                    44444444,
                    55555555,
                    66666666,
                    76543210,
                    77777777,
                    87654321,
                    88888888,
                    98765432,
                    99999999};
            for (long l : precalc) {
                set.add(l);
            }
            System.out.println(set.higher(x));
            return;
        }
        for (long i = x; i < (long)Math.pow(10, digit); i++) {
            if (ok(i)) {
                System.out.println(i);
                return;
            }
        }
    }

    static boolean ok(long k) {
        if (k<=9) {
            return true;
        }
        char[] s = String.valueOf(k).toCharArray();
        int diff = s[0]-s[1];
        for (int i = 1; i < s.length-1; i++) {
            if(s[i]-s[i+1]!=diff){
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
