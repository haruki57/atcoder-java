import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC369E {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        long[][] dist = new long[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], INF);
        }
        Map<Integer, int[]>[] g = new Map[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new HashMap<>();
        }
        int[][] edges = new int[M][3];
        for (int i = 0; i < M; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            int t = scanner.nextInt();
            g[x].put(i, new int[]{y, t});
            g[y].put(i, new int[]{x, t});
            edges[i][0]=x;
            edges[i][1]=y;
            edges[i][2]=t;
            dist[x][y]=Math.min(dist[x][y], t);
            dist[y][x]=Math.min(dist[y][x], t);
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        for (int i = 0; i < N; i++) {
            dist[i][i]=0;
        }
        for (int i = 0; i < dist.length; i++) {
            //System.out.println(Arrays.toString(dist[i]));
        }
        int Q = scanner.nextInt();
        while(Q-->0) {
            int K = scanner.nextInt();
            long ans = INF;
            int[] k = new int[K];
            for (int i = 0; i < k.length; i++) {
                k[i]= scanner.nextInt()-1;
            }
            for (int[] perm : new Permutations(K).perms) {
                int[][] tar = new int[K][2];
                for (int bit = 0; bit < 1 << K; bit++) {
                    for (int i = 0; i < perm.length; i++) {
                        int edgeIdx = k[perm[i]];
                        if((bit&(1<<i)) > 0) {
                            tar[i][0] = edges[edgeIdx][0];
                            tar[i][1] = edges[edgeIdx][1];
                        } else {
                            tar[i][0] = edges[edgeIdx][1];
                            tar[i][1] = edges[edgeIdx][0];
                        }
                    }
                    for (int i = 0; i < tar.length; i++) {
                        //System.out.println(Arrays.toString(tar[i]));
                    }
                    long sum = dist[0][tar[0][0]];
                    //System.out.println(0+" to "+tar[0][0]+" "+dist[0][tar[0][0]]);
                    //System.out.println("sum="+sum);
                    for (int i = 0; i < K-1; i++) {
                        sum += dist[tar[i][1]][tar[i+1][0]];
                        //System.out.println(tar[i][1]+" to "+tar[i+1][0]+" "+dist[tar[i][1]][tar[i+1][0]]);
                    }
                    //System.out.println("sum="+sum);
                    sum += dist[tar[K-1][1]][N-1];
                    //System.out.println(tar[K-1][1]+" to "+(N-1)+" "+dist[tar[K-1][1]][N-1]);
                    //System.out.println("sum="+sum);
                    for (int i = 0; i < K; i++) {
                        sum += edges[k[i]][2];
                    }
                    //System.out.println("sum="+sum);
                    //System.out.println();
                    ans = Math.min(ans, sum);
                }
            }
            System.out.println(ans);

        }
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
