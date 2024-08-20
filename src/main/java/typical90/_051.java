package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _051 {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE/2;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        long P = scanner.nextLong();
        long[] a = new long[N/2+N%2];
        int NA = a.length;
        Arrays.setAll(a, i -> scanner.nextLong());
        long[] b = new long[N/2];
        int NB = b.length;
        Arrays.setAll(b, i -> scanner.nextLong());
        long[][] sumA = new long[NA+1][1<<NA];
        for (int i = 0; i < sumA.length; i++) {
            Arrays.fill(sumA[i], INF);
        }
        fill(a, sumA);
        long[][] sumB = new long[NB+1][1<<NB];
        for (int i = 0; i < sumB.length; i++) {
            Arrays.fill(sumB[i], INF);
        }
        fill(b, sumB);

        long ans = 0;
        for (int ai = 0; ai < sumA.length; ai++) {
            int bi = K-ai;
            if(bi < 0 || bi >= sumB.length) {
                continue;
            }
            for (int i = 0; i < sumA[ai].length; i++) {
                long yen = sumA[ai][i];
                if(yen == INF) {
                    break;
                }

                int ub = upperBound(sumB[bi], yen, P);

                /*
                System.out.println(yen);
                System.out.println(Arrays.toString(sumB[bi]));
                System.out.println(ub+1);
                System.out.println();

                 */

                ans += ub+1;
                /*
                if(lb==-1) {
                    ans += ub+1;
                } else {
                    ans += ub-lb+1;
                }

                 */

            }
        }
        System.out.println(ans);


        /*
        for (int i = 0; i < sumA.length; i++) {
            System.out.println(Arrays.toString(sumA[i]));
        }
        for (int i = 0; i < sumB.length; i++) {
            System.out.println(Arrays.toString(sumB[i]));
        }

         */

    }

    private static int upperBound(long[] longs, long yen, long p) {
        int ng = longs.length, ok = -1;
        while(Math.abs(ok-ng) > 1) {
            int mid = (ok+ng)/2;
            if(longs[mid]+yen <= p) {
                ok = mid;
            } else {
                ng = mid;
            }
        }
        return ok;
    }

    private static void fill(long[] a, long[][] sumA) {
        for (int bit = 0; bit < 1 << a.length; bit++) {
            int cnt = Integer.bitCount(bit);
            long sum = 0;
            for (int i = 0; i < a.length; i++) {
                if((bit&(1<<i)) == 0) {
                    continue;
                }
                sum+= a[i];
            }
            sumA[cnt][bit]=sum;
        }
        for (int i = 0; i < sumA.length; i++) {
            Arrays.sort(sumA[i]);
            int cnt = 0;
            for (int j = 0; j < sumA[i].length; j++) {
                if(sumA[i][j]!=INF) {
                    cnt++;
                }
            }
            long[] newLong = new long[cnt];
            for (int j = 0; j < newLong.length; j++) {
                newLong[j]=sumA[i][j];
            }
            sumA[i]=newLong;
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
