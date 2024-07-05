import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC181D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        int[] a = new int[s.length];
        int[] cnts = new int[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int)(s[i]-'0');
            cnts[a[i]]++;
        }
        if (s.length==1) {
            if(cnts[8]>=1) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
            return;
        }
        if (s.length==2) {
            if (Integer.parseInt(new String(s))%8==0) {
                System.out.println("Yes");
                return;
            }
            char tmp = s[0];
            s[0]=s[1];
            s[1]=tmp;
            if (Integer.parseInt(new String(s))%8==0) {
                System.out.println("Yes");
                return;
            }
            System.out.println("No");
            return;

        }
        for (int i = 100; i < 1000; i++) {
            if (i%8!=0) {
                continue;
            }
            if (String.valueOf(i).contains("0")) {
                continue;
            }
            int[] subCnts = new int[10];
            subCnts[i%10]++;
            subCnts[i/10%10]++;
            subCnts[i/100]++;
            boolean allOk = true;
            for (int j = 1; j < cnts.length; j++) {
                if (subCnts[j] > cnts[j]) {
                    allOk=false;
                }
            }
            if (allOk) {
                System.out.println("Yes");
                return;
            }

            //System.out.println(i);
            //System.out.println(Arrays.toString(subCnts));
        }
        System.out.println("No");
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
