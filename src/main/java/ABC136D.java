import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC136D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {

        char[] s = scanner.next().toCharArray();
        int N = s.length;
        int[] last = new int[s.length];
        for (int i = 0; i < s.length; ) {
            int oi = i;
            if (s[i]=='L') {
                i++;
                continue;
            }
            while(s[i]=='R'&&i<s.length) {
                i++;
            }
            for (int j = oi; j < i; j++) {
                if ((i-j)%2==0) {
                    last[j]=i;
                } else {
                    last[j]=i-1;
                }
            }
        }
        //System.out.println(Arrays.toString(last));

        for (int i = N-1; i >=0; ) {
            int oi = i;
            if (s[i]=='R') {
                i--;
                continue;
            }
            while(s[i]=='L'&&i>=0) {
                i--;
            }
            //System.out.println(oi+" "+i);
            for (int j = oi; j >= i; j--) {
                //System.out.println("hoge");
                if ((i-j)%2==0) {
                    last[j]=i;
                } else {
                    last[j]=i+1;
                }
            }
        }
        //System.out.println(Arrays.toString(last));
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[last[i]]++;
        }
        for (int i = 0; i < N; i++) {
            out.print(ans[i]+" ");
        }
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
