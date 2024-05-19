import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC354D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;
    static long[][] a = new long[4][4];
    static long[][] sum = new long[5][5];
    static int N = 4;
    static void run (final FastScanner scanner, final PrintWriter out) {
/*
        long A = scanner.nextLong();
        long B = scanner.nextLong();
        long C = scanner.nextLong();
        long D = scanner.nextLong();

 */


        long B = scanner.nextLong()+1000000000L;
        long A = scanner.nextLong()+1000000000L;
        long D = scanner.nextLong()+1000000000L;
        long C = scanner.nextLong()+1000000000L;

        a[0][0]=2;
        a[0][1]=1;
        a[0][2]=0;
        a[0][3]=1;

        a[1][0]=1;
        a[1][1]=2;
        a[1][2]=1;
        a[1][3]=0;

        a[2][0]=2;
        a[2][1]=1;
        a[2][2]=0;
        a[2][3]=1;

        a[3][0]=1;
        a[3][1]=2;
        a[3][2]=1;
        a[3][3]=0;

        for (int i = 1; i < sum.length; i++) {
            for (int j = 1; j < sum.length; j++) {
                sum[i][j]=sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1]+a[i-1][j-1];
            }
        }
        //System.out.println(f(C,D)-f(A,D)-f(B,C)+f(A,B));
        System.out.println(f(C,D)-f(A,D)-f(C,B)+f(A,B));
    }

    private static long f(long a, long b) {
        long ret = 0;
        long an = a/N;
        long bn = b/N;
        //int ap = (int)((a+N-1)%N);
        //int bp = (int)((b+N-1)%N);
        int ap = (int)(a%N);
        int bp = (int)(b%N);
        ret += bn*an*sum[N][N];

        //System.out.println(an+" "+bn+" "+ap+" "+bp);
        //System.out.println(ret);

        ret += an*sum[N][bp];
        ret += bn*sum[ap][N];
        ret += sum[ap][bp];
        //System.out.println(ret);
        //System.out.println();
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
