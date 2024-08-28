package tessokubook;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class B17 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] h = new int[N];
        Arrays.setAll(h, i -> scanner.nextInt());
        if(N==2) {
            System.out.println(2);
            System.out.println("1 2");
            return;
        }
        long[] dp = new long[h.length];
        int[] prev = new int[h.length];
        dp[1]=Math.abs(h[0]-h[1]);
        for (int i = 2; i < dp.length; i++) {
            long prev1 = dp[i - 1] + Math.abs(h[i] - h[i - 1]);
            long prev2 = dp[i - 2] + Math.abs(h[i] - h[i - 2]);
            if(prev2 > prev1) {
                dp[i]= prev1;
                prev[i]=i-1;
            } else {
                dp[i]= prev2;
                prev[i]=i-2;
            }
        }
        //System.out.println(Arrays.toString(dp));
        List<Integer> path = new ArrayList<>();
        int cur = N-1;
        path.add(cur);
        while(cur > 0) {
            cur = prev[cur];
            path.add(cur);
        }
        out.println(path.size());
        for (int i = 0; i < path.size(); i++) {
            out.print((path.get(path.size()-1-i)+1)+" ");
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
