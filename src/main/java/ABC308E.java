import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ABC308E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        char[] s = scanner.next().toCharArray();
        int[] m0sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            m0sum[i+1]=m0sum[i]+(s[i]=='M'&&a[i]==0?1:0);
        }
        int[] m1sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            m1sum[i+1]=m1sum[i]+(s[i]=='M'&&a[i]==1?1:0);
        }
        int[] m2sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            m2sum[i+1]=m2sum[i]+(s[i]=='M'&&a[i]==2?1:0);
        }

        int[] x0sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            x0sum[i+1]=x0sum[i]+(s[i]=='X'&&a[i]==0?1:0);
        }
        int[] x1sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            x1sum[i+1]=x1sum[i]+(s[i]=='X'&&a[i]==1?1:0);
        }
        int[] x2sum = new int[N+1];
        for (int i = 0; i < a.length; i++) {
            x2sum[i+1]=x2sum[i]+(s[i]=='X'&&a[i]==2?1:0);
        }

        long ans = 0;
        int r = x0sum.length-1;
        for (int j = 1; j < s.length-1; j++) {
            if(s[j]=='E') {
                long cases = (long)m0sum[j] * (x0sum[r]-x0sum[j+1]);
                int mex = mex(0, a[j], 0);
                ans += cases * mex;

                cases = (long)m0sum[j] * (x1sum[r]-x1sum[j+1]);
                mex = mex(0, a[j], 1);
                ans += cases * mex;

                cases = (long)m0sum[j] * (x2sum[r]-x2sum[j+1]);
                mex = mex(0, a[j], 2);
                ans += cases * mex;

                cases = (long)m1sum[j] * (x0sum[r]-x0sum[j+1]);
                mex = mex(1, a[j], 0);
                ans += cases * mex;

                cases = (long)m1sum[j] * (x1sum[r]-x1sum[j+1]);
                mex = mex(1, a[j], 1);
                ans += cases * mex;

                cases = (long)m1sum[j] * (x2sum[r]-x2sum[j+1]);
                mex = mex(1, a[j], 2);
                ans += cases * mex;

                cases = (long)m2sum[j] * (x0sum[r]-x0sum[j+1]);
                mex = mex(2, a[j], 0);
                ans += cases * mex;

                cases = (long)m2sum[j] * (x1sum[r]-x1sum[j+1]);
                mex = mex(2, a[j], 1);
                ans += cases * mex;

                cases = (long)m2sum[j] * (x2sum[r]-x2sum[j+1]);
                mex = mex(2, a[j], 2);
                ans += cases * mex;
            }
        }
        System.out.println(ans);
    }

    static int mex(int a,int b,int c) {
        Set<Integer> set = new HashSet<>();
        set.add(a);
        set.add(b);
        set.add(c);
        for (int i = 0; i < 100; i++) {
            if(!set.contains(i)) {
                return i;
            }
        }
        return 0;
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
