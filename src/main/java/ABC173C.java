import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC173C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int K = scanner.nextInt();
        char[][] tile = new char[H][];
        for (int i = 0; i < H; i++) {
            tile[i]=scanner.next().toCharArray();
        }
        int ans = 0;
        for (int bit1 = 0; bit1 < (1 << H); bit1++) {
            for (int bit2 = 0; bit2 < (1 << W); bit2++) {
                char[][] newTile = new char[H][W];
                for (int i = 0; i < H; i++) {
                    for (int j = 0; j < W; j++) {
                        newTile[i][j]=tile[i][j];
                    }
                }
                for (int i = 0; i < Integer.toBinaryString(bit1).length(); i++) {
                    if (((1<<i)&bit1) > 0) {
                        for (int j = 0; j < W; j++) {
                            newTile[i][j]='r';
                        }
                    }
                }
                for (int i = 0; i < Integer.toBinaryString(bit2).length(); i++) {
                    if (((1<<i)&bit2) > 0) {
                        for (int j = 0; j < H; j++) {
                            newTile[j][i]='r';
                        }
                    }
                }
                int cnt = 0;
                for (int i = 0; i < H; i++) {
                    for (int j = 0; j < W; j++) {
                        if (newTile[i][j]=='#') {
                            cnt++;
                        }
                    }
                }
                for (int i = 0; i < H; i++) {
                    //System.out.println(newTile[i]);
                }
                //System.out.println();
                if(cnt==K) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
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
