import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC254C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int[] b = a.clone();
        Arrays.sort(b);
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            TreeSet<Integer> orDefault = map.getOrDefault(a[i], new TreeSet<>());
            orDefault.add(i);
            map.put(a[i], orDefault);
        }
        //System.out.println(map);
        for (int i = 0; i < b.length; i++) {
            int j = i+1;
            while(j < b.length && b[i]==b[j]) {
                j++;
            }
//            System.out.println(i+" "+j);
            int num = b[i];
            TreeSet<Integer> idxs = map.get(num);
//            System.out.println(idxs);
            Map<Integer, Integer> map2 = new HashMap<>();
            for (Integer idx : idxs) {
                Integer cnt = map2.getOrDefault(idx%K, 0);
                map2.put(idx%K, cnt+1);
            }
//            System.out.println(map2);
            for (int k = i; k < j; k++) {
                int kk = k%K;
                Integer i1 = map2.get(kk);
                if (i1==null || i1==0) {
                    System.out.println("No");
                    return;
                }
                map2.put(kk, i1-1);
            }
            i=j-1;
            //System.out.println();
        }
        System.out.println("Yes");
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
