import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ARC084A {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int upperBound(int[] A, int x) {
        int ng = A.length, ok = -1;
        while(Math.abs(ok - ng) > 1) {
            int mid = (ng + ok) / 2;
            if(A[mid] < x) {
                ok = mid;
            }
            else {
                ng = mid;
            }
        }
        return ok;
    }
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[N];
        Arrays.setAll(b, i -> scanner.nextInt());
        int[] c = new int[N];
        Arrays.setAll(c, i -> scanner.nextInt());
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);
        long[] sumB = new long[N];
        for (int i = 0; i < N; i++) {
            int target = b[i];
            int up = upperBound(a, target) + 1;
            if (i==0) {
                sumB[i] = up;
            } else {
                sumB[i] = sumB[i-1]+(up);
            }
        }

        /*
        for (int i = N-1; i>= 0; i--) {
            int target = b[i];
            int up = upperBound(a, target) + 1;
            System.out.println(up);
            if (i==N-1) {
                sumB[i] = up;
            } else {
                sumB[i] = sumB[i+1]+(up);
            }
        }

         */

        /*
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));

         */
        /*
        for (int i = 0; i < sumB.length/2; i++) {
            long tmp = sumB[i];
            sumB[i] = sumB[N-i-1];
            sumB[N-i-1] = tmp;
        }

         */

        //System.out.println(Arrays.toString(sumB));
        long sum = 0;
        for (int i = 0; i < N; i++) {
            //System.out.println(c[i]);
            int lowerB = upperBound(b, c[i]);
            //System.out.println(lowerB);
            //System.out.println();
            if (lowerB == -1 || lowerB == N) {
                continue;
            }


            sum += sumB[lowerB];
        }
        System.out.println(sum);
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
