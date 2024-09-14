import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC262D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long ans = N;
        for (int i = 2; i <= N; i++) {
            int[] b = new int[N];
            for (int j = 0; j < a.length; j++) {
                b[j]=(int)(a[j]%i);
            }
            long[][][] dp = new long[N+1][i+1][i];
            dp[0][0][0]=1;
            for (int j = 0; j < N; j++) {
                for (int k = 0; k <= i;k++) {
                    for (int l = 0; l < i; l++) {
                        dp[j+1][k][l]+=dp[j][k][l];
                        dp[j+1][k][l]%=MOD;
                        if(k+1<dp[j].length) {
                            dp[j+1][k+1][(l+b[j])%i]+=dp[j][k][l];
                            dp[j+1][k+1][(l+b[j])%i]%=MOD;
                        }
                    }
                }
            }
            ans+=dp[N][i][0];
            ans%=MOD;
        }
        System.out.println(ans);
        //ansTle(a);
    }

    static void ansTle(long[] a) {
        long ans = 0;
        for (int bit = 1; bit < 1 << a.length; bit++) {
            long sum = 0;
            for (int i = 0; i < a.length; i++) {
                if((bit&(1<<i))> 0) {
                    sum += a[i];
                }
            }
            if(sum%Integer.bitCount(bit)==0) {
                System.out.println(Integer.bitCount(bit)+" "+Integer.toBinaryString(bit)+" "+sum);
                ans++;
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
