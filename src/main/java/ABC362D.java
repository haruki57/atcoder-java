import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC362D {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE / 2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        List<int[]>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt()-1;
            int v = scanner.nextInt()-1;
            int b = scanner.nextInt();
            g[u].add(new int[]{v, b});
            g[v].add(new int[]{u, b});
        }
        long[] shortest = new long[N];
        PriorityQueue<C> q = new PriorityQueue<>(new Comparator<C>() {
            @Override
            public int compare(C o1, C o2) {
                return Long.compare(o1.w, o2.w);
            }
        });
        Arrays.fill(shortest, INF);
        q.add(new C(0, a[0]));
        while(!q.isEmpty()) {
            C poll = q.poll();
            int cur = poll.cur;
            long w = poll.w;
            if (shortest[cur] < w) {
                //continue;
            }
            for (int[] nexts : g[cur]) {
                int next = nexts[0];
                long nw = (long)nexts[1] + w + (long)a[next];
                if (shortest[next] <= nw) {
                    continue;
                }
                shortest[next] = nw;

                q.add(new C(next, nw));
            }
        }
        for (int i = 1; i < shortest.length; i++) {
            out.print(shortest[i]);
            out.print(" ");
        }
        out.println();
    }

    static class C {
        int cur;
        long w;
        public C(int a,long b){
            cur =a;
            w = b;
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
