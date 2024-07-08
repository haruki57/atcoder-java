import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC354D_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int a,b,c,d;
    static int N = 4;
    static int[][] wall;
    static int[][] sum;
    static void run (final FastScanner scanner, final PrintWriter out) {
        a= scanner.nextInt()+1000000000;
        b= scanner.nextInt()+1000000000;
        c= scanner.nextInt()+1000000000;
        d= scanner.nextInt()+1000000000;
        int[][]wall = {
                {2,1,0,1},
                {1,2,1,0},
                {2,1,0,1},
                {1,2,1,0},
        };
        sum = new int[N+1][N+1];
        for (int i = 1; i < sum.length; i++) {
            for (int j = 1; j < sum.length; j++) {
                sum[i][j]=sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1]+wall[i-1][j-1];
            }
        }
        System.out.println(black(d,c)-black(d,a)-black(b,c)+black(b,a));
    }

    private static long black(int y, int x) {
        long ret = 0;
        int yn = y/N;
        int xn = x/N;
        ret += sum[N][N] * (long)yn*xn;

        int ynn = y%N;
        int xnn = x%N;
        ret += (long)sum[N][xnn]*yn;
        ret += (long)sum[ynn][N]*xn;
        ret += sum[ynn][xnn];
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
