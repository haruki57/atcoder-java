import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC362E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    /*
    10
    1 3 5 7 7 5 9 7 9 7
     */

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        if (N==1) {
            System.out.println(1);
            return;
        }
        long[] ans = new long[N];
        ans[0]=N;
        ans[1]=(long)N*(N-1)/2;
        for (int x = 0; x <N; x++) {
            for (int y = x+1; y < N; y++) {
                long diff = a[y]-a[x];
                List<Long> list =new ArrayList<>();
                for (int i = y+1; i < N; i++) {
                    if (diff==0) {
                        if (a[i]==a[y]) {
                            list.add(a[i]);
                        }
                    } else {
                        if (diff > 0) {
                            if(a[i] > a[y] && a[i]%diff==a[x]%diff) {
                                list.add(a[i]);
                            }
                        } else {
                            if(a[i] < a[y] && a[i]%diff==a[x]%diff) {
                                list.add(a[i]);
                            }
                        }
                    }

                }
                if (list.isEmpty()) {
                    continue;
                }
                long[] array = new long[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i]=list.get(i);
                }
                if (diff < 0) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] *= -1;
                    }
                }
                comp(array);
                System.out.println(Arrays.toString(array));
                long[][] dp = new long[array.length+1][array.length+1];
                //System.out.println(x+" "+y);
                //System.out.println(Arrays.toString(array));
            }
        }
    }

    static Map<Long, Integer> comp(long[] a) {
        Map<Long, Integer> map = new HashMap<>();
        int cnt = 0;
        long[] copied = a.clone();
        Arrays.sort(copied);
        map.put(copied[0], cnt);
        cnt++;
        for (int i = 1; i < copied.length; i++) {
            if (copied[i-1]!=copied[i]) {
                map.put(copied[i], cnt);
                cnt++;
            }
        }
        for (int i = 0; i < a.length; i++) {
            a[i]=map.get(a[i]);
        }
        return map;
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
