import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class AGC023A {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long[] sum = new long[N+1];
        for (int i = 1; i < sum.length; i++) {
            sum[i]=sum[i-1]+a[i-1];
        }
        var multiset = new MultiSet();
        for (int i = 0; i < sum.length; i++) {
            multiset.add(sum[i]);
        }
        long ans = 0;
        for (Map.Entry<Long, Integer> longIntegerEntry : multiset.map.entrySet()) {
            long v = longIntegerEntry.getValue();
            ans += v*(v-1)/2;
        }
        System.out.println(ans);
    }

    static long tle(int[] sum) {
        long ret = 0;
        for (int i = 0; i < sum.length; i++) {
            for (int j = i+1; j < sum.length; j++) {
                if(sum[j]-sum[i]==0) {
                    ret++;
                }
            }
        }
        return ret;
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
