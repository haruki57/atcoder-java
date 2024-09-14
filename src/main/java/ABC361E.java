import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC361E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        List<int[]>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        long total = 0;
        for (int i = 0; i < N - 1; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            int c = scanner.nextInt();
            g[x].add(new int[]{y, c});
            g[y].add(new int[]{x, c});
            total += c * 2L;
        }
        long chokkei = chokkei(g);
        System.out.println(total - chokkei);
    }

    private static long chokkei(List<int[]>[] g) {
        long[] rec = rec(g, 0, -1, 0L);
        rec = rec(g, (int)rec[0], -1, 0L);
        return rec[1];
    }

    private static long[] rec(List<int[]>[] g, int cur, int prev,long cost) {
        List<long[]> list = new ArrayList<>();
        for (int[] ints : g[cur]) {
            int next = ints[0];
            if(next==prev) {
                continue;
            }
            long ncost = cost + ints[1];
            list.add(rec(g, next, cur, ncost));
        }
        if(list.size()==0) {
            return new long[]{cur, cost};
        }
        Collections.sort(list, (o1, o2) -> Long.compare(o2[1], o1[1]));
        return list.get(0);
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
