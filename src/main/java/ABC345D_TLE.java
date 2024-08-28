import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC345D_TLE {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int[] map;
    static int[][] tiles;
    static int N, H, W;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        H = scanner.nextInt();
        W = scanner.nextInt();
        map = new int[H];
        tiles= new int[N][2];
        for (int i = 0; i < N; i++) {
            tiles[i][0]=scanner.nextInt();
            tiles[i][1]=scanner.nextInt();
        }
        for (int bit = 1; bit < 1<<N; bit++) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                if ((bit & (1 << i)) > 0) {
                    list.add(i);
                }
            }
            int sum = 0;
            for (Integer i : list) {
                sum += tiles[i][0] * tiles[i][1];
            }
            if (sum != H * W) {
                continue;
            }
            //System.out.println(Integer.toBinaryString(bit));
            for (int i = 0; i < map.length; i++) {
                //System.out.println(Arrays.toString(map[i]));
            }
            if (rec(list, list.size()-1)) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");

    }

    private static boolean rec(List<Integer> list, int depth) {
        //System.out.println(depth);
        if (depth==-1) {
            return true;
        }
        int tileNum = list.get(depth);
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if ((map[i]&(1<<j))>1) {
                    continue;
                }
                int h = tiles[tileNum][0];
                int w = tiles[tileNum][1];
                int mask = 0;
                for (int k = 0; k < w; k++) {
                    mask += (1<<(j+k));
                }
                if (i+h<=H && j+w<=W) {
                    if (all0(i, i+h, mask)) {
                        put(i, i+h, mask);
                        if (rec(list, depth-1)) {
                            return true;
                        }
                        remove(i, i+h, mask);
                    }
                }
                if (h!=w) {
                    h = tiles[tileNum][1];
                    w = tiles[tileNum][0];
                    mask = 0;
                    for (int k = 0; k < w; k++) {
                        mask += (1<<(j+k));
                    }

                    if (i+h<=H && j+w<=W) {
                        if (all0(i, i+h, mask)) {
                            put(i, i+h, mask);
                            if (rec(list, depth-1)) {
                                return true;
                            }
                            remove(i, i+h, mask);
                        }
                    }
                }
            }
        }
        return false;
    }


    private static void put(int i0, int i1, int mask) {
        for (int i = i0; i < i1; i++) {
            map[i] |= mask;
        }
    }
    private static void remove(int i0, int i1, int mask) {
        for (int i = i0; i < i1; i++) {
            map[i] -= mask;
        }
    }

    private static boolean all0(int i0,int j0,int mask) {
        for (int i = i0; i < j0; i++) {
            if ((map[i]&mask)>0) {
                return false;
            }
        }
        return true;
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
