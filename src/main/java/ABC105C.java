import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC105C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        long[] maxs = new long[50];
        long[] mins = new long[50];
        maxs[0]=1;
        mins[0]=1;
        long max = 1;
        long min = 0;
        //System.out.println(1+" "+0+" "+1);
        for (int i = 1; i < maxs.length; i++) {
            long pow = 1L<<i;
            if(i%2==1) {
                pow = pow * -1;
            }
            maxs[i]=pow+max;
            mins[i]=pow+min;
            max = Math.max(max, maxs[i]);
            min = Math.min(min, mins[i]);
            //System.out.println((i+1)+" "+mins[i]+" "+maxs[i]);
        }

        //System.out.println();
        for (int i = 0; i < 1000; i++) {
            //System.out.println(Integer.toBinaryString(i)+" "+baseMinus2(i));
        }
        long N = scanner.nextInt();
        if(N==0) {
            System.out.println(0);
            return;
        }
        var sb = new StringBuilder();
        for (int i = maxs.length-1; i >= 0; i--) {
            if(mins[i]<=N && N <= maxs[i]) {
                long pow = 1L<<i;
                if(i%2==1) {
                    pow *= -1;
                }
                N-=pow;
                sb.append(1);
            } else {
                sb.append(0);
            }
            /*
            System.out.println(sb);
            System.out.println(N);
            System.out.println();
             */
        }
        while(sb.charAt(0)=='0') {
            sb.delete(0, 1);
        }
        System.out.println(sb);
        //System.out.println(baseMinus2((sb.reverse().toString())));
    }

    static long baseMinus2(String bit) {
        long ret = 0;
        for (int i = 0; i < bit.length(); i++) {
            if (bit.charAt(i)=='0') {
                continue;
            }
            if(i%2==0) {
                ret += 1L<<i;
            } else {
                ret -= 1L<<i;
            }
        }
        return ret;
    }

    static long baseMinus2(long bit) {
        long ret = 0;
        for (int i = 0; i < 64; i++) {
            if((bit&(1L<<i))==0)  {
                continue;
            }
            if(i%2==0) {
                ret += 1L<<i;
            } else {
                ret -= 1L<<i;
            }
        }
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
