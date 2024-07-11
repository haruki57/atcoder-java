import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC286E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        char[][] s = new char[N][];
        for (int i = 0; i < N; i++) {
            s[i]= scanner.next().toCharArray();
        }
        int Q = scanner.nextInt();

        long[][][] calc = new long[N][N][2];
        long[] steps = new long[N];
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new LinkedList<>();
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (s[i][j]=='Y') {
                    g[i].add(j);
                }
            }
        }
        for (int u = 0; u < N; u++) {
            Queue<long[]> q = new LinkedList<>();
            q.add(new long[]{u, a[u], 1});
            Arrays.fill(steps, INF);
            steps[u]=1;
            l:while(!q.isEmpty()) {
                long[] poll = q.poll();
                int cur = (int)poll[0];
                long sum = poll[1];
                long cnt = poll[2];
                for (Integer i : g[cur]) {
                    if (steps[i]<cnt+1) {
                        continue;
                    }
                    long nextSum = sum + a[i];
                    if (calc[u][i][0] >= nextSum ) {
                        continue;
                    }
                    steps[i]=cnt+1;
                    calc[u][i][0]=Math.max(calc[u][i][0], sum+a[i]);
                    calc[u][i][1]=cnt;
                    q.add(new long[]{i, sum+a[i], cnt+1});
                }
            }
        }
        for (int qi = 0; qi < Q; qi++) {
            int u = scanner.nextInt() - 1;
            int v = scanner.nextInt() - 1;
            if (calc[u][v][1]==0) {
                out.println("Impossible");
            } else {
                out.println(calc[u][v][1]+" "+calc[u][v][0]);
            }
        }
        /*
        for (int qi = 0; qi < Q; qi++) {
            int u = scanner.nextInt()-1;
            int v = scanner.nextInt()-1;
            PriorityQueue<long[]> q = new PriorityQueue<>((o1, o2) -> Long.compare(o2[1],o1[1]));
            q.add(new long[]{u, a[u], 1});
            boolean[] visited = new boolean[N];
            visited[u]=true;
            boolean f = false;
            l:while(!q.isEmpty()) {
                long[] poll = q.poll();
                int cur = (int)poll[0];
                long sum = poll[1];
                long cnt = poll[2];
                for (int i = 0; i < N; i++) {
                    if (s[cur][i]=='N') {
                        continue;
                    }
                    if (visited[i]){
                        continue;
                    }
                    if (i==v) {
                        out.println((cnt)+" "+(sum+a[i]));
                        f=true;
                        break l;
                    }

                    visited[i]=true;

                    q.add(new long[]{i, sum+a[i], cnt+1});
                }
            }
            if(!f) {
                out.println("Impossible");
            }
        }

         */

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
