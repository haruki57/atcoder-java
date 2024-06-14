import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC232E {
    static long MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {

        long H = scanner.nextInt();
        long W = scanner.nextInt();
        int K = scanner.nextInt();
        long x1 = scanner.nextInt();
        long y1 = scanner.nextInt();
        long x2 = scanner.nextInt();
        long y2 = scanner.nextInt();
        long[][] cnt = new long[K+1][4];
        if (x1==x2 && y1==y2) {
            cnt[0][0]=1;
        } else if(x1==x2) {
            cnt[0][1]=1;
        } else if (y1==y2) {
            cnt[0][2]=1;
        } else {
            cnt[0][3]=1;
        }

        for (int i = 1; i <= K; i++) {
            cnt[i][0] = (cnt[i-1][1] + cnt[i-1][2])%MOD;
            cnt[i][1] = ((W-1)*cnt[i-1][0] + ((W-2)*cnt[i-1][1] + cnt[i-1][3]))%MOD;
            cnt[i][2] = ((H-1)*cnt[i-1][0] + ((H-2)*cnt[i-1][2] + cnt[i-1][3]))%MOD;
            cnt[i][3] = ((W-2)*cnt[i-1][3] + (H-2)*cnt[i-1][3] + (H-1)*cnt[i-1][1]+(W-1)*cnt[i-1][2])%MOD;
        }
        for (int i = 0; i < cnt.length; i++) {
            //System.out.println(Arrays.toString(cnt[i]));
        }
        System.out.println(cnt[K][0]);
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
