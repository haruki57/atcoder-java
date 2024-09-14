import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC338E_CONFUSED {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int N;
    static List<Integer>[] g;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        int[][] a = new int[N][3];
        g = new List[2*N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            if(x>y) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            g[x].add(y);
            g[y].add(x);
            a[i][0]=x;
            a[i][1]=y;
            a[i][2]=dist(x, y);
        }
        Arrays.sort(a, (o1, o2) -> o1[2]-o2[2]);
        for (int i = 0; i < a.length; i++) {
            //System.out.println(Arrays.toString(a[i]));
        }
        if(a[a.length-1][2]==1) {
            System.out.println("No");
            return;
        }

        if(a[0][2]!=1) {
            System.out.println("Yes");
            return;
        }

        int l = a[a.length-1][0];
        int r = a[a.length-1][1];
        if(isNotCrossed(l, r, true) && isNotCrossed(l, r, false)) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        /*
        int l = a[0][0];
        int r = a[0][1];
        for (int i = 0; i < N; i++) {
            int nl = (l-1+2*N)%(2*N);
            r = (r+1)%(2*N);
            int nr = (r+1)%(2*N);
            Integer partner = g[r].get(0);
            if(partner == nr) {
                r = nr;
            } else if(partner == nl) {
                l = nl;
            } else {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
        */
    }

    private static boolean isNotCrossed(int x, int y, boolean dir) {
        int dist = dist(x, y);
        //System.out.println(x+" "+y+" "+dist);
        if(dist %2 == 0) {
            return false;
        }
        if(dist == 1) {
            return true;
        }
        int nx, ny;
        if(dir) {
            nx = (x-1+2*N)%(2*N);
            ny = (y+1)%(2*N);
        } else {
            nx = (x+1+2*N)%(2*N);
            ny = (y-1+2*N)%(2*N);
        }
        int partner = g[nx].get(0);
        int ndist = dist(nx, partner);
        //System.out.println("ndist:"+ndist+", partner:"+partner);
        if(ndist%2==0) {
            return false;
        } else if(ndist == 1) {
            int nnx = dir ? (nx-1+2*N)%(2*N) : (nx+1+2*N)%(2*N);
            return isNotCrossed(nnx, y, dir);
        } else if (partner == ny){
            return isNotCrossed(nx, ny, dir);
        } else {
            return isNotCrossed(nx, partner, dir) && isNotCrossed(nx, partner, !dir);
        }
    }

    private static int dist(int x,int y) {
        if(x>y) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        return Math.min(y-x, x+2*N-y);
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
