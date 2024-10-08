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
        if(K==0) {
            for (int i = 0; i < a.length; i++) {
                out.print(a[i]+" ");
            }
            out.println();
            return;
        }

        Node[] nodes = functionalGraph(x);
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
                if (distToLoop > K) {
                    long size = n.pathToLoop.size();
                    size -= distToLoop;
                    size += K;
                    int idx = n.pathToLoop.get((int)size);
                    out.print(a[idx]+" ");
                } else {
                    long KK = K-distToLoop;
                    long idx = ((long)n.idx + KK + n.loop.size()) % n.loop.size();
                    out.print(a[n.loop.get((int)idx)]+" ");
                }
            }
        }
    }

    private static Node[] functionalGraph(int[] x) {
        int N = x.length;
        int[] inCnt = new int[N];
        for (int i = 0; i < x.length; i++) {
            inCnt[x[i]]++;
        }
        List<Integer> inZeros = new LinkedList<>();
        for (int i = 0; i < inCnt.length; i++) {
            if(inCnt[i]==0) {
                inZeros.add(i);
            }
        }
        Set<Integer> visited = new HashSet<>();
        Node[] nodes = new Node[N];
        for (Integer i : inZeros) {
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
            if(loopStart==-1) {
                // existing loop
                var loop = nodes[lastVisited].loop;
                int i0=0;
                for (Integer p : path) {
                    Node n = new Node();
                    nodes[p]=n;
                    n.pathToLoop = path;
                    n.idxToLoop = n.pathToLoop.size() - i0++;
                    n.loop = loop;
                    n.idx = nodes[lastVisited].idx;
                }
            } else {
                List<Integer> pathToLoop = new ArrayList<>();
                for (Integer integer : path) {
                    if(integer==lastVisited){
                        break;
                    }
                    pathToLoop.add(integer);
                }
                List<Integer> loop = new ArrayList<>();
                for (int j = pathToLoop.size(); j < path.size(); j++) {
                    loop.add(path.get(j));
                }
                int i0 = 0;
                for (Integer p : pathToLoop) {
                    Node n = new Node();
                    nodes[p]=n;
                    n.pathToLoop = pathToLoop;
                    n.idxToLoop = n.pathToLoop.size() - i0++;
                    n.loop = loop;
                    n.idx = 0;
                }
                i0=0;
                for (Integer p : loop) {
                    Node n = new Node();
                    nodes[p]=n;
                    n.loop = loop;
                    n.idx = i0++;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if(visited.contains(i)) {
                continue;
            }
            visited.add(i);
            Queue<Integer> q = new LinkedList<>();
            List<Integer> path = new ArrayList<>();
            q.add(i);
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                int next = x[cur];
                path.add(cur);
                visited.add(cur);
                if(visited.contains(next)) {
                    break;
                }
                q.add(next);
            }
            int i0 = 0;
            for (Integer p : path) {
                Node n = new Node();
                nodes[p] = n;
                n.idx = i0++;
                n.loop = path;
            }

        }
        return nodes;
    }

    private static class Node {
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
