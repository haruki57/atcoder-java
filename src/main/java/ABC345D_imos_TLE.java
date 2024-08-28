import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class ABC345D_imos_TLE {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int[] map;
    static int[][] tiles;
    static int N, H, W;
    static int[][] imos;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        H = scanner.nextInt();
        W = scanner.nextInt();
        map = new int[H];
        tiles= new int[N][2];
        imos =new int[H+1][W+1];
        for (int i = 0; i < N; i++) {
            tiles[i][0]=scanner.nextInt();
            tiles[i][1]=scanner.nextInt();
        }
        for (int bit = 0; bit < 1 << N; bit++) {
            if(sum(tiles,bit) != H*W) {
                continue;
            }
            if(rec(0, 0, bit)) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }

    private static int sum(int[][] tiles, int bit) {
        int ret = 0;
        for (int i = 0; i < tiles.length; i++) {
            if((bit&(1<<i)) == 0) {
                continue;
            }
            ret += tiles[i][0]*tiles[i][1];
        }
        return ret;
    }

    private static boolean rec(int depth,int sum,int bit) {
        if(depth==N) {
            int[][] sumArray = new int[imos.length][imos[0].length];
            for (int i = 0; i < imos.length; i++) {
                sumArray[i]=imos[i].clone();
            }
            for (int y = 0; y < H; y++) {
                for (int x = 1; x < W; x++) {
                    sumArray[y][x] += sumArray[y][x - 1];
                }
            }
            for (int y = 1; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    sumArray[y][x] += sumArray[y - 1][x];
                }
            }
            for (int i = 0; i < sumArray.length; i++) {
                //System.out.println(Arrays.toString(sumArray[i]));
            }
            //System.out.println();
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if(sumArray[i][j]!=1) {
                        return false;
                    }
                }
            }
            return true;
        }
        if((bit&(1<<depth))==0) {
            return rec(depth+1, sum, bit);
        }
        int cnt = tiles[depth][0]!=tiles[depth][1]?2:1;
        int tileNum = tiles[depth][0]*tiles[depth][1];
        boolean ret = false;
        while(cnt-->0) {
            for (int y = 0; y < H; y++) {
                if(y+tiles[depth][0] > H) {
                    break;
                }
                for (int x = 0; x < W; x++) {
                    if(x+tiles[depth][1] > W) {
                        break;
                    }
                    if(put(y, x, tiles[depth], 1)) {
                        ret |= rec(depth+1, sum + tileNum, bit);
                    }
                    put(y, x, tiles[depth], -1);
                }
            }
            turnTile(tiles[depth]);
        }
        return ret;
    }

    private static boolean put(int y,int x, int[] tile, int value) {
        imos[y][x]+=value;
        imos[y][x+tile[1]]-=value;
        imos[y+tile[0]][x]-=value;
        imos[y+tile[0]][x+tile[1]]+=value;
        return !(imos[y][x]>=2 ||
                imos[y][x+tile[1]] <= -2||
                imos[y+tile[0]][x] <= -2||
                imos[y+tile[0]][x+tile[1]] >= 2);
    }

    private static void turnTile(int[] tile) {
        int tmp = tile[0];
        tile[0]=tile[1];
        tile[1]=tmp;
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
