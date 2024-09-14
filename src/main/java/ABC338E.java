import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC338E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int N;
    static List<Integer>[] g;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        int[][] a = new int[N*2][2];
        for (int i = 0; i < N; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            if(x>y) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            a[y][0]=x;
            a[y][1]=i;
            a[x][0]=y;
            a[x][1]=i;
        }
        Arrays.sort(a, (o1, o2) -> o1[0]-o2[0]);
        for (int i = 0; i < a.length; i++) {
            //System.out.println(Arrays.toString(a[i]));
        }
        boolean[] isPushed = new boolean[2*N];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < a.length; i++) {
            int i1 = a[i][1];
            if(isPushed[i1]) {
                Integer pop = stack.pop();
                if(i1!=pop) {
                    System.out.println("Yes");
                    return;
                }
            } else {
                stack.push(i1);
                isPushed[i1]=true;
            }
        }
        System.out.println("No");
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
