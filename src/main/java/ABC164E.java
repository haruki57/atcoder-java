import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC164E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int S = scanner.nextInt();
        S = Math.min(3000, S);
        List<int[]>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt()-1;
            int v = scanner.nextInt()-1;
            int cost = scanner.nextInt();
            int time = scanner.nextInt();
            graph[u].add(new int[]{v, cost, time});
            graph[v].add(new int[]{u, cost, time});
        }
        int[][] changes = new int[N][2];
        for (int i = 0; i < N; i++) {
            int c = scanner.nextInt();
            int d = scanner.nextInt();
            changes[i][0]=c;
            changes[i][1]=d;
        }
        PriorityQueue<long[]> q = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1],o2[1]));
        long[][] shortest = new long[N][3009];
        for (int i = 0; i < N; i++) {
            Arrays.fill(shortest[i], (long)INF*INF);
        }
        shortest[0][S] = 0;
        q.add(new long[]{0, 0, S});
        while(!q.isEmpty()) {
            long[] poll = q.poll();
            int cur = (int)poll[0];
            long time = poll[1];
            int coinNum = (int)poll[2];

            // change
            {
                int changeNum = changes[cur][0];
                int changeTime = changes[cur][1];
                long nextTime = time + changeTime;
                int nextCoinNum = Math.min(coinNum + changeNum, 3000);
                if (nextCoinNum < 3000) {
                    if(shortest[cur][nextCoinNum] > nextTime) {
                        shortest[cur][nextCoinNum] = nextTime;
                        q.add(new long[]{cur, nextTime, nextCoinNum});
                    }
                }
            }
            for (int[] arr : graph[cur]) {
                int next = arr[0];
                int ncost = arr[1];
                int ntime = arr[2];
                int nCoinNum = coinNum - ncost;
                if (nCoinNum < 0) {
                    continue;
                }
                long time2 = time + ntime;
                if (shortest[next][nCoinNum] > time2) {
                    shortest[next][nCoinNum] = time2;
                    q.add(new long[]{next, time2, nCoinNum});
                }
            }
        }
        for (int i = 1; i < N; i++) {
            long min = (long)INF*INF;
            for (int j = 0; j < shortest[i].length; j++) {
                min = Math.min(min, shortest[i][j]);
            }
            out.println(min);
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
