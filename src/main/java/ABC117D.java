import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC117D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;
    static long K;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        K = scanner.nextLong();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextLong());
        /*
        long max = 0;
        for (int i = 0; i <= K*K*K; i++) {
            long cnt = 0;
            for (int j = 0; j < N; j++) {
                cnt += a[j]^i;
            }
            System.out.println(Integer.toBinaryString(i));
            System.out.println(cnt);
            System.out.println();
            max=Math.max(max,cnt);
        }
        System.out.println(max);
*/
        long[][] maxs = new long[50][2];


        long ans = 0;
        for (int j = 0; j < maxs.length; j++) {
            for (int k = 0; k < 2; k++) {
                long cnt = 0;
                for (int i = 0; i < N; i++) {
                    cnt += (((a[i]>>j)&1)^k)<<j;
                }
                maxs[j][k]=cnt;
            }
        }
        for (int i = 0; i < maxs.length; i++) {
            //System.out.println(maxs[i][0]+" "+maxs[i][1]);
        }
        System.out.println(bt(maxs, maxs.length-1, 0, 0));
        //System.out.println(ans);
    }

    static long bt(long[][] maxs, int bitCount, long sum, long ans) {
        if (bitCount == -1) {
            return ans;
        }
        if (sum > K) {
            return 0;
        }
        if (sum + (1L<<bitCount) <= K && ans + maxs[bitCount][0] < ans + maxs[bitCount][1]) {
            return bt(maxs, bitCount-1, sum + (1L<<bitCount), ans + maxs[bitCount][1]);
        } else {
            return bt(maxs, bitCount-1, sum, ans + maxs[bitCount][0]);
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
