import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC266C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    // https://qiita.com/NULLchar/items/aef3c133ee7a98410039

    static void run (final FastScanner scanner, final PrintWriter out) {
        int[][] a = new int[4][2];
        for (int i = 0; i < a.length; i++) {
            a[i][0]= scanner.nextInt();
            a[i][1]= scanner.nextInt();
        }
        for (int i = 0; i < 4; i++) {
            int ii = i;
            int jj = (i-1+4)%4;
            int kk = (i-2+4)%4;

            int x0 = a[ii][0];
            int y0 = a[ii][1];
            int x1 = a[jj][0];
            int y1 = a[jj][1];
            int x2 = a[kk][0];
            int y2 = a[kk][1];

            double s = (x0*y1+
                    x1*y2+
                    x2*y0
                    -y0*x1
                    -y1*x2
                    -y2*x0) / 2.0;

            int[] ba = new int[2];
            ba[0] = a[ii][0]-a[jj][0];
            ba[1] = a[ii][1]-a[jj][1];
            int[] bc = new int[2];
            bc[0] = a[kk][0]-a[jj][0];
            bc[1] = a[kk][1]-a[jj][1];
            int babc = ba[0] * bc[0] + ba[1] * bc[1];
            int ban = (ba[0] * ba[0]) + (ba[1] * ba[1]);
            int bcn = (bc[0] * bc[0]) + (bc[1] * bc[1]);
            double radian = Math.acos(babc / (Math.sqrt(ban * bcn)));
            double angle = radian * 180 / Math.PI;  // 結果（ラジアンから角度に変換）
            if (s > 0) {
                angle = 360 - angle;
            }
            //System.out.println(babc);
            //System.out.println(radian+" "+angle);
            if (angle >= 180) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
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
