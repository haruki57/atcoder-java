import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC312B {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        char[][] tile = new char[N][];
        for (int i = 0; i < N; i++) {
            tile[i] = scanner.next().toCharArray();
        }
        for (int i = 0; i < N - 8; i++) {
            for (int j = 0; j < M - 8; j++) {
                if (ok(tile, i, j)) {
                    System.out.println((i+1)+ " " +(j+1));
                }
            }
        }
    }

    static boolean ok(char[][] tile, int y,int x) {
        int cnt1 = 0;
        int cnt2 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tile[y+i][x+j]=='#') {
                    cnt1++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tile[y+i+6][x+j+6]=='#') {
                    cnt1++;
                }
            }
        }
        cnt2 += tile[y+0][x+3]=='.'?1:0;
        cnt2 += tile[y+1][x+3]=='.'?1:0;
        cnt2 += tile[y+2][x+3]=='.'?1:0;
        cnt2 += tile[y+3][x+3]=='.'?1:0;
        cnt2 += tile[y+3][x+0]=='.'?1:0;
        cnt2 += tile[y+3][x+1]=='.'?1:0;
        cnt2 += tile[y+3][x+2]=='.'?1:0;

        cnt2 += tile[y+5][x+5]=='.'?1:0;
        cnt2 += tile[y+6][x+5]=='.'?1:0;
        cnt2 += tile[y+7][x+5]=='.'?1:0;
        cnt2 += tile[y+8][x+5]=='.'?1:0;
        cnt2 += tile[y+5][x+6]=='.'?1:0;
        cnt2 += tile[y+5][x+7]=='.'?1:0;
        cnt2 += tile[y+5][x+8]=='.'?1:0;

        //System.out.println(y+" "+x+" "+cnt1+" "+cnt2);
        return cnt1==18 && cnt2==14;
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
