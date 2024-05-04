import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC314C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        {
            int aa = 1;
            int bb = 2;
            int cc = 3;
            int tmp = aa;
            aa = bb;
            bb = tmp;

            // System.out.println(aa +" "+ bb + " "+ cc);
            tmp = bb;
            bb = cc;
            cc = tmp;
            // System.out.println(aa +" " +bb + " "+ cc);
        }
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        int[] c = new int[N];
        Arrays.setAll(c, i -> scanner.nextInt());
        Map<Integer, List<Integer>> indexesByColor = new HashMap<>();
        for (int i = 0; i < c.length; i++) {
            if (!indexesByColor.containsKey(c[i])) {
                indexesByColor.put(c[i], new ArrayList<>());
            }
            indexesByColor.get(c[i]).add(i);
        }
        //System.out.println(Arrays.toString(indexes));
        for (int i = 1; i <= M; i++) {
            List<Integer> idxs = indexesByColor.get(i);
            //System.out.println(idxs);
            for (int j = idxs.size()-2; j >= 0; j--) {
                Integer i1 = idxs.get(j);
                Integer i2 = idxs.get((j+1)%idxs.size());
                char tmp = s[i1];
                s[i1] = s[i2];
                s[i2] = tmp;
            }
            //System.out.println(Arrays.toString(indexes));
            //System.out.println();
        }
        for (int i = 0; i < s.length; i++) {
            out.print(s[i]);
        }
        out.println();

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
