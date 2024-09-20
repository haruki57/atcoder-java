import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ARC062C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        /*
        for (int i = 2; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                if(i%3==0||j%3==0){
                    continue;
                }
                System.out.println(i+" "+j);
                System.out.println(i%2+" "+j%2);
                System.out.println(i%3+" "+j%3);
                System.out.println(ansVer(i, j)+" "+ ansVer(j, i));
                System.out.println(ansHori(i, j)+" "+ ansHori(j, i));
                System.out.println();
            }
        }

         */
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        System.out.println(Math.min(Math.min(ansVer(H, W), ansVer(W, H)), Math.min(ansHori(H, W), ansHori(W, H))));
    }

    private static long ansVer(long H, long W) {
        long ans = Long.MAX_VALUE;
        for (long i = 1; i < H; i++) {
            long a = i * W;
            long restH = (H-i);
            long w = W/2;
            long restW = W-w;
            long b = restH*w;
            long c = restH*restW;
            long[] arr = {a,b,c};
            Arrays.sort(arr);
            ans = Math.min(ans, arr[2]-arr[0]);

            /*
            long low = 0;
            long high = INF;
            int cnt = 100;
            while(cnt-- >= 0) {
                long w1 = (low+low+high)/3;
                long w2 = (low+high+high)/3;
                System.out.println(w1+" "+w2);
                long b1 = restH*w1;
                long c1 = restH*(W-w1);
                long[] arr1 = {a,b1,c1};
                Arrays.sort(arr1);
                long ans1 = Math.min(ans, Math.abs(arr1[0]-arr1[2]));
                ans = Math.min(ans, ans1);

                long b2 = restH*w2;
                long c2 = restH*(W-w2);
                long[] arr2 = {a,b2,c2};
                Arrays.sort(arr2);
                long ans2 = Math.min(ans, Math.abs(arr2[0]-arr2[2]));
                ans = Math.min(ans, ans2);
                if (ans1 > ans2) {
                    low = w1;
                } else {
                    high = w2;
                }
            }

             */

            /*
            for (int j = 1; j < W; j++) {
                long w = j;
                long restW = W-w;

                long b = restH*w;
                long c = restH*restW;
                long[] arr = {a,b,c};
                Arrays.sort(arr);
                ans = Math.min(ans, Math.abs(arr[0]-arr[2]));
            }

             */
        }
        return ans;
    }

    private static long ansHori(long H, long W) {
        long ans = Long.MAX_VALUE;
        for (int i = 1; i < H; i++) {
            long a = i * W;
            long restH = (H-i);
            long halfRestH = restH /2;
            long restRestH = restH - halfRestH;
            long b = halfRestH * W;
            long c = restRestH * W;
            long[] arr = {a,b,c};
            Arrays.sort(arr);
            ans = Math.min(ans, arr[2]-arr[0]);

            /*
            long low = 0;
            long high = INF;
            int cnt = 100;
            while(cnt-- >= 0) {
                long h1 = (low+low+high)/3;
                long h2 = (low+high+high)/3;

                long b1 = h1*W;
                long c1 = (restH-h1)*W;
                long[] arr1 = {a,b1,c1};
                Arrays.sort(arr1);
                long ans1 = Math.min(ans, Math.abs(arr1[0]-arr1[2]));
                ans = Math.min(ans, ans1);

                long b2 = h2*W;
                long c2 = (restH-h2)*W;
                long[] arr2 = {a,b2,c2};
                Arrays.sort(arr2);
                long ans2 = Math.min(ans, Math.abs(arr2[0]-arr2[2]));
                ans = Math.min(ans, ans2);
                if (ans1 > ans2) {
                    low = h1;
                } else {
                    high = h2;
                }
            }

             */

            /*
            for (int j = 1; j < W; j++) {
                long w = j;
                long restW = W-w;

                long b = restH*w;
                long c = restH*restW;
                long[] arr = {a,b,c};
                Arrays.sort(arr);
                ans = Math.min(ans, Math.abs(arr[0]-arr[2]));
            }
             */

            /*
            for (int j = 1; j < restH; j++) {
                long restRestH = restH-j;
                long b = j*W;
                long c = restRestH*W;
                long[] arr = new long[]{a,b,c};
                Arrays.sort(arr);
                ans = Math.min(ans, Math.abs(arr[0]-arr[2]));
            }

             */
        }
        return ans;
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
