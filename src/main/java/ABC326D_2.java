import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

// WA

public class ABC326D_2 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int N;
    static char[] s;
    static char[] t;
    static char[][] tile;

    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        s = scanner.next().toCharArray();
        t = scanner.next().toCharArray();
        tile = new char[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(tile[i], '.');
        }
        bt1(0);
    }

    static void bt1(int depth){
        if (depth == N) {
            //out();

            bt2(0);
            return;
        }
        for (int i = 0; i < N-2; i++) {
            tile[depth][i]=s[depth];
            if (!ng1()) {
                bt1(depth+1);
            }


            tile[depth][i]='.';
        }
    }
    static void bt2(int depth){
        if (depth == N) {
            if (okFirstRow() && okFirstCol()) {
                bt3(0, 'A');
            }
            return;
        }
        boolean isFirst=true;
        for (int i = 0; i < N; i++) {
            if (tile[i][depth]!='.') {
                if(isFirst) {
                    if (tile[i][depth] == t[depth]) {
                        bt2(depth+1);
                    }
                    isFirst=false;
                }
            } else {
                tile[i][depth] = t[depth];
                if (!ng1() && !ng2())
                    bt2(depth + 1);
                tile[i][depth] = '.';
            }
        }
    }

    static void bt3(int depth, char c) {
        if (depth == N) {
            System.out.println(depth +" "+c);
            if (c!='D') {
                bt3(0, (char)(c+1));
            } else {
                out();
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            if (puttable(i, depth, c)) {
                tile[i][depth]=c;
                out();
                bt3(depth+1, c);
                tile[i][depth]='.';
            }
        }
    }

    static boolean puttable(int y,int x, char c) {
        if (tile[y][x]!='.') {
            return false;
        }
        for (int i = 0; i < N; i++) {
            if (tile[y][i]==c) {
                return false;
            }
            if (tile[i][x]==c) {
                return false;
            }
        }
        return true;
    }

    static boolean ng1() {
        for (int i = 0; i < N; i++) {
            int[] cnt = new int[3];
            for (int j = 0; j < N; j++) {
                if (tile[j][i]!='.') {
                    cnt[tile[j][i]-'A']++;
                }
            }
            if (cnt[0]>1||cnt[1]>1||cnt[2]>1){
                return true;
            }
        }
        return false;
    }

    static boolean ng2() {
        for (int i = 0; i < N; i++) {
            int[] cnt = new int[3];
            for (int j = 0; j < N; j++) {
                if (tile[i][j]!='.') {
                    cnt[tile[i][j]-'A']++;
                }
            }
            if (cnt[0]>1||cnt[1]>1||cnt[2]>1){
                return true;
            }
        }
        return false;
    }
    static boolean okFirstRow() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tile[i][j]!='.') {
                    if (tile[i][j]!=s[i]) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    static boolean okFirstCol() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tile[j][i]!='.') {
                    if (tile[j][i]!=t[i]) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
    static void out() {
        for (int i = 0; i < N; i++) {
            System.out.println(tile[i]);
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
