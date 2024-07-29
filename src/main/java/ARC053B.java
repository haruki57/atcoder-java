import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ARC053B {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        int[] cnts = new int[26];
        for (char c : s) {
            cnts[c-'a']++;
        }
        int ok = 1, ng = s.length+1;
        if (makePalin(cnts.clone(), s.length)) {
            System.out.println(s.length);
            return;
        }

        while(Math.abs(ok-ng) >1) {
            int mid = (ok+ng)/2;
            if (ok(cnts.clone(), mid)) {
                ok = mid;
            } else{
                ng = mid;
            }
        }
        System.out.println(ok);
    }

    private static boolean ok(int[] cnts, int mid) {
        int sum = 0;
        for (int i = 0; i < cnts.length; i++) {
            sum+=cnts[i];
        }
        //System.out.println(mid);
        //System.out.println(Arrays.toString(cnts));
        if(!makePalin(cnts, mid)) {
            return false;
        }
        //System.out.println(Arrays.toString(cnts));
        sum-=mid;
        if (sum < mid) {
            return false;
        }
        while(true) {
            if (sum-mid < mid) {
                if(!makePalin(cnts, sum)) {
                    return false;
                } else {
                    return true;
                }
            }
            if(!makePalin(cnts, mid)) {
                return false;
            }
            sum -= mid;
        }
    }

    static private boolean makePalin(int[] cnts, int len) {
        if (len%2==0) {
            for (int i = 0; i < cnts.length; i++) {
                while(len > 0 && cnts[i]>=2) {
                    cnts[i]-=2;
                    len-=2;
                }
            }
            return len==0;
        }
        for (int i = 0; i < cnts.length; i++) {
            while(len > 1 && cnts[i]>=2) {
                cnts[i]-=2;
                len-=2;
            }
        }
        if (len!=1) {
            return false;
        }
        // use character from odd num
        for (int i = 0; i < cnts.length; i++) {
            if(cnts[i]%2==1&&cnts[i]>=1) {
                cnts[i]--;
                return true;
            }
        }
        // use character from even num
        for (int i = 0; i < cnts.length; i++) {
            if(cnts[i]>=1) {
                cnts[i]--;
                return true;
            }
        }
        return false;
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
