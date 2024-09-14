import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC157D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            g[x].add(y);
            g[y].add(x);
        }
        List<Integer>[] block = new List[N];
        for (int i = 0; i < block.length; i++) {
            block[i] = new ArrayList<>();
        }
        for (int i = 0; i < K; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            block[x].add(y);
            block[y].add(x);
        }
        int[] answers = new int[N];
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            if(visited[i]) {
                continue;
            }
            visited[i]=true;
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            Set<Integer> set = new HashSet<>();
            set.add(i);
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                for (Integer next : g[cur]) {
                    if(visited[next]) {
                        continue;
                    }
                    visited[next] = true;
                    q.add(next);
                    set.add(next);
                }
            }
            Set<Integer> all = new HashSet<>();
            all.addAll(set);
            for (Integer cur : all) {
                set.removeAll(g[cur]);
                Set<Integer> blocked = new HashSet<>();
                for (Integer b : block[cur]) {
                    if (set.remove(b)) {
                        blocked.add(b);
                    }
                }
                answers[cur] = set.size()-1;
                set.addAll(g[cur]);
                set.addAll(blocked);
            }
        }
        for (int answer : answers) {
            out.print(answer+" ");
        }
        out.println();
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
