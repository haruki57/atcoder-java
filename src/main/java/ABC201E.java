import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC201E {
    static int MOD = 1000000000 + 7;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        List<long[]>[]g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int u = scanner.nextInt()-1;
            int v = scanner.nextInt()-1;
            long w = scanner.nextLong();
            g[u].add(new long[]{v, w});
            g[v].add(new long[]{u, w});
        }
        int root = 0;
        long ans = 0;
        long[] dist = new long[N];
        Arrays.fill(dist, -1);
        Queue<long[]> q = new LinkedList<>();
        q.add(new long[]{root, 0});
        dist[0]=0;
        while(!q.isEmpty()){
            long[] poll = q.poll();
            int cur = (int)poll[0];
            long curW = poll[1];
            for (long[] nexts : g[cur]) {
                int next = (int)nexts[0];
                if (dist[next]!=-1) {
                    continue;
                }
                dist[next]=curW ^ nexts[1];
                q.add(new long[]{next, dist[next]});
            }
        }
        for (int bit = 0; bit < 61; bit++) {
            long cnt = 0;
            for (int i = 0; i < dist.length; i++) {
                int x = (int)(dist[i]>>bit)&1;
                if (x==1) {
                    cnt++;
                }
            }
            ans += (cnt*(N-cnt)%MOD)*((1L<<bit)%MOD);
            ans %= MOD;
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