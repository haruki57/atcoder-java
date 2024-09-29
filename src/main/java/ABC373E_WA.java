import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC373E_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        long K = scanner.nextLong();
        long sum = 0;
        long[][] a = new long[N][2];
        for (int i = 0; i < a.length; i++) {
            a[i][0]= scanner.nextLong();
            a[i][1]=i;
        }
        if(N==M) {
            for (int i = 0; i < N; i++) {
                out.print("0 ");
            }
            out.println();
            return;
        }
        Arrays.sort(a, (o1, o2) -> Long.compare(o1[0], o2[0]));
        for (int i = 0; i < a.length; i++) {
            sum += a[i][0];
        }
        long rest = K -sum;
        long[] ans = new long[N];
        for (int i = 0; i < N; i++) {
            long rival = a[N-M][0];
            if(i >= N-M) {
                rival = a[N-M-1][0];
            }
            if(a[i][0]+rest < rival) {
                ans[(int)a[i][1]] = -1;
                continue;
            }
            if(a[i][0] >= rival + rest) {
                ans[(int)a[i][1]] = 0;
                continue;
            }
            //System.out.println(a[i][0]+" "+rival);
            long ok = Long.MAX_VALUE / 4;
            long ng = Long.MIN_VALUE / 4;
            while(Math.abs(ok-ng) > 1) {
                long mid = (ok + ng) / 2;
                long my = a[i][0]+mid;
                long rival2 = rival + (rest-mid);
                //System.out.println(my+" "+rival2+" "+mid+" "+(my >= rival2));
                if(my >= rival2) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            if(ok<0) {
                ok=-1;
            }
            if(ok > rest) {
                ok = rest;
            }
            //System.out.println(ng+" "+ok);
            //System.out.println();
            ans[(int)a[i][1]] = ok;
        }
        for (long an : ans) {
            out.print(an+" ");
        }
        out.println();
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
