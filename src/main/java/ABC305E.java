import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.*;

public class ABC305E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();
        List<Integer>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            graph[a].add(b);
            graph[b].add(a);
        }
        int[][] p = new int[K][2];
        for (int i = 0; i < K; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt();
            p[i][0]=a;
            p[i][1]=b;
        }
        int[] visited = new int[N];
        Arrays.fill(visited, -1);
        //Arrays.sort(p, (o1, o2) -> o2[1]-o1[1]);
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o2[1]-o1[1]);
        for (int i = 0; i < p.length; i++) {
            int start = p[i][0];
            int h = p[i][1];
            visited[start] = h;
            q.add(new int[]{start, h});
        }
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int cur = polled[0];
            int curH = polled[1];
            if (curH < visited[cur]) {
                continue;
            }
            for (Integer next : graph[cur]) {
                if (visited[next] >= curH-1) {
                    continue;
                }
                visited[next]=curH-1;
                if (curH-1<=0) {
                    continue;
                }
                q.add(new int[]{next, curH-1});
            }
        }
        int cnt = 0;
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]>=0) cnt++;
        }
        out.println(cnt);
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]>=0)
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
