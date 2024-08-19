import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC367E_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long K = scanner.nextLong();
        int[] x = new int[N];
        Arrays.setAll(x, i -> scanner.nextInt()-1);
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());

        Map<Integer, List<Integer>> loopIdxToLoop = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            if(visited.contains(i)) {
                continue;
            }
            visited.add(i);
            Queue<Integer> q = new LinkedList<>();
            List<Integer> path = new ArrayList<>();
            Set<Integer> visitedLocal = new HashSet<>();
            q.add(i);
            visitedLocal.add(i);
            visited.add(i);


            int loopStart = -1;
            int lastVisited = -1;
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                int next = x[cur];
                path.add(cur);
                visited.add(cur);
                visitedLocal.add(cur);
                if(visitedLocal.contains(next)) {
                    loopStart = next;
                    lastVisited=next;
                    break;
                }
                if(visited.contains(next)) {
                    lastVisited=next;
                    break;
                }
                q.add(next);

            }
            /*
            System.out.println(path);
            System.out.println(i);
            System.out.println(loopStart);
            System.out.println(lastVisited);
            System.out.println();

             */
            if(loopStart != -1) {
                // all new
                List<Integer> pathToLoop = new ArrayList<>();
                for (Integer p : path) {
                    if(p.equals(loopStart)) {
                        break;
                    }
                    pathToLoop.add(p);
                }
                List<Integer> loop = new ArrayList<>();
                for (int j = pathToLoop.size(); j < path.size(); j++) {
                    loop.add(path.get(j));
                }
                int i0 = 0;
                for (Integer p : pathToLoop) {
                    Node n = new Node();
                    nodes[p] = n;
                    n.pathToLoop = pathToLoop;
                    n.idxToLoop = pathToLoop.size()-i0++;
                    n.loop = loop;
                }
                i0=0;
                for (Integer p : loop) {
                    Node n = new Node();
                    nodes[p] = n;
                    n.idx = i0++;
                    n.loop = loop;
                }
            } else {
                Node already = nodes[lastVisited];
                var pathToLoop = new ArrayList<Integer>();
                pathToLoop.addAll(path);
                if(already.pathToLoop!=null) {
                    pathToLoop.addAll(already.pathToLoop);
                }
                int i0 = 0;
                for (int j = 0; j < path.size(); j++) {
                    int p = path.get(j);
                    Node n = new Node();
                    nodes[p] = n;
                    n.pathToLoop = pathToLoop;
                    n.idxToLoop = pathToLoop.size()-i0++;
                    n.loop = already.loop;
                }
            }

        }
        for (Node node : nodes) {
            //System.out.println(node);
        }

        for (int i = 0; i < nodes.length; i++) {
            Node n = nodes[i];
            if(n.pathToLoop==null) {
                int idx = (int)((((long)n.idx + K)) % n.loop.size());
                out.print(a[n.loop.get(idx)]+" ");
            } else {
                int distToLoop = n.idxToLoop;
                if(distToLoop > K) {
                    int idx = n.pathToLoop.get((int)distToLoop- (int)K );
                    out.print(a[n.loop.get(idx)]+" ");
                } else {
                    long KK = K-distToLoop;
                    int idx = (int)((((long)n.idx + KK)) % n.loop.size());
                    out.print(a[n.loop.get(idx)]+" ");
                }
            }
            //System.out.println(nodes[i]);
        }



        /*
        var scc = new StronglyConnectedComponents(N);
        for (int i = 0; i < N; i++) {
            scc.addEdge(i, x[i]);
        }
        List<List<Integer>> components = scc.getStronglyConnectedComponents();
        for (List<Integer> component : components) {
            int temp = component.get(0);
            for (int i = 1; i < component.size(); i++) {
                uf.unite(temp, component.get(i));
            }
//            System.out.println(component);
        }
//        System.out.println();

        boolean[] isLoop = new boolean[components.size()];
        isLoop[isLoop.length-1]=true;
        for (int i = 1; i < components.size(); i++) {
            var c0 = components.get(i-1);
            var c1 = components.get(i);
            var first0 = c0.get(0);
            var first1 = c1.get(0);
            //System.out.println(first0+" "+x[first0]+" "+first1+" "+uf.same(x[first0], first1));
            if(uf.same(x[first0], first1)) {
                isLoop[i-1]=false;
                //System.out.println("false:"+" "+i);
            } else {
                isLoop[i-1]=true;
                //System.out.println("true:"+" "+i);
            }
        }
        //System.out.println(Arrays.toString(isLoop));

        List<Integer> pathToLoop = new ArrayList<>();
        Node[] nodes = new Node[N];
        for (int i = 1; i < components.size(); i++) {
            var c0 = components.get(i-1);
            var c1 = components.get(i);
            if(isLoop[i-1]) {
                {
                    int i1 = 0;
                    for (Integer nodeInLoop : c0) {
                        nodes[nodeInLoop] = new Node();
                        nodes[nodeInLoop].loop = c0;
                        nodes[nodeInLoop].idx = i1;
                        i1++;
                    }
                }

                pathToLoop = new ArrayList<>();
                if(isLoop[i]) {
                    int i1 = 0;
                    for (Integer nodeInLoop : c1) {
                        nodes[nodeInLoop] = new Node();
                        nodes[nodeInLoop].loop = c1;
                        nodes[nodeInLoop].idx = i1;
                        i1++;
                    }
                }
                continue;
            }
            var first0 = c0.get(0);
            var first1 = c1.get(0);
            if(uf.same(x[first0], first1)) {
                pathToLoop.add(first0);
                if(isLoop[i]) {
                    for (int i1 = 0; i1 < pathToLoop.size(); i1++) {
                        int path = pathToLoop.get(i1);
                        nodes[path] = new Node();
                        nodes[path].pathToLoop = pathToLoop;
                        nodes[path].idxToLoop = pathToLoop.size()-i1-1;
                        nodes[path].loop = c1;
                    }
                    pathToLoop = new ArrayList<>();
                    int i1 = 0;
                    for (Integer nodeInLoop : c1) {
                        nodes[nodeInLoop] = new Node();
                        nodes[nodeInLoop].loop = c1;
                        nodes[nodeInLoop].idx = i1;
                        i1++;
                    }
                }
            } else {
                pathToLoop = new ArrayList<>();
                if(isLoop[i]) {
                    int i1 = 0;
                    for (Integer nodeInLoop : c1) {
                        nodes[nodeInLoop] = new Node();
                        nodes[nodeInLoop].loop = c1;
                        nodes[nodeInLoop].idx = i1;
                        i1++;
                    }
                }
            }
        }
        for (int i = 0; i < nodes.length; i++) {
            //System.out.println(nodes[i]);
        }
        for (int i = 0; i < nodes.length; i++) {
            Node n = nodes[i];
            if(n.pathToLoop==null) {
                int idx = (int)((((long)n.idx + K)) % n.loop.size());
                out.print(a[n.loop.get(idx)]+" ");
            } else {
                int distToLoop = n.idxToLoop;
                if(distToLoop > K) {
                    int idx = n.pathToLoop.get((int)distToLoop- (int)K );
                    out.print(a[n.loop.get(idx)]+" ");
                } else {
                    long KK = K-distToLoop;
                    int idx = (int)((((long)n.idx + KK)) % n.loop.size());
                    out.print(a[n.loop.get(idx)]+" ");
                }
            }
            //System.out.println(nodes[i]);
        }

         */
    }

    static class Node {
        int idx;
        List<Integer> loop;
        int idxToLoop;
        List<Integer> pathToLoop;

        @Override
        public String toString() {
            return "Node{" +
                    "idx=" + idx +
                    ", loop=" + loop +
                    ", idxToLoop=" + idxToLoop +
                    ", pathToLoop=" + pathToLoop +
                    '}';
        }
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
