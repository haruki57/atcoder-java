import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC301C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        char[] t = scanner.next().toCharArray();
        int cntAtS = 0;
        int cntAtT = 0;
        int[] cntS = new int[26];
        int[] cntT = new int[26];
        for (char c : s) {
            if (c=='@') {
                cntAtS++;
            } else {
                cntS[c-'a']++;
            }

        }
        for (char c : t) {
            if (c=='@') {
                cntAtT++;
            } else {
                cntT[c-'a']++;
            }
        }
        for (int i = 0; i < cntS.length; i++) {
            char c = (char)('a' + i);
            int diff = Math.abs(cntS[i] - cntT[i]);
            if (c=='a'||
                    c=='t'||
                    c=='c'||
                    c=='o'||
                    c=='d'||
                    c=='e'||
                    c=='r'
            ) {
                if (cntS[i] < cntT[i]) {
                    cntAtS -= diff;
                }
                if (cntS[i] > cntT[i]) {
                    cntAtT -= diff;
                }
            } else {
                if (diff>0) {
                    System.out.println("No");
                    return;
                }
            }

            if (cntAtS < 0) {
                out.println("No");
                return;
            }
            if (cntAtT < 0) {
                out.println("No");
                return;
            }
        }
        out.println("Yes");
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
