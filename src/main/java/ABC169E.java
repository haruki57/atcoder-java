import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC169E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] a = new int[N][2];
        int[] mins=new int[N];
        int[] maxs = new int[N];
        for (int i = 0; i < N; i++) {
            mins[i]=a[i][0]= scanner.nextInt();
            maxs[i]=a[i][1]= scanner.nextInt();
        }
        Arrays.sort(mins);
        Arrays.sort(maxs);

        /*
        範囲の値は連続になる。Nが偶数なら、x, x+1, x+2...
        Nが奇数なら、x, x+0.5, x+1...
        範囲の最小値と最大値を求めれば良い
        範囲の最小値=全部の区間の一番小さい値を取ったときの中央値
        範囲の最大値=全部の区間の一番大さい値を取ったときの中央値

         */
        if (N%2==0) {
            System.out.println((long)(((maxs[N/2-1]+maxs[N/2])/2.0-(mins[N/2-1]+mins[N/2])/2.0)*2)+1);
        } else {
            System.out.println(maxs[N/2]-mins[N/2]+1);
        }

        /*
        double l =0, r=0;
        l = (a[0][0]+a[1][0])/2.0;
        r = (a[0][1]+a[1][1])/2.0;
        System.out.println(l+" "+r);
        for (int i = 2; i < N; i++) {
            l = (l+a[i][0])/2.0;
            r = (r+a[i][1])/2.0;
            System.out.println(l+" "+r);
        }
        System.out.println((long)(r-l)*2);
         */
        /*
        for (int i = a[0][0]; i <= a[0][1]; i++) {
            for (int j = a[1][0]; j <= a[1][1]; j++) {
                for (int k = a[2][0]; k <= a[2][1]; k++) {
                    System.out.println(i+" "+j+" "+k);
                    int[] arr = {i,j,k};
                    Arrays.sort(arr);
                    System.out.println(arr[1]);
                    System.out.println();
                }
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
