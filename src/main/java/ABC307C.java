import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC307C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int HA = scanner.nextInt();
        int WA = scanner.nextInt();
        char[][] a = new char[10][10];
        for (int i = 0; i < a.length; i++) {
            Arrays.fill(a[i], '.');
        }
        for (int i = 0; i < HA; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 0; j < 10; j++) {
                if (j<s.length)
                    a[i][j] = s[j];
            }
        }

        int HB = scanner.nextInt();
        int WB = scanner.nextInt();
        char[][] b = new char[10][10];
        for (int i = 0; i < b.length; i++) {
            Arrays.fill(b[i], '.');
        }
        for (int i = 0; i < HB; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 0; j < 10; j++) {
                if (j<s.length)
                    b[i][j] = s[j];
            }
        }

        int HX = scanner.nextInt();
        int WX = scanner.nextInt();
        char[][] x = new char[10][10];
        for (int i = 0; i < x.length; i++) {
            Arrays.fill(x[i], '.');
        }
        for (int i = 0; i < HX; i++) {
            char[] s = scanner.next().toCharArray();
            for (int j = 0; j < 10; j++) {
                if (j<s.length)
                    x[i][j] = s[j];
            }
        }

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                var sheet = makeSheet(a, b, i, j);
                //printSheet(sheet);
                if (ok(x, sheet)) {
                    System.out.println("Yes");
                    return;
                }
            }
        }
        System.out.println("No");
    }

    private static void printSheet(char[][] sheet) {
        for (int i = 0; i < sheet.length; i++) {
            System.out.println(sheet[i]);
        }
        System.out.println();
    }

    private static boolean ok(char[][] x, char[][] sheet) {
        int cntX=0,cntSheet=0;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                if (x[i][j]=='#') cntX++;
            }
        }
        for (int i = 0; i < sheet.length; i++) {
            for (int j = 0; j < sheet[i].length; j++) {
                if (sheet[i][j]=='#')cntSheet++;
            }
        }
        if (cntX != cntSheet) {
            return false;
        }
        int len = x[0].length;
        for (int i = 0; i < sheet.length - x.length; i++) {
            for (int j = 0; j < sheet[i].length - len; j++) {
                boolean ok = true;
                //System.out.println(i+" "+j);
                for (int k = 0; k < x.length; k++) {
                    for (int l = 0; l < x[k].length; l++) {
                        int yy = i+k;
                        int xx = j+l;
                        if (x[k][l]!=sheet[yy][xx]) {
                            ok = false;
                        }
                    }
                }
                if (ok) {
                    return true;
                }
            }
        }
        return false;
    }

    private static char[][] makeSheet(char[][] a, char[][] b, int y, int x) {
        char[][] ret = new char[61][61];
        for (int i = 0; i < ret.length; i++) {
            Arrays.fill(ret[i], '.');
        }

        int centerY = ret.length /2, centerX = ret.length/2;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                int yy = i + centerY;
                int xx = j + centerX;
                if (a[i][j]=='#') {
                    ret[yy][xx] = '#';
                }
            }
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                int yy = i + y;
                int xx = j + x;
                if (b[i][j]=='#') {
                    ret[yy][xx] = '#';
                }
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
