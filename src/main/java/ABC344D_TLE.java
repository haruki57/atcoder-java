import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC344D_TLE {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    private static String t;
    static List<String>[] list;
    private static int N;
    static void run (final FastScanner scanner, final PrintWriter out) {
        t = scanner.next();
        N = scanner.nextInt();
        list = new List[N];
        for (int i = 0; i < N; i++) {
            list[i]=new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();
            for (int j = 0; j < a; j++) {
                list[i].add(scanner.next());
            }
        }
        int ret = bt(new char[t.length()], 0, 0, 0);
        if (ret==INF) {
            System.out.println(-1);
        } else {
            System.out.println(ret);
        }
    }

    static int bt(char[] sb, int cur, int cost, int depth) {
        if (depth == N) {
            if (String.valueOf(sb).equals(t)) {
                return cost;
            }
            return INF;
        }
        int ret = INF;
        for (String s : list[depth]) {
            if (cur+s.length() > sb.length) {
                continue;
            }
            for (int i = 0; i < s.length(); i++) {
                int ii = i + cur;
                sb[ii]=s.charAt(i);
            }
            boolean allSame = true;
            for (int i = 0; i < cur + s.length(); i++) {
                if (t.charAt(i)!=sb[i]) {
                    allSame=false;
                }
            }
            if (allSame) {
                ret = Math.min(ret, bt(sb, cur+s.length(), cost+1, depth+1));
            }
            for (int i = 0; i < s.length(); i++) {
                int ii = i + cur;
                sb[ii]=0;
            }
        }
        ret = Math.min(ret, bt(sb, cur, cost, depth+1));
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
