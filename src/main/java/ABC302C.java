import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC302C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static char[][] s;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        int M = scanner.nextInt();
        s = new char[N][];
        for (int i = 0; i < N; i++) {
            s[i] = scanner.next().toCharArray();
        }
        perm = new int[N];
        used = new boolean[N];
        perm(0);
        System.out.println("No");
    }

    static int[] perm;
    static boolean[] used;
    static int N;
    static void perm(int depth) {
        if (depth == N) {
            int[] idxArr = new int[N];
            for (int i = 0; i < N; i++) {
                idxArr[i] = perm[i];
            }
            for (int i = 0; i < idxArr.length-1; i++) {
                int i1 = idxArr[i];
                int i2 = idxArr[i+1];
                int diff = 0;
                for (int j = 0; j < s[i1].length; j++) {
                    if (s[i1][j]!=s[i2][j]) {
                        diff++;
                    }
                }
                if (diff!=1) {
                    return;
                }
            }
            System.out.println("Yes");
            System.exit(0);
        }
        for (int i = 0; i < N; i++) {
            if (used[i]) {
                continue;
            }
            used[i]=true;
            perm[depth]=i;
            perm(depth+1);
            used[i]=false;
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
