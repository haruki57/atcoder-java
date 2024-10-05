import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class APC001C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static String V = "Vacant";
    static String M = "Male";
    static String F = "Female";
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        System.out.println(0);
        String ret = scanner.next();
        if(ret.equals(V)) {
            return;
        }
        String first = ret;
        System.out.println(N-1);
        ret = scanner.next();
        if(ret.equals(V)) {
            return;
        }
        int left = 0, right = N;
        for (int i = 0; i < 18; i++) {
            int mid = (left+right)/2;
            System.out.println(mid);
            ret = scanner.next();
            boolean isFirstEven = left % 2 == 0;
            if(ret.equals(V)) {
                return;
            }
            if(mid%2==0) {
                if(isFirstEven) {
                    if(ret.equals(first)) {
                        first = ret;
                        left = mid;
                    } else {
                        right = mid;
                    }
                } else {
                    if(!ret.equals(first)) {
                        first = ret;
                        left = mid;
                    } else {
                        right = mid;
                    }

                }
            } else {
                if(isFirstEven) {
                    if(!ret.equals(first)) {
                        first = ret;
                        left = mid;
                    } else {
                        right = mid;
                    }
                } else {
                    if(ret.equals(first)) {
                        first = ret;
                        left = mid;
                    } else {
                        right = mid;
                    }

                }

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
