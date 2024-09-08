import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC370E_TLE {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long K = scanner.nextLong();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long[] sum = new long[N+1];
        for (int i = 0; i < a.length; i++) {
            sum[i+1]=sum[i]+a[i];
        }
        Map<Long, List<Integer>> valToIdx = new TreeMap<>();
        for (int i = 0; i < sum.length; i++) {
            List<Integer> hoge = valToIdx.getOrDefault(sum[i]-K, new ArrayList<>());
            if(!hoge.isEmpty()) {
                System.out.println(sum[i]+" "+i+" "+hoge);
                System.out.println(sum[i]-sum[hoge.get(0)]);
            }
            List<Integer> list = valToIdx.getOrDefault(sum[i], new ArrayList<>());
            list.add(i);
            valToIdx.put(sum[i], list);
        }
        System.out.println(Arrays.toString(sum));
        ansTle(sum, K);
    }

    static void ansTle(long[] sum,long K) {
        int ans = 0;
        for (int bit = 0; bit < 1 << (sum.length - 2); bit++) {
            Set<Long> set = new HashSet<>();
            int cur = 0;
            for (int i = 0; i < sum.length-1; i++) {
                if((bit&(1<<i))>0) {
                    set.add(sum[i+1]-sum[cur]);
                    cur = i+1;
                }
            }
            set.add(sum[sum.length-1]-sum[cur]);
            //System.out.println(Integer.toBinaryString(bit));
            //System.out.println(set);
            //System.out.println();
            if(!set.contains(K)) {
                ans++;
            }
        }
        System.out.println(ans);
        //System.out.println(recTle(a, K, 0, 0));
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
