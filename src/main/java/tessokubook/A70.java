package tessokubook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class A70 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int start = 0;
        int[] shortest = new int[1<<N + 9];
        Arrays.fill(shortest, INF);
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();
            if (a == 1) {
                start = start | 1 << i;
            }
        }
        int[][] xyz = new int[M][3];
        for (int i = 0; i < xyz.length; i++) {
            xyz[i][0] = scanner.nextInt()-1;
            xyz[i][1] = scanner.nextInt()-1;
            xyz[i][2] = scanner.nextInt()-1;
        }
        shortest[start] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, 0});
        while(!queue.isEmpty()){
            int[] polled = queue.poll();
            int curr = polled[0];
            int dist = polled[1];
            for (int i = 0; i < M; i++) {
                int next = curr ^ (1<<xyz[i][0]) ^ (1<<xyz[i][1]) ^ (1<<xyz[i][2]);
                if (shortest[next] != INF) {
                    continue;
                }
                shortest[next] = dist + 1;
                queue.add(new int[]{next, dist+1});
            }
        }
        System.out.println(shortest[(1<<N)-1] == INF ? -1 : shortest[(1<<N)-1]);
    }

    public static void main(final String[] args) {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner scanner = new FastScanner();
        run(scanner, out);
        out.flush();
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
