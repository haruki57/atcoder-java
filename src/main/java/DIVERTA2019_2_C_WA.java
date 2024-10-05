import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class DIVERTA2019_2_C_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        Arrays.sort(a);
        List<Integer> adds = new ArrayList<>();
        List<Integer> minuses = new ArrayList<>();
        for (int i = 0; i < N / 2; i++) {
            minuses.add(a[i]);
        }
        for (int i = N/2; i < N; i++) {
            adds.add(a[i]);
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N/2; i++) {
            stack.add(adds.get(i));
            stack.add(minuses.get(i));
        }
        if(N%2==1) {
            stack.add(adds.get(adds.size()-1));
        }
        List<int[]> answers = new ArrayList<>();
        for (int i = 0; i < N - 2; i++) {
            Integer p1 = stack.pop();
            Integer p2 = stack.pop();
            answers.add(new int[]{p2, p1});
            stack.add(p2-p1);
        }
        int secondLast = stack.pop();
        int last = stack.pop();
        out.println(last - secondLast);
        answers.add(new int[]{last, secondLast});
        for (int[] answer : answers) {
            out.println(answer[0]+" "+answer[1]);
        }

        /*
        PriorityQueue<Integer> q = new PriorityQueue<>();
        PriorityQueue<Integer> qRe = new PriorityQueue<>((o1, o2) -> o2-o1);
        for (int i = 0; i < N; i++) {
            q.add(a[i]);
            qRe.add(a[i]);
        }
        long ans = 0;
        List<int[]> answers = new ArrayList<>();
        for (int i = 0; i < N - 2; i++) {
            //Integer min = q.poll();
            Integer min = q.poll();
            Integer min2 = q.poll();
            int sum = min-min2;
            answers.add(new int[]{min, min2});
            q.add(sum);
        }
        Integer min = q.poll();
        Integer max = q.poll();
        ans += max-min;
        answers.add(new int[]{max, min});
        out.println(ans);
        for (int[] answer : answers) {
            out.println(answer[0]+" "+answer[1]);
        }

        /*
        for (int i = 0; i < N/2; i++) {
            sum -= a[i];
        }
        for (int i = N/2; i < N; i++) {
            sum += a[i];
        }

         */

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
