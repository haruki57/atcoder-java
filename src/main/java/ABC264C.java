import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ABC264C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H1 = scanner.nextInt();
        int W1 = scanner.nextInt();
        int[][] a = new int[H1][W1];
        for (int i = 0; i < H1; i++) {
            for (int j = 0; j < W1; j++) {
                a[i][j]=scanner.nextInt();
            }
        }
        int H2 = scanner.nextInt();
        int W2 = scanner.nextInt();
        int[][] b = new int[H2][W2];
        for (int i = 0; i < H2; i++) {
            for (int j = 0; j < W2; j++) {
                b[i][j]=scanner.nextInt();
            }
        }
        for (int bit1 = 0; bit1 < 1<<H1; bit1++) {
            for (int bit2 = 0; bit2 < 1 << W1; bit2++) {
                int newH = H1 - Integer.bitCount(bit1);
                int newW = W1 - Integer.bitCount(bit2);
                if (newH != H2 || newW != W2) {
                    continue;
                }
                int[][] newA = make(a, bit1, bit2);
                if (Objects.deepEquals(newA, b)) {
                    System.out.println("Yes");
                    return;
                }
            }
        }
        System.out.println("No");
    }

    private static int[][] make(int[][] a, int bit1, int bit2) {
        int ii=0, jj=0;
        int[][] ret = new int[a.length-Integer.bitCount(bit1)][a[0].length-Integer.bitCount(bit2)];
        for (int i = 0; i < a.length; i++) {
            if (((1<<i)&bit1) != 0) {
                continue;
            }
            jj=0;
            for (int j = 0; j < a[0].length; j++) {
                if (((1<<j)&bit2) != 0) {
                    continue;
                }
                ret[ii][jj]=a[i][j];
                jj++;
            }
            ii++;
        }
        /*
        System.out.println(Integer.toBinaryString(bit1)+" "+Integer.toBinaryString(bit2));
        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret[0].length; j++) {
                System.out.print(ret[i][j]+" ");
            }
            System.out.println();
        }
         */
        return ret;
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
