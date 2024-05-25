import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ABC355D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] a = new int[N][2];
        for (int i = 0; i < N; i++) {
            a[i][0]= scanner.nextInt();
            a[i][1]= scanner.nextInt();
        }
        Arrays.sort(a, new Comparator<int[]>() {
            /*
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1]==o2[1]) {
                    return o1[0]-o2[0];
                }
                return o1[1]-o2[1];
            }

             */
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0]==o2[0]) {
                    return o1[1]-o2[1];
                }
                return o1[0]-o2[0];
            }
        });
        for (int i = 0; i < N; i++) {
            //System.out.println(Arrays.toString(a[i]));
        }
        long ans = 0;

        for (int i = 0; i < N; i++) {
            int ok = i, ng=N;
            while(Math.abs(ok-ng) > 1) {
                int mid = (ok+ng)/2;
                if (a[mid][0]<=a[i][1]) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            ans += (ok)-i;
        }
        System.out.println(ans);
        /*
        int next = 1;

        for (int i = 0; i < N; i++) {
            while(next<N) {
                if (a[i][1] >= a[next][0]) {

                } else {
                    break;
                }
                next++;
            }
            System.out.println(i+" "+next);
            ans += (next-1)-i;
        }
        System.out.println(ans);
         */
        /*
        long ans2 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (a[i][0]<=a[j][0] && a[j][0] <= a[i][1] ||
                        a[i][0]<=a[j][1] && a[j][1] <= a[i][1] ||
                        a[j][0]<=a[i][0] && a[i][0] <= a[j][1] ||
                        a[j][0]<=a[i][1] && a[i][1] <= a[j][1]

                )
                    ans2++;
            }
        }
        System.out.println(ans2);

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
