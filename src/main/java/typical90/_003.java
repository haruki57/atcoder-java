package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class _003 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        List<Integer>[] graph = new List[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            graph[a].add(b);
            graph[b].add(a);
        }
        int[] len = new int[N];
        Arrays.fill(len, INF);
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        len[0] = 0;
        int longest = -1;
        int longestIdx = 0;

        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int cur = polled[0];
            int dist = polled[1];
            for (Integer next : graph[cur]) {
                if (dist + 1 < len[next]) {
                    q.add(new int[]{next, dist+1});
                    len[next] = dist + 1;
                    if (dist + 1 > longest) {
                        longest = dist + 1;
                        longestIdx = next;
                    }
                }
            }
        }
        // System.out.println(longest);

        Arrays.fill(len, INF);
        q.add(new int[]{longestIdx, 0});
        len[longestIdx] = 0;
        while(!q.isEmpty()) {
            int[] polled = q.poll();
            int cur = polled[0];
            int dist = polled[1];
            for (Integer next : graph[cur]) {
                if (dist + 1 < len[next]) {
                    q.add(new int[]{next, dist+1});
                    len[next] = dist + 1;
                    if (dist + 1 > longest) {
                        longest = dist + 1;
                        longestIdx = next;
                    }
                }
            }
        }
        System.out.println(longest + 1);


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
