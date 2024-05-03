import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC338C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] q = new long[N];
        Arrays.setAll(q, i -> scanner.nextInt());
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[N];
        Arrays.setAll(b, i -> scanner.nextInt());
        long ans = 0;
        for (int aNum = 0; aNum <= 1000000; aNum++) {
            long[] qq = q.clone();
            boolean ng = false;
            for (int i = 0; i < N; i++) {
                qq[i] -= (long)a[i]*aNum;
                if (qq[i]<0) {
                    ng=true;
                    break;
                }
            }
            if (ng) {
                break;
            }
            long bNum = INF;
            for (int i = 0; i < N; i++) {
                if (b[i]>0)
                    bNum = Math.min(bNum, qq[i]/b[i]);
            }
            ans = Math.max(ans, aNum+bNum);
        }
        System.out.println(ans);
        /*
        int aOk = -1, aNg = INF/2;
        long ans = 0;
        while(Math.abs(aOk-aNg)>1){
            int aMid = (aOk+aNg)/2;
            long[] qq = q.clone();
            boolean ok = true;
            for (int i = 0; i < qq.length; i++) {
                qq[i]-=(long)aMid*a[i];
                if (qq[i]<0) {
                    ok=false;
                    break;
                }
            }
            long bMin = INF;
            for (int i = 0; i < N; i++) {
                bMin = Math.min(bMin, qq[i]/b[i]);
            }
            System.out.println(aMid + " "+ bMin);
            if (ok) {
                aOk = aMid;
                ans = Math.max(aMid + bMin, ans);
            } else {
                aNg = aMid;
            }
        }

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
