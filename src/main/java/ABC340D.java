import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ABC340D {
    static int MOD = 1000000007;
    static long INF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[][] costs = new int[N-1][3];
        for (int i = 0; i < costs.length; i++) {
            costs[i][0] = scanner.nextInt();
            costs[i][1] = scanner.nextInt();
            costs[i][2] = scanner.nextInt()-1;
        }
        PriorityQueue<long[]> q = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o1[1], o2[1]);
            }
        });

        long[] shortest = new long[N+9];
        Arrays.fill(shortest, INF);
        shortest[0] = 0;
        q.add(new long[]{0, 0});
        while(!q.isEmpty()) {
            int cur = (int)q.peek()[0];
            long curCost = q.poll()[1];
            int next = cur + 1;
            if (cur>=N-1) {
                break;
            }
            long nextCost = curCost + costs[cur][0];
            if (shortest[next] > nextCost) {
                shortest[next] = nextCost;
                q.add(new long[]{next, nextCost});
            }
            next = costs[cur][2];
            nextCost = curCost + costs[cur][1];
            if (shortest[next] > nextCost) {
                shortest[next] = nextCost;
                q.add(new long[]{next, nextCost});
            }
        }
        //System.out.println(Arrays.toString(shortest));
        System.out.println(shortest[N-1]);
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
