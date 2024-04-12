package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class A72 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int K = scanner.nextInt();
        char[][] tile = new char[H][W];
        for (int i = 0; i < H; i++) {
            tile[i] = scanner.next().toCharArray();
        }
        int ans = 0;
        for (int bit = 0; bit < 1<<H; bit++) {
            if (Integer.bitCount(bit) > K) {
                continue;
            }
            char[][] copy = new char[H][W];
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    copy[i][j] = tile[i][j];
                }
            }
            int len = Integer.toBinaryString(bit).length();
            for (int i = 0; i < len; i++) {
                if ((bit & (1<<i)) > 0) {
                    for (int j = 0; j < W; j++) {
                        copy[i][j] = '#';
                    }
                }
            }
            int blackNum = 0;
            int sumW[] = new int[W];
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (copy[i][j] == '.') {
                        sumW[j]++;
                    } else {
                        blackNum++;
                    }
                }
            }
            Arrays.sort(sumW);
            for (int i = 0; i <K-Integer.bitCount(bit); i++) {
                blackNum += sumW[W-1-i];
            }
            ans = Math.max(ans ,blackNum);
        }
        System.out.println(ans);
    }

    public static void main(final String[] args) {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner scanner = new FastScanner();
        run(scanner, out);
        out.flush();
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
