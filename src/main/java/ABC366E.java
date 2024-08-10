import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC366E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static int MAX = 1000000;

    static void run (final FastScanner scanner, final PrintWriter out) {
        /*
        System.out.println(lowNum(new int[]{1,2,3}, 0));
        System.out.println(lowNum(new int[]{1,2,3}, 1));
        System.out.println(lowNum(new int[]{1,2,3}, 2));
        System.out.println(lowNum(new int[]{1,2,3}, 3));
        System.out.println(lowNum(new int[]{1,2,3}, 4));

         */
        int N = scanner.nextInt();
        int D = scanner.nextInt();
        int[][] xy = new int[N][2];
        int[] x = new int[N];
        int[] y = new int[N];
        for (int i = 0; i < N; i++) {
            xy[i][0]= scanner.nextInt();
            x[i]=xy[i][0];
            xy[i][1]= scanner.nextInt();
            y[i]=xy[i][1];
        }
        Arrays.sort(x);
        Arrays.sort(y);
        int centerx = x[x.length/2];
        int centery = y[y.length/2];
        long d = 0;
        for (int i = 0; i < N; i++) {
            d += Math.abs(xy[i][0]-centerx) + Math.abs(xy[i][1]-centery);
        }
        if(d > D){
            System.out.println(0);
            return;
        }
        int left = lowNum(x, centerx);
        int right = N-left;
        long dd = d;
        long ans = 0;
        int MOSTR = 0;
        int MOSTL = 0;
        long rightD = 0;
        long leftD = 0;
        for (int i = centerx;; ) {
            left = lowNum(x, i);
            right = N-left;
            i++;
            dd -= right;
            dd += left;
            if(dd>D) {
                MOSTR = i-1;
                break;
            }
            rightD = dd;
        }

        dd = d;
        for (int i = centerx;; ) {
            left = lowNum(x, i);
            right = N-left;
            i--;
            dd += right;
            dd -= left;
            if(dd>D) {
                MOSTL = i+1;
                break;
            }
            leftD = dd;
        }
        ans += MOSTR-MOSTL+1;
        System.out.println(MOSTL+" "+MOSTR);
        dd = d;
        int r = MOSTR;
        int l = MOSTL;
        for (int i= centery;; ) {
            int lower = lowNum(y, i);
            int upper = N - lower;
            i++;
            dd += lower;
            dd -= upper;
            rightD += lower;
            rightD -= upper;
            while(rightD > D) {
                left = lowNum(x, r);
                right = N-left;
                r--;
                rightD += right;
                rightD -= left;
            }
            leftD += lower;
            leftD -= upper;
            while(leftD > D) {
                left = lowNum(x, l);
                right = N-left;
                l++;
                leftD -= right;
                leftD += left;
            }
            if(leftD >= rightD) {
                break;
            }
            ans+=rightD-leftD+1;
            System.out.println(leftD+" "+rightD);
        }
        System.out.println(ans);

        /*
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                int sum = 0;
                for (int k = 0; k < N; k++) {
                    sum += Math.abs(xy[k][0]-i) + Math.abs(xy[k][1]-j);
                }
                if(sum<=D) {
                    if(i==xy[0][0]&&j==xy[0][1]) {
                        System.out.print(1+" ");
                    } else if(i==xy[1][0]&&j==xy[1][1]) {
                        System.out.print(2+" ");
                    } else if(i==xy[2][0]&&j==xy[2][1]) {
                        System.out.print(3+" ");
                    } else if(sum==9) {
                        System.out.print(9+" ");
                    } else {
                        System.out.print(7+" ");
                    }

                    list.add(new int[]{i,j, sum});
                } else {
                    System.out.print(0+" ");
                }
            }
            System.out.println();
        }
        for (int[] h : list) {
            System.out.println(Arrays.toString(h));
        }

         */
    }

    static int lowNum(int[] x, int target) {
        int ok = x.length;
        int ng = -1;
        while(Math.abs(ok-ng)>1) {
            int mid = (ok+ng)/2;
            if(x[mid] > target) {
                ok = mid;
            } else {
                ng = mid;
            }
        }
        return ok;
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
