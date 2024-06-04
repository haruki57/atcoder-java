import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC222C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        char[][] hands = new char[N*2][];
        for (int i = 0; i < hands.length; i++) {
            hands[i]=scanner.next().toCharArray();
        }
        PriorityQueue<int[]> q = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1]==o2[1]) {
                    return o1[0]-o2[0];
                }
                return o2[1]-o1[1];
            }
        });
        for (int i = 0; i < hands.length; i++) {
            q.add(new int[]{i, 0});
        }
        int[] wins = new int[N*2];
        for (int i = 0; i < M; i++) {
            List<int[]> listToAdd = new ArrayList<>();
            while(!q.isEmpty()) {
                int[] a = q.poll();
                int[] b = q.poll();
                char aa = hands[a[0]][i];
                char bb = hands[b[0]][i];
                if (aa==bb) {
                    //continue;
                } else
                if (aa=='G') {
                    if (bb=='C') {
                        wins[a[0]]++;
                    } else {
                        wins[b[0]]++;
                    }
                } else if (aa=='C') {
                    if (bb=='P') {
                        wins[a[0]]++;
                    } else {
                        wins[b[0]]++;
                    }
                }else if (aa=='P') {
                    if (bb=='G') {
                        wins[a[0]]++;
                    } else {
                        wins[b[0]]++;
                    }
                }
                listToAdd.add(new int[]{a[0], wins[a[0]]});
                listToAdd.add(new int[]{b[0], wins[b[0]]});
            }
            for (int[] ints : listToAdd) {
                q.add(ints);
            }
            //out.println(Arrays.toString(wins));
            for (int[] ints : q) {
                //out.println(ints[0]+1);
            }
            //out.println();
        }
        while(!q.isEmpty()){
            int[] polled = q.poll();
            out.println(polled[0]+1);
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
