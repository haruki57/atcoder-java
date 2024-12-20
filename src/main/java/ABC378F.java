import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC378F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        List<Integer>[] g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            g[x].add(y);
            g[y].add(x);
        }
        long ans = 0;
        Set<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {
            if (g[i].size()!=3) {
                continue;
            }
            if(seen.contains(i)) {
                continue;
            }
            Set<Integer> set3 = new HashSet<>();
            Set<Integer> set2 = new HashSet<>();
            set3.add(i);
            seen.add(i);
            Queue<Integer> q = new LinkedList<>();
            q.add(i);
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                for (Integer next : g[cur]) {
                    if(seen.contains(next)) {
                        continue;
                    }
                    if (g[next].size()!=3) {
                        continue;
                    }
                    set3.add(next);
                    seen.add(next);
                    q.add(next);
                }
            }
            //printSet(set3);
            for (Integer cur : set3) {
                for (Integer next : g[cur]) {
                    if(g[next].size()==2){
                        set2.add(next);
                    }
                }
            }
            ///printSet(set2);
            //System.out.println();
            long size = set2.size();
            ans += ((size-1)*size)/2;
        }
        System.out.println(ans);

    }

    private static void printSet(Set<Integer> set3) {
        for (Integer i : set3) {
            System.out.print(i+1+" ");
        }
        System.out.println();
    }


    private static long rec(List<Integer>[] g, int cur, int prev, Set<Integer> seen) {
        long ret = 0;
        for (Integer next : g[cur]) {
            if(next==prev) {
                continue;
            }
            if(g[next].size()==2) {
                if(prev==-1) {
                    continue;
                }
                ret++;
            }
            if(g[next].size()!=3) {
                continue;
            }
            seen.add(next);
            ret += rec(g, next, cur, seen);
            seen.remove(next);
        }
        return ret;
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
