package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class _021 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        var scc = new StronglyConnectedComponents(N);
        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            scc.addEdge(a,b);
        }
        List<List<Integer>> stronglyConnectedComponents = scc.getStronglyConnectedComponents();
        long ans = 0;
        for (List<Integer> stronglyConnectedComponent : stronglyConnectedComponents) {
            //System.out.println(stronglyConnectedComponent);
            long size = stronglyConnectedComponent.size();
            ans += (size * (size-1))/2;
        }
        System.out.println(ans);
    }

    static class StronglyConnectedComponents {
        private int N;
        private List<List<Integer>> graph;
        private List<List<Integer>> reverseGraph;
        private boolean[] visited;
        private Stack<Integer> stack;

        public StronglyConnectedComponents(int n) {
            N = n;
            graph = new ArrayList<>();
            reverseGraph = new ArrayList<>();
            visited = new boolean[N];
            stack = new Stack<>();
            for (int i = 0; i < N; i++) {
                graph.add(new ArrayList<>());
                reverseGraph.add(new ArrayList<>());
            }
        }

        public void addEdge(int from, int to) {
            graph.get(from).add(to);
            reverseGraph.get(to).add(from);
        }

        public List<List<Integer>> getStronglyConnectedComponents() {
            for (int i = 0; i < N; i++) {
                if (!visited[i]) {
                    dfs1(i);
                }
            }

            Arrays.fill(visited, false);
            List<List<Integer>> sccs = new ArrayList<>();
            while (!stack.isEmpty()) {
                int v = stack.pop();
                if (!visited[v]) {
                    List<Integer> scc = new ArrayList<>();
                    dfs2(v, scc);
                    sccs.add(scc);
                }
            }
            return sccs;
        }

        private void dfs1(int v) {
            visited[v] = true;
            for (int u : graph.get(v)) {
                if (!visited[u]) {
                    dfs1(u);
                }
            }
            stack.push(v);
        }

        private void dfs2(int v, List<Integer> scc) {
            visited[v] = true;
            scc.add(v);
            for (int u : reverseGraph.get(v)) {
                if (!visited[u]) {
                    dfs2(u, scc);
                }
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
