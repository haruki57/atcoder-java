import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC363D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextLong();

        if (N<=10) {
            System.out.println(N-1);
            return;
        }

        long[] cnts = new long[100];
        cnts[1]=9;

        for (int i = 11; i < 100000; i++) {
            if(isKaibun(i)) {
                cnts[String.valueOf(i).length()]++;
            }
        }
        //System.out.println(Arrays.toString(cnts));

        for (int i = 1; i < cnts.length; i++) {
            int ii = (i+1)/2-1;
            cnts[i]=(long)Math.pow(10, ii)*9;
        }
        //System.out.println(Arrays.toString(cnts));
        N--;
        for (int i = 1; i < cnts.length; i++) {
            N -= cnts[i];
            if (N <= 0) {
                N += cnts[i];

                //System.out.println((cnts[i] + cnts[i]/9) +" "+ (N-1));
                StringBuilder sb = new StringBuilder(String.valueOf((cnts[i] + cnts[i]/9)/10 + (N-1)));
                int len = sb.length();
                if (i%2==1) {
                    len--;
                }
                for (int j = len-1; j >= 0; j--) {
                    sb.append(sb.charAt(j));
                }
                System.out.println(sb);

                //System.out.println(i);
                return;
            }
        }
    }

    static boolean isKaibun(long l) {
        char[] s = String.valueOf(l).toCharArray();
        for (int i = 0; i < s.length/2; i++) {
            if(s[i]!=s[s.length-1-i]) {
                return false;
            }
        }
        return true;
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
