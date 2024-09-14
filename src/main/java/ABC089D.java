import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC089D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int D = scanner.nextInt();
        int[][] a = new int[H*W][2];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int x = scanner.nextInt()-1;
                a[x][0]=i;
                a[x][1]=j;
            }
        }
        int N = H*W;
        Long[][] sums = new Long[D][];
        for (int i = 0; i < D; i++) {
            List<Long> list = new ArrayList<>();
            long sum = 0;
            list.add(sum);
            for (int j = i; j + D < N; j+=D) {
                int jj = j + D;
                int x1 = a[j][0];
                int y1 = a[j][1];
                int x2 = a[jj][0];
                int y2 = a[jj][1];
                long dist = Math.abs(x1-x2) + Math.abs(y1-y2);
                sum += dist;
                list.add(sum);
            }
            sums[i]= list.toArray(new Long[]{});
        }


        int Q = scanner.nextInt();
        while(Q-->0) {
            int s = scanner.nextInt()-1;
            int t = scanner.nextInt()-1;
            int num = s%D;
            int dist = (t-s)/D;
            int start = s / D;
            out.println(sums[num][start+dist]-sums[num][start]);
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
