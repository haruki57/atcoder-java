package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _046 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static long[] memo1 = new long[100];
    static long[] memo2 = new long[100];
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        Arrays.fill(memo1, -1);
        Arrays.fill(memo2, -1);
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = new int[N];
        Arrays.setAll(b, i -> scanner.nextInt());
        int[] c = new int[N];
        Arrays.setAll(c, i -> scanner.nextInt());
        for (int i = 0; i < N; i++) {
            for(int[] array: new int[][]{a,b,c}) {
                array[i] = array[i]%46;
            }
        }
        long cnt = 0;
        for (int i = 0; i < N; i++) {
            cnt += f1(a[i], b, c);
        }
        System.out.println(cnt);
    }

    private static long f1(int i, int[] b, int[] c) {
        if (memo1[i] != -1) {
            return memo1[i];
        }
        long ret = 0;
        for (int j = 0; j < b.length; j++) {
            ret += f2(i+b[j],c);
        }
        return memo1[i] = ret;
    }


    private static long f2(int i, int[] c) {
        if (memo2[i] != -1) {
            return memo2[i];
        }
        long ret = 0;
        for (int j = 0; j < c.length; j++) {
            if ((i + c[j]) % 46 == 0) {
                ret++;
            }
        }
        return memo2[i] = ret;
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
