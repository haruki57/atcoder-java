package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _060 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] re = a.clone();
        for (int i = 0; i < re.length; i++) {
            re[i]=a[a.length-i-1];
        }
        var lis = lis(a);
        var lisRe = lis(re);
        /*
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(re));
        System.out.println(Arrays.toString(lis[0]));
        System.out.println(Arrays.toString(lis[1]));
        System.out.println(Arrays.toString(lisRe[0]));
        System.out.println(Arrays.toString(lisRe[1]));

         */
        int ans = 0;
        for (int i = 0; i < lis[1].length; i++) {
            ans = Math.max(lis[1][i]+lisRe[1][lis[1].length-1-i]-1, ans);
        }
        System.out.println(ans);
    }

    static int[][] lis(int[] a) {
        int n = a.length;
        int[] dp = new int[n+1];
        int[] p = new int[n];
        Arrays.fill(dp, INF);
        dp[1]=a[0];
        dp[0]=-1;
        p[0]=1;
        int ret = 1;
        for (int i = 1; i < a.length; i++) {
            int ng=0, ok=i+1;
            while(Math.abs(ok-ng)> 1) {
                int mid = (ok+ng)/2;
                if(dp[mid] >= a[i]) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            dp[ok]=a[i];
            p[i]=ok;
            ret = Math.max(ret, ok);
            //System.out.println(i+" "+ng+" "+ok+" "+a[i]);
            //System.out.println(Arrays.toString(dp));
        }
        return new int[][]{dp, p};
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
