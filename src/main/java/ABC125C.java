import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC125C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        if(N==2) {
            System.out.println(Math.max(a[0], a[1]));
            return;
        }
        long[] gcds = new long[N];

        for (int i = 0; i < N; i++) {
            if(i==0) {
                gcds[i]=a[i];
            } else {
                gcds[i]=gcd(gcds[i-1], a[i]);
            }
        }
        long[] gcdsRe = new long[N];
        for (int i = N-1; i >= 0; i--) {
            if(i==N-1) {
                gcdsRe[i]=a[i];
            } else {
                gcdsRe[i]=gcd(gcdsRe[i+1], a[i]);
            }
        }

        //System.out.println(Arrays.toString(gcds));
        //System.out.println(Arrays.toString(gcdsRe));

        long ans = 1;
        for (int i = 1; i < N-1; i++) {
            long gcd = gcd(gcds[i-1], gcdsRe[i+1]);
            ans = Math.max(ans, gcd);
        }
        ans = Math.max(ans, gcds[N-2]);
        ans = Math.max(ans, gcdsRe[1]);
        System.out.println(ans);
        //ansTLE(N, a);
    }

    static long gcd(long x, long y) {
        if (x<y) {
            long tmp = x;
            x = y;
            y = tmp;
        }
        if (x%y==0) {
            return y;
        }

        return gcd(y, x % y);
    }


    private static void ansTLE(int N, int[] a) {
        Map<Long, Integer> cnts = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (Long l : yakusuu(a[i])) {
                cnts.put(l, cnts.getOrDefault(l, 0) + 1);
            }
        }
        long ans = 1;
        for (Map.Entry<Long, Integer> longIntegerEntry : cnts.entrySet()) {
            Long yakusuu = longIntegerEntry.getKey();
            int cnt = longIntegerEntry.getValue();
            if(cnt >= N -1) {
                ans = Math.max(ans, yakusuu);
            }
        }
        System.out.println(ans);
    }


    static ArrayList<Long> yakusuu(long N) {
        ArrayList<Long> ret = new ArrayList<>();
        for (long i = 1; i*i <= N ; i++) {
            if (N%i==0) {
                ret.add(i);
                if (N/i != i) {
                    ret.add(N/i);
                }
            }
        }
        Collections.sort(ret);
        return ret;
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
