import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC345D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static int[] map;
    static int[][] tiles;
    static int N, H, W;
    static int[][] area;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        H = scanner.nextInt();
        W = scanner.nextInt();
        map = new int[H];
        tiles= new int[N][2];
        area =new int[H][W];
        for (int i = 0; i < N; i++) {
            tiles[i][0]=scanner.nextInt();
            tiles[i][1]=scanner.nextInt();
        }

        Permutations permutations = new Permutations(N);

        for (int[] perm : permutations.perms) {
            List<int[]> list = new ArrayList<>();
            for (int i : perm) {
                list.add(tiles[i]);
            }
            if(rec(0, list, 0)) {
                System.out.println("Yes");
                return;
            }
        }
        System.out.println("No");
    }

    private static boolean rec(int depth,List<int[]> tiles,int sum) {
        for (int i = 0; i < area.length; i++) {
            //System.out.println(Arrays.toString(area[i]));
        }
        //System.out.println();
        if(sum==H*W){
            return true;
        }
        if(sum > H*W) {
            return false;
        }
        if(depth==tiles.size()) {
            return false;
        }
        int[] tile = tiles.get(depth);
        int cnt = tile[0]!=tile[1]?2:1;
        while(cnt-->0) {
            l:for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    if(area[y][x]==0) {
                        boolean ok = false;
                        if(put(y, x, tile, 1)){
                            ok = rec(depth+1, tiles, sum + tiles.get(depth)[0]*tiles.get(depth)[1]);
                        }
                        put(y, x, tile, -1);
                        if(ok) {
                            return ok;
                        }
                        break l;
                    }
                }
            }
            turnTile(tile);
        }
        return false;
    }

    private static boolean put(int y,int x, int[] tile, int value) {
        if(y+tile[0]>H || x+tile[1]>W) {
            return false;
        }
        boolean ret = true;
        for (int i = y; i < y + tile[0]; i++) {
            for (int j = x; j < x + tile[1]; j++) {
                area[i][j]+=value;
                if(area[i][j]>=2) {
                    ret=false;
                }
            }
        }
        return ret;
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


    private static void turnTile(int[] tile) {
        int tmp = tile[0];
        tile[0]=tile[1];
        tile[1]=tmp;
    }

    static class Permutations {
        private int[] perm;
        private boolean[] used;
        private int N;
        ArrayList<int[]> perms = new ArrayList<>();

        public Permutations(int n) {
            this.N=n;
            used=new boolean[N];
            perm=new int[N];
            perm(0);
        }
        private void perm(int depth) {
            if (depth == N) {
                int[] idxArr = new int[N];
                for (int i = 0; i < N; i++) {
                    idxArr[i] = perm[i];
                }
                perms.add(idxArr);
                return;
            }
            for (int i = 0; i < N; i++) {
                if (used[i]) {
                    continue;
                }
                used[i] = true;
                perm[depth] = i;
                perm(depth + 1);
                used[i] = false;
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
