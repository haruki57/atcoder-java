import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ABC325E {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        int C = scanner.nextInt();
        long[][] d = new long[N][N];
        long[][][] carOrTrain = new long[2][N][N];



        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                d[i][j]= scanner.nextInt();
                carOrTrain[0][i][j]=d[i][j]*A;
                carOrTrain[1][i][j]=d[i][j]*B+C;
            }
        }
        long[][] shortest = new long[2][N];
        for (int i = 0; i < shortest.length; i++) {
            for (int j = 0; j < shortest[i].length; j++) {
                Arrays.fill(shortest[i], INF);
            }
        }
        PriorityQueue<long[]> q = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));

        q.add(new long[]{0, 0, 0});
        while(!q.isEmpty()) {
            long[] poll = q.poll();
            long dist = poll[0];
            int cur = (int)poll[1];
            int vehicle = (int)poll[2];
            if(shortest[vehicle][cur] < dist) {
                continue;
            }
            if(vehicle==0 && shortest[1][cur] > dist) {
                shortest[1][cur] = dist;
                q.add(new long[]{dist, cur, 1});
            }

            for (int next = 0; next < N; next++) {
                if(next==cur) {
                    continue;
                }
                long ndist = dist + carOrTrain[vehicle][cur][next];
                if(shortest[vehicle][next] <= ndist) {
                    continue;
                }
                shortest[vehicle][next] = ndist;
                q.add(new long[]{ndist, next, vehicle});
            }
        }
        System.out.println(Math.min(shortest[0][N-1], shortest[1][N-1]));
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
