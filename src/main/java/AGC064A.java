import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class AGC064A {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] ans = new int[(N*(N+1))/2];
        int idx = 0;
        for (int i = 1; i <= N-2; i++) {
            ans[idx++]=i;
        }
        ans[idx++]=N;
        ans[idx++]=N-1;

        for (int n = N; n>=0; n-=2) {
            for (int i = 0; i < n - 2; i++) {
                ans[idx++]=n;
                if(idx==ans.length) {
                    break;
                }
                ans[idx++]=n-1;
                if(idx==ans.length) {
                    break;
                }
            }
            if(idx==ans.length) {
                break;
            }
            ans[idx++]=n;
        }
        for (int an : ans) {
            out.print(an+" ");
        }
        out.println();


        /*
        for (int i = 0; ; i++) {
            if(ans[i+1]!=0) {
                break;
            }
            int cur = ans[i];
            int val = -1;
            for (int j = cnts.length-1; j >=1; j--) {
                if(j!=cur&&cnts[j]>0&&Math.abs(j-cur)<=2) {
                    val=j;
                    break;
                }
            }
            ans[i+1]=val;
            cnts[val]--;

            int ii = ((NN-i-1)+NN)%NN;
            System.out.println(ii);
            if(ans[ii]!=0) {
                break;
            }
            for (int j = cnts.length-1; j >=1; j--) {
                if(j!=cur&&cnts[j]>0&&Math.abs(j-cur)<=2) {
                    val=j;
                    break;
                }
            }
            ans[ii]=val;
            cnts[val]--;
            System.out.println(Arrays.toString(ans));
        }
        for (int an : ans) {
            out.print(an+" ");
        }
        out.println();

         */
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
