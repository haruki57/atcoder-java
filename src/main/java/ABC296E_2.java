import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC296E_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt()-1);
        int ans = 0;
        boolean[] inLoop = new boolean[N];
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            if(visited[i]) {
                continue;
            }
            visited[i]=true;
            Set<Integer> localVisited = new HashSet<>();
            Stack<Integer> path = new Stack<>();
            int cur = i;
            localVisited.add(cur);
            path.add(cur);
            int last = -1;
            while(true) {
                int next = a[cur];
                if (localVisited.contains(next)) {
                    last = next;
                    break;
                }
                if (visited[next]) {
                    break;
                }

                localVisited.add(next);
                visited[next]=true;
                path.add(next);
                cur=next;
            }

            /*
            System.out.println(i);
            System.out.println(last);
            System.out.println(localVisited);
            System.out.println(path);

             */

            while(last!=-1&&!path.isEmpty()) {
                int p = path.pop();
                inLoop[p]=true;
//                System.out.println("p:"+p);
                if (last==p) {
                    break;
                }

            }
        }
        //System.out.println(Arrays.toString(inLoop));
        for (int i = 0; i < N; i++) {
            if(inLoop[i]) {
                ans++;
            }
        }
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
