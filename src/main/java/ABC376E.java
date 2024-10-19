import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC376E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int T = scanner.nextInt();
        while(T-->0) {
            int N = scanner.nextInt();
            int K = scanner.nextInt();
            long[][] ab = new long[N][2];
            for (int i = 0; i < N; i++) {
                ab[i][0]= scanner.nextInt();
            }
            for (int i = 0; i < N; i++) {
                ab[i][1]= scanner.nextInt();
            }
            Arrays.sort(ab, Comparator.comparingLong(o -> o[0]));
            long sum = 0;
            long max = 0;
            long ans = Long.MAX_VALUE;
            var multiSet = new MultiSet();
            for (int i = 0; i < K; i++) {
                max=ab[i][0];
                sum += ab[i][1];
                multiSet.add(ab[i][1]);
            }
            ans = max*sum;
            for (int i = K; i < N; i++) {
                Map.Entry<Long, Integer> entry = multiSet.map.lastEntry();
                max = ab[i][0];
                Long val = entry.getKey();
                if(ab[i][1] > val) {
                    continue;
                }
                multiSet.remove(val);
                sum -= val;
                multiSet.add(ab[i][1]);
                sum += ab[i][1];
                ans = Math.min(ans, max*sum);
            }

            System.out.println(ans);
            /*
            if(ansTLE(N, K, ab)!=ans) {
                System.out.println(N+" "+K);
                for (int i = 0; i < ab.length; i++) {
                    System.out.println(Arrays.toString(ab[i]));
                }
                return;
            }

             */




        }
    }

    static class MultiSet {
        // You can access map directly but don't mutate it.
        public TreeMap<Long, Integer> map;
        int uniqueSize = 0;
        int size = 0;
        long sum = 0;
        public MultiSet() {
            map = new TreeMap<>();
        }
        public void add(long x) {
            map.put(x, map.getOrDefault(x, 0)+1);
            sum += x;
            size++;
            uniqueSize = map.size();
        }
        public void remove(long x) {
            var nextPut = map.getOrDefault(x, 0)-1;
            if (nextPut == -1) {
                return;
            }
            if (nextPut == 0) {
                map.remove(x);
            } else {
                map.put(x, nextPut);
            }
            uniqueSize = map.size();
            size--;
            sum -= x;
        }
    }


    static long ansTLE(int N, int K, long[][] ab) {
        long ret = Long.MAX_VALUE;
        for (int bit = 0; bit < 1 << N; bit++) {
            if(Integer.bitCount(bit)!=K) {
                continue;
            }
            long max = 0;
            long sum = 0;
            for (int i = 0; i < N; i++) {
                if(((1<<i)&bit)==0){
                    continue;
                }
                max = Math.max(max, ab[i][0]);
                sum += ab[i][1];
            }
            ret = Math.min(ret, max*sum);
        }
        return ret;
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
