import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC302F_TLE {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        Set<Integer>[] sets = new Set[N];
        Map<Integer, List<Integer>> es = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < a; j++) {
                int b = scanner.nextInt()-1;
                set.add(b);
                List<Integer> list = es.getOrDefault(b, new ArrayList<>());
                list.add(i);
                es.put(b, list);
            }
            sets[i]=set;
        }
        int[] dist = new int[N];
        Arrays.fill(dist, INF);
        List<Integer> starts = es.getOrDefault(0, new ArrayList<>());
        Queue<int[]> q = new LinkedList<>();
        for (Integer start : starts) {
            dist[start] = 0;
            q.add(new int[]{start, 0});
        }
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int cur = polled[0];
            int d = polled[1]+1;
            //System.out.println(cur);
            //System.out.println(sets[cur]);
            //System.out.println();
            for (Integer i : sets[cur]) {
                if (i==M-1) {
                    System.out.println(d-1);
                    return;
                }
            }
            for (Integer i : sets[cur]) {
                for (Integer next : es.get(i)) {
                    if (dist[next] > d) {
                        dist[next] = d;
                        q.add(new int[]{next, d});
                    }
                }

            }
        }
        System.out.println(-1);
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
