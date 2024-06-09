import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC357E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt()-1);
        //System.out.println(Arrays.toString(a));
        boolean[] globalVisited = new boolean[N];
        int[] len = new int[N];
        int[] counts = new int[N];
        for (int i = 0; i < N; i++) {
            if (globalVisited[i]) {
                continue;
            }
            Set<Integer> localVisited = new HashSet<>();
            List<Integer> path = new ArrayList<>();
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{i, 1});
            localVisited.add(i);
            len[i]=1;
            int start = -1;
            int end = -1;
            int count = 1;
            path.add(i);
            while(!q.isEmpty()) {
                int cur = q.peek()[0];
                int dist = q.poll()[1];
                int next = a[cur];
                if(globalVisited[next]) {
                    count += counts[next];
                    end = next;
                    break;
                }
                if (localVisited.contains(next)) {
                    start = next;
                    end = cur;
                    break;
                }
                localVisited.add(next);
                len[next]=dist+1;
                path.add(next);
                q.add(new int[]{next, dist+1});
                count++;
            }
            int size = -1;
            if (start==-1) {
                size = counts[end];
            } else {
                size = Math.abs(len[start]-len[end])+1;
            }
            q.clear();
            q.add(new int[]{i, 1});
            //System.out.println(Arrays.toString(globalVisited));
            //System.out.println(Arrays.toString(len));
            //System.out.println(localVisited);
            //System.out.println(start+" "+end);
            //System.out.println("size:"+size);
            //System.out.println("count:"+count);
            if (start==-1) {
                //System.out.println(path);
                for (Integer cur : path) {
                    counts[cur] = count;
                    count--;
                }
            } else {
                while(!q.isEmpty()) {
                    int cur = q.peek()[0];
                    int dist = q.poll()[1];
                    counts[cur]=Math.max(count,size);
                    int next = a[cur];
                    if (next==end) {
                        counts[next]=Math.max(count-1,size);
                        break;
                    }
                    if (globalVisited[next]) {
                        break;
                    }
                    count--;
                    q.add(new int[]{next, dist+1});
                }

            }
//            System.out.println("counts:" + Arrays.toString(counts));
//            System.out.println();
            for (Integer integer : localVisited) {
                globalVisited[integer]=true;
            }
        }
        long ans = 0;
        for (int count : counts) {
            ans += count;
        }
        //System.out.println(Arrays.toString(counts));
        System.out.println(ans);
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
