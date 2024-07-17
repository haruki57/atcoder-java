import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC357E_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt()-1);
        int[] answers = new int[N];
        for (int i = 0; i < N; i++) {
            if (answers[i] > 0) {
                continue;
            }
            Set<Integer> visited=new HashSet<>();
            visited.clear();
            visited.add(i);
            int cur = i;
            int ans = 1;
            //Stack<Integer>path=new Stack<>();
            Queue<Integer>path=new LinkedList<>();
            path.add(cur);
            int kanStart = -1;
            while (true) {
                cur = a[cur];
                if (visited.contains(cur)) {
                    kanStart=cur;
                    break;
                }
                visited.add(cur);
                path.add(cur);
                if (answers[cur]>0) {
                    ans+=answers[cur];
                    break;
                } else {
                    ans++;
                }
            }
            boolean inKan = false;
            while(!path.isEmpty()) {
                int poll = path.poll();
                //System.out.println(poll);
                if (poll==kanStart) {
                    inKan=true;
                }
                if (answers[poll]>0){
                    continue;
                }

                answers[poll]=ans;
                if (!inKan) {
                    ans--;
                }

            }
            //System.out.println();

        }
        long sum = 0;
        for (int answer : answers) {
            sum+=answer;
            //out.print(answer+" ");
        }
        //out.println();


        out.println(sum);
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
