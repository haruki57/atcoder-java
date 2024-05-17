import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC322D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int[][] tile;
    static char[][][] abc;
    static void run (final FastScanner scanner, final PrintWriter out) {
        abc = new char[3][4][];
        for (int i = 0; i < 4; i++) {
            abc[0][i] = scanner.next().toCharArray();
        }
        for (int i = 0; i < 4; i++) {
            abc[1][i] = scanner.next().toCharArray();
        }
        for (int i = 0; i < 4; i++) {
            abc[2][i] = scanner.next().toCharArray();
        }
        tile = new int[12][12];
        int cnt = 0;
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 4; j++) {

                for (int k = 0; k < 4; k++) {
                    cnt+=(abc[i][j][k]=='#')?1:0;
                }
            }
        }
        if (cnt!=16) {
            System.out.println("No");
            return;
        }
        if (bt(0)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    static boolean bt(int depth) {
        if (depth==3) {
            int cnt = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tile[i+4][j+4]!=1) {
                        return false;
                    } else {
                        cnt++;
                    }
                }
            }
            int cntAll = 0;
            return cnt==16;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    boolean moreThan1 = false;
                    for (int l = 0; l < 4; l++) {
                        for (int m = 0; m < 4; m++) {
                            if (abc[depth][l][m]=='#') {
                                tile[l+j][m+k]++;
                                if (tile[l+j][m+k] >= 2) {
                                    moreThan1=true;
                                }
                            }
                        }
                    }
                    if (!moreThan1 && bt(depth+1))  {
                        return true;
                    }

                    for (int l = 0; l < 4; l++) {
                        for (int m = 0; m < 4; m++) {
                            if (abc[depth][l][m]=='#') {
                                tile[l+j][m+k]--;
                            }
                        }
                    }

                }
            }
            abc[depth] = rotate(abc[depth]);
        }
        return false;
    }

    static char[][] rotate(char[][] a) {
        char[][] ret = new char[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ret[i][j]=a[3-j][i];
            }
        }
        return ret;
    }

    static void print(char[][] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                System.out.print(s[i][j]);
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
