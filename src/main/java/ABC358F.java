import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC358F {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int N,M,K;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        M = scanner.nextInt();
        K = scanner.nextInt();
        int gy = M-1, gx=N-1;
        List<int[]> paths = new ArrayList<>();
        if (N%2==0) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int jj = j;
                    if (i%2==0) {
                        jj = M-j-1;
                    }
                    paths.add(new int[]{i, jj});
                    if (len(jj,i,gy,gx)+ paths.size() == K) {
                        for (int[] path : paths) {
                            //System.out.println(Arrays.toString(path));
                        }
                        output(paths, true);
                        return;
                    }
                }
            }
            System.out.println("No");
            return;
        }
        /*
        if (M%2==1) {
            int ii = 0;
            for (int j = 0; j < M; j++) {
                int jj = M-j-1;
                paths.add(new int[]{ii, jj});
                if (len(jj, ii, gy, gx)+paths.size() == K) {
                    output(paths, true);
                    return;
                }
            }
            for (int j = 0; j < M; j++) {
                for (int i = 0; i < N-1; i++) {
                    ii = i+1;
                    if (j%2==1) {
                        ii = N-i-1;
                    }
                    paths.add(new int[]{ii, j});
                    for (int[] path : paths) {
                        //System.out.println(Arrays.toString(path));
                    }
                    //System.out.println();
                    if (len(j, ii, gy, gx)+paths.size() == K) {
                        output(paths, false);
                        return;
                    }
                }
            }
            System.out.println("No");
            return;
        }
         */


        for (int i = 0; i < N-2; i++) {
            for (int j = 0; j < M; j++) {
                int jj = j;
                if (i%2==0) {
                    jj = M-j-1;
                }
                paths.add(new int[]{i, jj});
                if (len(jj,i,gy,gx)+ paths.size() == K) {
                    for (int[] path : paths) {
                        //System.out.println(Arrays.toString(path));
                    }
                    output(paths, true);
                    return;
                }
            }
        }
        int ii = N-2;
        for (int j = 0; j < M; j++) {
            if (j%2==0) {
                ii = N-2;
            } else {
                ii = N-1;
            }

            paths.add(new int[]{ii, j});
            if (len(j,ii,gy,gx)+ paths.size() == K) {
                output(paths, false);
                return;
            }

            if (j%2==1) {
                ii = N-2;
            } else {
                ii = N-1;
            }

            paths.add(new int[]{ii, j});
            if (len(j,ii,gy,gx)+ paths.size() == K) {
                output(paths, false);
                return;
            }
        }
        System.out.println("No");

        //Stack<int[]> stack = new Stack<>();
    }

    static void output(List<int[]> paths, boolean goDown) {
        char[][] out = new char[N*2+1][M*2+1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                out[i*2+1][j*2+1]='o';
                out[i*2+1][j*2+2]='|';
                out[i*2+2][j*2+1]='-';
                out[i*2+2][j*2+2]='+';
            }
        }
        for (int i = 0; i < out.length; i++) {
            out[i][0] = '+';
            out[i][M*2] = '+';
        }
        for (int i = 0; i < out[0].length; i++) {
            out[0][i] = '+';
            out[N*2][i] = '+';
        }
        out[0][M*2-1] = 'S';
        out[N*2][M*2-1] = 'G';

        int[] last = null;
        for (int[] path : paths) {
            last = path;
        }

        for (int[] path : paths) {
            //System.out.println(Arrays.toString(path));
        }
        //System.out.println();
        if (goDown) {
            int y = last[0];
            int x = last[1];
            for (int i = y+1; i < N; i++) {
                paths.add(new int[]{i, x});
            }
            for (int i = x+1; i < M; i++) {
                paths.add(new int[]{N-1, i});
            }
        } else {
            int y = last[0];
            int x = last[1];
            for (int i = x+1; i < M; i++) {
                paths.add(new int[]{y, i});
            }
            for (int i = y+1; i < N; i++) {
                paths.add(new int[]{i, M-1});
            }
        }
        for (int[] path : paths) {
//            System.out.println(Arrays.toString(path));
        }
//        System.out.println();
        for (int i = 0; i <paths.size()-1; i++) {
            int[] a = paths.get(i);
            int[] b = paths.get(i+1);
            int a1 = a[0]*2+1;
            int a2 = a[1]*2+1;
            int b1 = b[0]*2+1;
            int b2 = b[1]*2+1;
            out[(a1+b1)/2][(a2+b2)/2] = '.';
        }
        for (int[] path : paths) {
            //System.out.println(Arrays.toString(path));
        }
        System.out.println("Yes");
        for (int i = 0; i < out.length; i++) {
            System.out.println(out[i]);
        }
    }

    static int len(int a,int b,int c,int d) {
        return (Math.abs(a-c)+Math.abs(b-d));
    }

    /*
    static int[] dy = {1,1,-1,-1};
    static int[] dx = {-1,1,1, -1};
    private static void bt(int y, int x, LinkedList<Integer> integers) {

    }

     */

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
