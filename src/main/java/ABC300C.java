import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC300C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        char[][] s = new char[H+2][W+2];
        for (int i = 0; i < H; i++) {
            char[] ss = scanner.next().toCharArray();
            for (int j = 0; j < ss.length; j++) {
                s[i+1][j+1]=ss[j];
            }
        }
        int n = Math.min(H, W);
        int[] dirY = {1, 1, -1, -1};
        int[] dirX = {-1, 1, -1, 1};
        int[] cnt = new int[n];
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= H; j++) {
                for (int k = 1; k <=  W ; k++) {
                    if (s[j][k]!='#') {
                        continue;
                    }
                    boolean allSharp = true;
                    for (int l = 0; l < 4; l++) {
                        int yy = j;
                        int xx = k;
                        for (int m = 0; m < i; m++) {
                            yy += dirY[l];
                            xx += dirX[l];
                            if (s[yy][xx]!='#') {
                                allSharp = false;
                            }
                            if (s[yy][xx]==0) {
                                allSharp = false;
                                break;
                            }
                        }
                    }
                    if (allSharp) {
                        cnt[i-1]++;
                        s[j][k]='.';
                        for (int l = 0; l < 4; l++) {
                            int yy = j;
                            int xx = k;
                            for (int m = 0; m < i; m++) {
                                yy += dirY[l];
                                xx += dirX[l];
                                s[yy][xx] = '.';
                            }
                        }
                    }
                }
            }
        }
        for (int i : cnt) {
            System.out.print(i+" ");
        }
        System.out.println();
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
