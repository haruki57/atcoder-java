package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class _058 {
    static int MOD = 100000;
    static int INF = Integer.MAX_VALUE/2;

    static int next(int n) {
        int digitSum = 0;
        String s = String.valueOf(n);
        for (int i = 0; i < s.length(); i++) {
            digitSum += s.charAt(i) - '0';
        }
        return (n+digitSum) % MOD;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int n = scanner.nextInt();
        int start = n;
        int loopStart = -1;
        int loopEnd = -1;
        long K = scanner.nextLong();
        if (n==0) {
            System.out.println(0);
            return;
        }
        int[] step = new int[MOD + 100];
        Arrays.fill(step, -1);
        step[n] = 0;
        for (int i = 0; i < K; i++) {
            int prevN = n;
            n = next(n);
            if (n==0) {
                System.out.println(0);
                return;
            }
            if (step[n] != -1) {
                loopStart = n;
                loopEnd = prevN;
                break;
            }
            step[n] = step[prevN] + 1;
            // System.out.println(n);
        }
        if (loopStart == -1) {
            System.out.println(n);
            return;
        }
        System.out.println(start + " "+ loopStart + " "+ loopEnd);
        System.out.println(step[start] + " "+ step[loopStart] + " "+ step[loopEnd]);
        K = (K-step[loopStart]) % (step[loopEnd]-step[loopStart]) + step[loopStart];

        n = start;
        for (int i = 0; i < K; i++) {
            int prevN = n;
            n = next(n);
            step[n] = step[prevN] + 1;
            // System.out.println(n);
        }
        System.out.println(n);

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
