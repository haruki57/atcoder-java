import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC146F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] a = new int[N+1];
        char[] s = scanner.next().toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i]=s[i]-'0';
        }
        long[] dp = new long[a.length];
        Arrays.fill(dp, INF);
        dp[0]=0;
        for (int i = 0; i < dp.length;) {
            long cur = dp[i];
            int nextI = i;
            for (int j = 0; j < M; j++) {
                int jj = i+j+1;
                if (jj >= a.length) {
                    continue;
                }
                if (a[jj]==1) {
                    continue;
                }
                nextI = jj;
                dp[jj]=cur+1;
            }
            if (nextI == i) {
                break;
            }
            i = nextI;
        }
        //System.out.println(Arrays.toString(dp));
        if (dp[dp.length-1] >= INF) {
            System.out.println(-1);
            return;
        }
        List<Integer> answers = new ArrayList<>();
        for (int i = dp.length-1; i > 0; ) {
            long cur =dp[i];
            int originalI = i;
            i--;
            int last = i;
            int cnt = 0;
            while(i>=0) {
                if (dp[i]==cur-1) {
                    last = i;
                }
                if (dp[i]==0) {
                    break;
                }
                if (dp[i]==cur-2) {
                    break;
                }
                i--;
                cnt++;
                if (cnt>=M) {
                    break;
                }
            }
            answers.add(originalI-last);
            i=last;
//            System.out.println(answers);
//            System.out.println(i);
        }
        Collections.reverse(answers);
        for (Integer answer : answers) {
            out.print(answer+" ");
        }
        out.println();
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
