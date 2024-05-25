import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ABC355E_WA {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int L = scanner.nextInt();
        int R = scanner.nextInt();
        long ans = 0;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{L, R});
        while(!stack.isEmpty()) {
            int[] pop = stack.pop();
            int diff = pop[1]-pop[0]+1;
            for (int i = N; i >= 0; i--) {
                int width = (int)Math.pow(2, i);
                /*
                if (width > diff) {
                    continue;
                }
                 */
                int start = 0;
                if (pop[0]%width == 0) {
                    start = pop[0];
                } else {
                    start = pop[0]-pop[0]%width + width;
                }
                int end = start + width - 1;
                int ii = i;
                int jj = start / width;
                if (pop[0] <= start && end <= pop[1]) {
                    System.out.println("? "+ii+" " + jj);
                    if (pop[0]!=start) {
                        stack.add(new int[]{pop[0], start-1});
                    }
                    if (pop[1]!=end) {
                        stack.add(new int[]{end+1, pop[1]});
                    }
                    break;
                }
            }

            int T = scanner.nextInt();
            if (T==-1) {
                throw new RuntimeException("-1");
            }
            ans += T;
            for (int[] ints : stack) {
                //System.out.println(Arrays.toString(ints));
            }
            //System.out.println();

        }
        System.out.println("! " +ans % 100);
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
