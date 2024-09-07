import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ABC287E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] bb = new long[500009];
        bb[0] = 1;
        for (int i = 1; i < bb.length; i++) {
            bb[i] = bb[i-1] * 100 % MOD;
        }
        var multiset = new MultiSet();
        String[] s = new String[N];
        long[][] hash = new long[N][];
        for (int i = 0; i < N; i++) {
            s[i] = scanner.next();
            char[] S = s[i].toCharArray();
            long[] hashes = new long[s[i].length()+1];
            hash[i]=hashes;
            hashes[0] = 0;
            long pow = MOD;
            for (int j = 1; j < hashes.length; j++) {
                hashes[j] = hashes[j-1] + (long)S[j -1] * pow;
                pow *= MOD;
                multiset.add(hashes[j]);
            }
        }
        for (int i = 0; i < N; i++) {
            int max = 0;
            for (int j = 1; j <= s[i].length(); j++) {
                if (multiset.map.getOrDefault(hash[i][j], 0) >= 2) {
                    max = Math.max(max, j);
                }
            }
            out.println(max);
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


    private static long myHash(int a, int b, long[] hashes, long[] bb) {
        long ret = hashes[b] - (hashes[a-1] * bb[b-a+1]) % MOD;
        if (ret < 0) {
            ret += MOD;
        }
        return ret % MOD;
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
