import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC259C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        char[] t = scanner.next().toCharArray();
        List<CharInt> list0 = asshuku(s);
        List<CharInt> list1 = asshuku(t);
        if(list0.size() != list1.size()) {
            System.out.println("No");
            return;
        }
        //System.out.println(list0);
        //System.out.println(list1);
        for (int i = 0; i < list0.size(); i++) {
            CharInt c0 = list0.get(i);
            CharInt c1 = list1.get(i);
            if(c0.c != c1.c) {
                System.out.println("No");
                return;
            }
            if(c0.len == 1 && c1.len > 1) {
                System.out.println("No");
                return;
            }
            if(c0.len > c1.len) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }

    static List<CharInt> asshuku(char[] s) {
        List<CharInt> ret = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            int j = i;
            while(j < s.length && s[i]==s[j]) {
                j++;
            }
            ret.add(new CharInt(s[i], j-i));
            i=j-1;
        }
        return ret;
    }

    static class CharInt {
        char c;
        int len;

        public CharInt(char c, int len) {
            this.c = c;
            this.len = len;
        }

        @Override
        public String toString() {
            return "CharInt{" +
                    "c=" + c +
                    ", len=" + len +
                    '}';
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
