import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC362C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[][] a = new long[N][2];
        for (int i = 0; i < a.length; i++) {
            a[i][0]= scanner.nextInt();
            a[i][1]= scanner.nextInt();
        }
        long[] x = new long[N];
        long sum = 0;
        long plus = 0;
        for (int i = 0; i < x.length; i++) {
            x[i]=a[i][0];
            sum += x[i];
            plus += a[i][1]-a[i][0];
        }
        //System.out.println(sum +" "+plus);
        if (sum > 0) {
            System.out.println("No");
            return;
        }
        if (sum == 0){
            out.println("Yes");
            for (int i = 0; i < x.length; i++) {
                out.print(x[i]+ " ");
            }
            return;
        }

        // sum is negative

        sum *= -1;
        if (sum > plus) {
            System.out.println("No");
            return;
        }

        //System.out.println(Arrays.toString(x));
        for (int i = 0; i < x.length; i++) {
            long diff = a[i][1]-a[i][0];
            if (diff < sum) {
                x[i] += diff;
                sum -= diff;
            } else {
                x[i] += sum;
                break;
            }
        }

        out.println("Yes");
        long checkSum = 0;
        for (int i = 0; i < x.length; i++) {
            out.print(x[i]+ " ");
            checkSum+=x[i];
        }
        out.println();
        //out.println(checkSum);
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
