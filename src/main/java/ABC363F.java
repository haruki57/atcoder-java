import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ABC363F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long N = scanner.nextLong();

        var ans = rec(N, new StringBuilder(), 1000000);
        if (ans==null){
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }

    private static String rec(long n, StringBuilder sb, long prev) {
        //System.out.println("n:"+n);
        //System.out.println("sb:"+sb);

        String nStr = String.valueOf(n);
        boolean contain0 = false;
        for (int i = 0; i < nStr.length(); i++) {
            if (nStr.charAt(i)=='0'){
                contain0=true;
            }
        }
        if (sb.length() + String.valueOf(n).length() <= 1000 && !contain0 && isKaibun(n)) {
            //System.out.println(sb);
            sb.insert(sb.length()/2, String.valueOf(n));
            return sb.toString();
        }
        l:for (long i = prev; i >= 2 ; i--) {
            String revStr = new StringBuilder(""+i).reverse().toString();
            for (int j = 0; j < revStr.length(); j++) {
                if (revStr.charAt(j)=='0'){
                    continue l;
                }
            }
            long j = Long.parseLong(revStr);
            //System.out.println(i+" "+j+" "+i*j);
            if (n >= i*j && n % (i*j) == 0) {
                //System.out.println(i+" "+j);
                sb.insert(0, i + "*");
                sb.append("*"+j);
                String ret = rec(n/(i*j), sb, i);
                if (ret !=null){
                    return ret;
                }
                sb.delete(sb.length()-revStr.length()-1, sb.length());
                sb.delete(0, revStr.length()+1);
            }
        }
        return null;
    }

    static boolean isKaibun(long a) {
        String s = ""+a;
        for (int i = 0; i <= s.length()/2; i++) {
            if (s.charAt(i) != s.charAt(s.length()-i-1)) {
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
