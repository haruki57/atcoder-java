import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC344E_STUPID {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        int Q = scanner.nextInt();
        int[][] q = new int[Q][3];
        for (int i = 0; i < Q; i++) {
            int t = scanner.nextInt();
            q[i][0]=t;
            if (t==1) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                q[i][1]=x;
                q[i][2]=y;
            } else {
                int x = scanner.nextInt();
                q[i][1]=x;
            }
        }
        TreeSet<Integer> s = new TreeSet<>();
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> remap = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            s.add(a[i]);
        }
        for (int i = 0; i < q.length; i++) {
            s.add(q[i][1]);
            s.add(q[i][2]);
        }
        s.remove(0);
        int idx = 1;
        for (Integer i : s) {
            map.put(idx, i);
            remap.put(i, idx);
            idx++;
        }
        System.out.println(map);
        System.out.println(remap);
        int len = 2*100000+9;
        boolean[] b = new boolean[len];
        for (int i = 0; i < a.length; i++) {
            b[remap.get(a[i])]=true;
        }
        for (int i = 0; i < a.length; i++) {
            a[i]=remap.get(a[i]);
        }
        for (int i = 0; i < q.length; i++) {
            q[i][1]=remap.get(q[i][1]);
            q[i][2]=remap.getOrDefault(q[i][2], 0);
        }
        System.out.println(Arrays.toString(a));
        for (int i = 0; i < q.length; i++) {
            System.out.println(Arrays.toString(q[i]));
        }
        int[] pad = new int[10];
        for (int i = 0; i < q.length; i++) {
            if (q[i][0]==1) {
                pad[q[i][1]]++;
            }
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
