import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class ABC370D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int Q = scanner.nextInt();
        TreeSet<Integer>[] hs = new TreeSet[H];
        for (int i = 0; i < hs.length; i++) {
            hs[i]=new TreeSet<>();
            for (int j = 0; j < W; j++) {
                hs[i].add(j);
            }
        }
        TreeSet<Integer>[] ws = new TreeSet[W];
        for (int i = 0; i < ws.length; i++) {
            ws[i]=new TreeSet<>();
            for (int j = 0; j < H; j++) {
                ws[i].add(j);
            }
        }

        while(Q-->0){
            int h = scanner.nextInt()-1;
            int w = scanner.nextInt()-1;
            Integer w1 = hs[h].floor(w);
            Integer w2 = hs[h].ceiling(w);
            Integer h1 = ws[w].floor(h);
            Integer h2 = ws[w].ceiling(h);
            //System.out.println(w1+" "+w2+" "+h1+" "+h2);
            if(w1!=null) {
                hs[h].remove(w1);
                ws[w1].remove(h);
            }

            if(w2!=null) {
                hs[h].remove(w2);
                ws[w2].remove(h);
            }

            if(h1!=null) {
                ws[w].remove(h1);
                hs[h1].remove(w);
            }

            if(h2!=null) {
                ws[w].remove(h2);
                hs[h2].remove(w);
            }
            /*
            for (int i = 0; i < hs.length; i++) {
                System.out.println(hs[i]);
            }
            System.out.println();
            for (int i = 0; i < ws.length; i++) {
                System.out.println(ws[i]);
            }
            System.out.println("----------");

             */
        }
        int ans = 0;
        for (int i = 0; i < hs.length; i++) {
            ans += hs[i].size();
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
