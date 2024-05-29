import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC334C_2 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[] a = new int[K];
        Arrays.setAll(a, i -> scanner.nextInt());
        if((K)%2==0) {
            long ans = 0;
            for (int i = 0; i < a.length; i+=2) {
                ans += a[i+1]-a[i];
            }
            System.out.println(ans);
            return;
        }
        long[] sum = new long[K/2+1];
        for (int i = 0; i < K-1; i+=2) {
            sum[i/2+1]=sum[i/2]+a[i+1]-a[i];
        }

        long[] sumRev = new long[K/2+1];
        for (int i = 1; i < K-1; i+=2) {
            sumRev[i/2+1]=sumRev[i/2]+a[a.length-i]-a[a.length-i-1];
        }
        //System.out.println(Arrays.toString(sum));
        //System.out.println(Arrays.toString(sumRev));
        long min = (long)INF*INF;
        for (int i = 0; i < K; i++) {
            if (i%2==0) {
                min = Math.min(min, sum[i/2] + sumRev[sumRev.length-i/2-1]);
            } else {
                //System.out.println(i/2+" "+(sumRev.length-i/2-2));
                //System.out.println(sum[i/2]+" "+sumRev[(sumRev.length-i/2-2)]);
                //System.out.println();
                min = Math.min(min, sum[i/2] + sumRev[sumRev.length-i/2-2] + a[i+1]-a[i-1]);
            }
        }
        System.out.println(min);

/*
        long[] sum0 = new long[K/2+1];
        long[] sum1 = new long[K/2+1];

        for (int i = 0; i < K-1; i+=2) {
            sum0[i/2+1]=sum0[i/2]+(a[i+1]-a[i]);
        }
        for (int i = 1; i < K-1; i+=2) {
            sum1[i/2+1]=sum1[i/2]+(a[i+1]-a[i]);
        }
        long min = (long)INF*INF;
        System.out.println(Arrays.toString(sum0));
        System.out.println(Arrays.toString(sum1));
        for (int i = 0; i < K; i++) {

            if (i%2==0) {
                min = Math.min(min, sum1[i/2]+(sum1[sum1.length-1]-sum1[i/2]));
            } else {
                continue;
            }
            System.out.println(i+" "+i/2+" "+(i/2+1));
            System.out.println(min);
            System.out.println();
        }
        System.out.println(min);
         */
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
