import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC226E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();


        var uf = new UnionFind(N);
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int x = scanner.nextInt()-1;
            int y = scanner.nextInt()-1;
            g[x].add(y);
            g[y].add(x);
            uf.unite(x, y);
        }
        if(N!=M) {
            System.out.println(0);
            return;
        }
        int treeCnt = 0;
        TreeSet<Integer> visited = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            if(visited.contains(i)) {
                continue;
            }
            treeCnt++;
            TreeSet<Pair<Integer, Integer>> edgeVisited = new TreeSet<>();
            Queue<Integer> q = new LinkedList<>();
            visited.add(i);
            q.add(i);
            int nodeCnt = 0;
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                nodeCnt++;
                for (Integer next : g[cur]) {
                    int x = cur;
                    int y = next;
                    if(x > y) {
                        int tmp = x;
                        x = y;
                        y = tmp;
                    }
                    edgeVisited.add(new Pair(x, y));
                    if(visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    q.add(next);
                }
            }
            //System.out.println(nodeCnt+" "+edgeVisited);
            if(nodeCnt!= edgeVisited.size()) {
                System.out.println(0);
                return;
            }
        }
        /*
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            map.put(uf.root(i), map.getOrDefault(uf.root(i), 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue()<=2) {
                System.out.println(0);
                return;
            }
        }

         */
        System.out.println(powWithMod(2, treeCnt, MOD));
    }

    static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A,B>>{
        A first;
        B second;
        public Pair(A a, B b) {
            this.first=a;
            this.second=b;
        }

        @Override
        public int compareTo(Pair<A,B> o) {
            int compareFirst = this.first.compareTo(o.first);
            if (compareFirst==0) {
                return this.second.compareTo(o.second);
            }
            return compareFirst;
        }

        @Override
        public String toString() {
            return "[" + this.first + ", " + this.second + "]";
        }

    }


    private static long powWithMod(long a, long b, int mod) {
        String binaryString = Long.toBinaryString(b);
        int len = binaryString.length();
        long ret = 1;
        for (int i = 0; i < len; i++) {
            if (binaryString.charAt(len-i-1) == '1') {
                ret = (ret * a) % mod;
            }
            a = (a*a) % mod;
        }
        return ret;
    }

    static class UnionFind {
        int[] par;
        int[] size;

        public UnionFind(int n) {
            par = new int[n + 1];
            size = new int[n + 1];
            Arrays.fill(par, -1);
            Arrays.fill(size, 1);
        }

        int root(int x){
            while(true) {
                if(par[x] == -1) {
                    break;
                }
                x = par[x];
            }
            return x;
        }

        void unite(int u, int v) {
            int rootU = root(u);
            int rootV = root(v);
            if(rootU == rootV) {
                return;
            }
            if(size[rootU] < size[rootV]) {
                par[rootU] = rootV;
                size[rootV] += size[rootU];
            }
            else {
                par[rootV] = rootU;
                size[rootU] += size[rootV];
            }
        }

        boolean same(int u, int v) {
            return root(u) == root(v);
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
