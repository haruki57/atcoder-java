import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ABC160E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        int toEat = X+Y;
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        int C = scanner.nextInt();
        int[] a = new int[A];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[B];
        Arrays.setAll(b, i -> scanner.nextInt());
        int[] c = new int[C];
        Arrays.setAll(c, i -> scanner.nextInt());

        int[][] apples = new int[A+B+C][2];
        int ii = 0;
        for (int i = 0; i < a.length; i++) {
            apples[ii][0]=a[i];
            apples[ii][1]=0;
            ii++;
        }
        for (int i = 0; i < b.length; i++) {
            apples[ii][0]=b[i];
            apples[ii][1]=1;
            ii++;
        }
        for (int i = 0; i < c.length; i++) {
            apples[ii][0]=c[i];
            apples[ii][1]=2;
            ii++;
        }
        Arrays.sort(apples, (o1, o2) -> o2[0]-o1[0]);
        long sum = 0;
        int eatCnt = 0;
        for (int i = 0; i < apples.length; i++) {
            if(apples[i][1]==0 && X > 0) {
                X--;
                sum += apples[i][0];
            } else if(apples[i][1]==1 && Y > 0) {
                Y--;
                sum += apples[i][0];
            } else if (apples[i][1]==2){
                sum += apples[i][0];
            } else {
                eatCnt--;
            }
            eatCnt++;
            //System.out.println(sum+" "+eatCnt);
            if(eatCnt==toEat) {
                break;
            }

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
