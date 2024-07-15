import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC304D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int W = scanner.nextInt();
        int H = scanner.nextInt();
        int N = scanner.nextInt();
        int[][] pq = new int[N][2];
        for (int i = 0; i < N; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            pq[i][0]=x;
            pq[i][1]=y;
        }
        TreeSet<Integer> xSet = new TreeSet<>();
        xSet.add(W);
        TreeSet<Integer> ySet = new TreeSet<>();
        ySet.add(H);
        int A = scanner.nextInt();


        for (int i = 0; i < A; i++) {
            xSet.add(scanner.nextInt());
        }
        int B = scanner.nextInt();

        for (int i = 0; i < B; i++) {
            ySet.add(scanner.nextInt());
        }

        Map<Integer, Map<Integer, Integer>> counts = new HashMap<>();
        long max = 0;
        for (int[] ints : pq) {
            int x = ints[0];
            int y = ints[1];
            int ceilX = xSet.ceiling(x);
            int ceilY = ySet.ceiling(y);
            var subCounts = counts.getOrDefault(ceilX, new HashMap<>());
            int cnt = subCounts.getOrDefault(ceilY, 0);
            counts.put(ceilX, subCounts);
            subCounts.put(ceilY, cnt+1);
            max= Math.max(max, cnt+1);
        }
        long min = INF;
        long valuesTotal = 0;
        for (Map<Integer, Integer> value : counts.values()) {
            for (Integer i : value.values()) {
                min = Math.min(min, i);
                valuesTotal++;
            }
        }
        if (valuesTotal < (A+1L)*(B+1L)) {
            min = 0;
        }
        System.out.println(min+" "+max);
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
