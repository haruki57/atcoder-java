import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC327C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static private boolean unique(int a1,
                                  int a2,
                                  int a3,
                                  int a4,
                                  int a5,
                                  int a6,
                                  int a7,
                                  int a8,
                                  int a9
    ) {
        int bit = 0;
        bit = bit | (1<<a1);
        bit = bit | (1<<a2);
        bit = bit | (1<<a3);
        bit = bit | (1<<a4);
        bit = bit | (1<<a5);
        bit = bit | (1<<a6);
        bit = bit | (1<<a7);
        bit = bit | (1<<a8);
        bit = bit | (1<<a9);
        return bit == 0b1111111110;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int[][] a = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                a[i][j]= scanner.nextInt();
            }
        }
        boolean ok = true;
        for (int i = 0; i < 9; i++) {
            if (!unique(a[i][0], a[i][1], a[i][2], a[i][3], a[i][4], a[i][5], a[i][6], a[i][7], a[i][8])) {
                ok = false;
            }
            if (!unique(a[0][i], a[1][i], a[2][i], a[3][i], a[4][i], a[5][i], a[6][i], a[7][i], a[8][i])) {
                ok = false;
            }
        }

        for (int i = 0; i < 9; i+=3) {
            for (int j = 0; j < 9; j+=3) {
                if (!unique(a[i][j], a[i+1][j], a[i+2][j], a[i][j+1], a[i+1][j+1], a[i+2][j+1], a[i][j+2], a[i+1][j+2], a[i+2][j+2])) {
                    ok = false;
                }
            }
        }
        if (ok) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
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
