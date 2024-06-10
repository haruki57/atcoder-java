import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC216D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        List<Integer>[] lists = new List[N];
        for (int i = 0; i < N; i++) {
            lists[i]=new ArrayList<>();
        }
        int[][] t = new int[M][];
        int[] idx = new int[M];
        for (int i = 0; i < M; i++) {
            int k = scanner.nextInt();
            t[i]=new int[k];
            for (int j = 0; j < k; j++) {
                t[i][j]= scanner.nextInt()-1;
            }
        }
        Queue<List<Integer>>q = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            lists[t[i][0]].add(i);
            if (lists[t[i][0]].size()>=2) {
                q.add(lists[t[i][0]]);
            }
        }
        int cnt = 0;
        while(!q.isEmpty()) {
            List<Integer> poll = q.poll();
            int idx1 = poll.get(0);
            int idx2 = poll.get(1);
            //System.out.println(idx1+" "+idx2);
            idx[idx1]++;
            idx[idx2]++;
            if (idx[idx1] < t[idx1].length) {
                lists[t[idx1][idx[idx1]]].add(idx1);
                if (lists[t[idx1][idx[idx1]]].size() >= 2) {
                    q.add(lists[t[idx1][idx[idx1]]]);
                }
            }

            if (idx[idx2] < t[idx2].length) {
                lists[t[idx2][idx[idx2]]].add(idx2);
                if (lists[t[idx2][idx[idx2]]].size() >= 2) {
                    q.add(lists[t[idx2][idx[idx2]]]);
                }
            }
            for (List<Integer> list : lists) {
//                System.out.println(list);
            }
//            System.out.println(q);
            cnt+=2;
        }
        if (cnt == 2*N) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
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
