import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC188D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long C = scanner.nextInt();
        int[][] a = new int[N][3];
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            a[i][0]= scanner.nextInt();
            a[i][1]= scanner.nextInt();
            a[i][2]= scanner.nextInt();
            set.add(a[i][0]);
            set.add(a[i][1]);
            set.add(a[i][1]+1);
        }
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> mapRev = new HashMap<>();
        int cnt = 0;
        for (Integer i : set) {
            map.put(i, cnt);
            mapRev.put(cnt, i);
            cnt++;
        }
        long[] imos = new long[N*3];
        for (int i = 0; i < a.length; i++) {
            Integer l = map.get(a[i][0]);
            Integer r = map.get(a[i][1]);
            imos[l]+=a[i][2];
            imos[r+1]-=a[i][2];
        }
        long[] sum = new long[N*3];
        sum[0]=imos[0];
        for (int i = 1; i < sum.length; i++) {
            sum[i]=sum[i-1]+imos[i];
        }

        System.out.println(Arrays.toString(imos));
        System.out.println(Arrays.toString(sum));
        System.out.println(map);
        System.out.println(mapRev);
        long ans = 0;
        for (int i = 0; i < sum.length-1; i++) {
            if(sum[i]==0) {
                continue;
            }
            long cost = Math.min(sum[i], C);
            Integer i1 = mapRev.get(i);
            Integer i2 = mapRev.get(i+1);
            System.out.println(i1+" "+i2+" "+cost);
            ans += cost * (i2-i1);
        }
        System.out.println(ans);
        //ansForSmallCase(N, C, a);
    }

    static void ansForSmallCase(int N, long C, int[][] a) {
        long ans = 0;
        for (int i = 0; i < 100; i++) {
            long sum = 0;
            for (int j = 0; j < N; j++) {
                if(a[j][0]<=i&&i<=a[j][1]) {
                    sum += a[j][2];
                }
            }
            ans += Math.min(sum, C);
        }
        System.out.println(ans);
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
