import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC270C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int X = scanner.nextInt()-1;
        int Y = scanner.nextInt()-1;
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            g[a].add(b);
            g[b].add(a);
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{X, -1});
        int[] prevs = new int[N];
        Arrays.fill(prevs, -1);

        while(!q.isEmpty()) {
            int[] poll = q.poll();
            int cur = poll[0];
            if (cur==Y) {
                break;
            }
            int prev = poll[1];
            for (Integer next : g[cur]) {
                if (next==prev) {
                    continue;
                }
                prevs[next] = cur;
                q.add(new int[]{next, cur});
            }
        }
        int cur = 0;
        List<Integer> path = new ArrayList<>();

        //System.out.println(Arrays.toString(prevs));
        while(true) {
            path.add(Y);
            Y = prevs[Y];
            if (Y==X) {
                path.add(Y);
                break;
            }
        }
        Collections.reverse(path);
        for (Integer i : path) {
            out.print((i+1)+" ");
        }
        out.println();
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
