import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class ARC119B_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        char[] t = scanner.next().toCharArray();
        char[] cloneS = s.clone();
        char[] cloneT = t.clone();
        Arrays.sort(cloneS);
        Arrays.sort(cloneT);
        if (!Arrays.equals(cloneS, cloneT)) {
            System.out.println(-1);
            return;
        }
        TreeSet<Integer> zeros = new TreeSet<>();
        TreeSet<Integer> ones = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            if(s[i]=='0') {
                zeros.add(i);
            } else {
                ones.add(i);
            }
        }
        System.out.println(zeros);
        System.out.println(ones);
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            if(s[i]==t[i]) {
                continue;
            }
            System.out.println(i);
            cnt++;
            if(s[i]=='0') {
                Integer higher = ones.higher(i);
                ones.remove(higher);
                ones.add(i);
                zeros.remove(i);
                zeros.add(higher);
                var tmp = s[i];
                s[i] = s[higher];
                s[higher] = tmp;
            } else {
                Integer higher = zeros.higher(i);
                zeros.remove(higher);
                zeros.add(i);
                ones.remove(i);
                ones.add(higher);
                var tmp = s[i];
                s[i] = s[higher];
                s[higher] = tmp;
            }
            System.out.println(s);
            System.out.println(t);
            System.out.println();
        }
        System.out.println(cnt);
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
