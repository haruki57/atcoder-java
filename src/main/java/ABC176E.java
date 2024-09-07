import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC176E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int M = scanner.nextInt();
        Set<Integer>[] hsets = new Set[H];
        for (int i = 0; i < hsets.length; i++) {
            hsets[i]=new HashSet<>();
        }
        Set<Integer>[] wsets = new Set[W];
        for (int i = 0; i < wsets.length; i++) {
            wsets[i]=new HashSet<>();
        }
        Set<Long> hwset = new HashSet<>();
        for (int i = 0; i < M; i++) {
            int h = scanner.nextInt()-1;
            int w = scanner.nextInt()-1;
            hsets[h].add(w);
            wsets[w].add(h);
            hwset.add((long)h*Integer.MAX_VALUE + w);
        }
        int[][] cnth = new int[H][2];
        for (int i = 0; i < H; i++) {
            cnth[i][0]=hsets[i].size();
            cnth[i][1]=i;
        }
        int[][] cntw = new int[W][2];
        for (int i = 0; i < W; i++) {
            cntw[i][0]=wsets[i].size();
            cntw[i][1]=i;
        }
        Arrays.sort(cnth, (o1, o2) -> o2[0]-o1[0]);
        Arrays.sort(cntw, (o1, o2) -> o2[0]-o1[0]);
        int ans = 0;
        for (int i = 0; i < H; i++) {
            if(cnth[i][0]==0) {
                break;
            }
            for (int j = 0; j < W; j++) {
                if(cntw[j][0]==0) {
                    break;
                }
                int sum = cnth[i][0]+cntw[j][0];
                if(sum <= ans) {
                    break;
                }
                int y = cnth[i][1];
                int x = cntw[j][1];
                if (hwset.contains((long)y*Integer.MAX_VALUE + x)) {
                    sum--;
                }
                ans = Math.max(ans, sum);
            }
        }
        System.out.println(ans);
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
