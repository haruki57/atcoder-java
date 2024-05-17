import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC326D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static char[][] tile;
    static char[] R;
    static char[] C;
    static int N;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        tile=new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tile[i][j]='.';
            }
        }
        R = scanner.next().toCharArray();
        C = scanner.next().toCharArray();
        if (backtrack('A', 0)) {
            System.out.println("Yes");
            print(tile);
        } else {
            System.out.println("No");
        }
    }

    static boolean backtrack(char c, int y) {
        if(y==N) {
            if ((c+1)=='D') {
                for (int i = 0; i < N; i++) {
                    char first=0;
                    for (int j = 0; j < N; j++) {
                        if(tile[i][j]!='.'){
                            first=tile[i][j];
                            break;
                        }
                    }
                    if (R[i]!=first) {
                        return false;
                    }
                }

                for (int i = 0; i < N; i++) {
                    char first=0;
                    for (int j = 0; j < N; j++) {
                        if(tile[j][i]!='.'){
                            first=tile[j][i];
                            break;
                        }
                    }
                    if (C[i]!=first) {
                        return false;
                    }
                }
                return true;
            }
            return backtrack((char)(c+1), 0);
        }
        for (int i = 0; i < N; i++) {
            if (tile[y][i]!='.') {
                continue;
            }
            tile[y][i]=c;
            if(perOneOk(tile, c)) {
                if (backtrack(c, y+1)){
                    return true;
                }
            }

            tile[y][i]='.';
        }
        return false;
    }

    static boolean perOneOk(char[][] tile, char c) {
        int[] cntRow = new int[N];
        int[] cntCol = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tile[i][j]==c) {
                    cntRow[i]++;
                    cntCol[j]++;
                    if (cntRow[i]>=2||cntCol[j]>=2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    static void print(char[][] tile) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(tile[i][j]);
            }
            System.out.println();
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
