import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC322D_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[][][] a = new char[3][4][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                a[i][j]= scanner.next().toCharArray();
            }
        }
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                for (int k = 0; k < a[i][j].length; k++) {
                    if(a[i][j][k]=='#') {
                        cnt++;
                    }
                }
            }
        }
        if (cnt!=16) {
            System.out.println("No");
            return;
        }
        for (int s0 = 0; s0 < 4; s0++) {
            for (int s1 = 0; s1 < 4; s1++) {
                for (int s2 = 0; s2 < 4; s2++) {
                    int tileLen = 4+3;
                    for (int y0 = 0; y0 < tileLen; y0++) {
                        for (int x0 = 0; x0 < tileLen; x0++) {
                            if (!canPut(tileLen, a[0], y0, x0)) {
                                continue;
                            }
                            for (int y1 = 0; y1 < tileLen; y1++) {
                                for (int x1 = 0; x1 < tileLen; x1++) {
                                    if (!canPut(tileLen, a[1], y1, x1)) {
                                        continue;
                                    }
                                    for (int y2 = 0; y2 < tileLen; y2++) {
                                        for (int x2 = 0; x2 < tileLen; x2++) {
                                            if (!canPut(tileLen, a[2], y2, x2)) {
                                                continue;
                                            }
                                            char[][] tile = new char[tileLen][tileLen];
                                            put(tile, a[0], y0, x0);
                                            put(tile, a[1], y1, x1);
                                            put(tile, a[2], y2, x2);
                                            if (ok(tile)) {
                                                System.out.println("Yes");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }



                    a[2]=spin(a[2]);
                }
                a[1]=spin(a[1]);
            }
            a[0]=spin(a[0]);
        }
        System.out.println("No");

        /*


         */
    }

    private static boolean canPut(int tileSize, char[][] a, int y, int x) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int ii = i+y;
                int jj = j+x;
                if (a[i][j]=='#' && ii>=tileSize || jj >= tileSize) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean ok(char[][] tile) {
        for (int y = 0; y < tile.length; y++) {
            for (int x = 0; x < tile.length; x++) {
                int cnt = 0;
                l: for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        int ii = i+y;
                        int jj = j+x;
                        if (ii>=tile.length || jj >= tile.length) {
                            continue l;
                        }
                        if (tile[ii][jj]=='#') {
                            cnt++;
                        }
                    }
                }
                if (cnt==16) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean put(char[][] tile, char[][] a, int y, int x) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int ii = i+y;
                int jj = j+x;
                if (ii >= tile.length || jj >= tile.length) {
                    continue;
                }
                if (a[i][j]=='#') {
                    tile[ii][jj]=a[i][j];
                }
            }
        }
        return true;
    }

    static char[][] spin(char[][] a) {
        char[][] ret = new char[a.length][a.length];
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int ii = j;
                int jj = N-i-1;
                ret[ii][jj]=a[i][j];
            }
        }
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
