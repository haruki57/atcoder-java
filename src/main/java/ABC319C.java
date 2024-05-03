import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC319C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int[] perm;
    static boolean[] used;
    static int N;
    static int[][] a = new int[3][3];
    static int[][] b = new int[3][3];
    static double a1=0,a2=0;
    static void perm(int depth) {
        if (depth == N) {
            int[] idxArr = new int[N];
            for (int i = 0; i < N; i++) {
                idxArr[i] = perm[i];
            }

            int cnt = -1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    b[i][j]=cnt--;
                }
            }

            for (int idx : idxArr) {
                int x = idx/3;
                int y = idx%3;
                b[x][y]=a[x][y];
                if (isGakkari(b)) {
                    a1++;
                    break;
                }
            }
            a2++;
            return;
        }
        for (int i = 0; i < N; i++) {
            if (used[i]) {
                continue;
            }
            used[i]=true;
            perm[depth]=i;
            perm(depth+1);
            used[i]=false;
        }
    }

    static boolean isGakkari(int[][] b) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0]==b[i][1] && b[i][2] <= 0) {
                return true;
            }
            if (b[i][2]==b[i][1] && b[i][0] <= 0) {
                return true;
            }
            if (b[i][0]==b[i][2] && b[i][1] <= 0) {
                return true;
            }

            if (b[0][i]==b[1][i] && b[2][i] <= 0) {
                return true;
            }
            if (b[2][i]==b[1][i] && b[0][i] <= 0) {
                return true;
            }
            if (b[0][i]==b[2][i] && b[1][i] <= 0) {
                return true;
            }
        }
        if (b[0][0]==b[1][1] && b[2][2] <= 0) {
            return true;
        }
        if (b[0][0]==b[2][2] && b[1][1] <= 0) {
            return true;
        }
        if (b[1][1]==b[2][2] && b[0][0] <= 0) {
            return true;
        }

        if (b[2][0]==b[1][1] && b[0][2] <= 0) {
            return true;
        }
        if (b[2][0]==b[0][2]&& b[1][1] <= 0) {
            return true;
        }
        if (b[1][1]==b[0][2] && b[2][0] <= 0) {
            return true;
        }

        return false;
    }


    static void run (final FastScanner scanner, final PrintWriter out) {
        perm = new int[9];
        used = new boolean[9];
        N = 9;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j]= scanner.nextInt();
            }
        }
        perm(0);
        //System.out.println(a1);
        //System.out.println(a2);
        System.out.println((a2-a1)/a2);
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
