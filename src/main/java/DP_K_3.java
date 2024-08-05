import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class DP_K_3 {
    static int MOD = (int)Math.pow(10, 9)+ 7;
    static int INF = Integer.MAX_VALUE/2;

    static int N, K;
    static int[] a;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        K = scanner.nextInt();
        a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        boolean[] win = new boolean[K+1];
        //win[0]=true;
        for (int i = 1; i <=K; i++) {
            int winCnt = 0;
            int loseCnt = 0;
            for (int j = 0; j < N; j++) {
                int ii = i - a[j];
                if(ii<0) {
                    //loseCnt++;
                    continue;
                }
                if(win[ii]) {
                    winCnt++;
                } else {
                    loseCnt++;
                }
            }
            if(winCnt+loseCnt==0) {
                win[i]=false;
            } else {
                if(loseCnt > 0) {
                    win[i]=true;
                } else {
                    win[i]=false;
                }
            }
        }
        //System.out.println(Arrays.toString(win));
        if(win[K]) {
            System.out.println("First");
        } else {
            System.out.println("Second");
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
