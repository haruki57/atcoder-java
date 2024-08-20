package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _058_2 {
    static int MOD = 100000;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long K = scanner.nextLong();
        if(N==0) {
            System.out.println(0);
            return;
        }
        int[] visited = new int[MOD+1];
        visited[N]=1;
        int cnt = 1;
        long lastI = -1;
        int start = -1;
        long loopSize = -1;
        int prevN=-1;
        for (long i = 0; i < K; i++) {
            prevN=N;
            N = next(N);
            //System.out.println(i+" "+N);
            if(visited[N]>0) {
                lastI = i;
                start = N;
                loopSize = visited[prevN]-visited[N]+1;
                break;
            }
            visited[N]=cnt++;
        }
        if(lastI == -1) {
            System.out.println(N);
            return;
        }
        K = K - (lastI+1) + loopSize * 10000;
        //System.out.println(N+" "+prevN+" "+lastI+" "+start+" "+loopSize+" "+visited[prevN]+" "+visited[N]);
        K %= loopSize;
        for (int i = 0; i < K; i++) {
            N = next(N);
        }
        System.out.println(N);

    }

    static int next(int n) {
        int on = n;
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += n%10;
            n/=10;
        }
        return (on + sum) % MOD;
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
