import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC323C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[] score = new int[M];
        Arrays.setAll(score, i -> scanner.nextInt());
        int[][] solved = new int[N][M];
        int[] sum = new int[N];
        for (int i = 0; i < N; i++) {
            String s = scanner.next();
            sum[i] = i+1;
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j)=='o') {
                    solved[i][j]=1;
                    sum[i]+=score[j];
                }
            }
        }
        for (int i = 0; i < N; i++) {
            int max = 0;
            int currentScore = sum[i];
            for (int j = 0; j < N; j++) {
                if (i==j) continue;
                max = Math.max(sum[j],max);
            }
            if (max < currentScore) {
                out.println(0);
                continue;
            }
            List<Integer> notSolvedScores = new ArrayList<>();
            for (int j = 0; j < M; j++) {
                if (solved[i][j]==0) {
                    notSolvedScores.add(-score[j]);
                }
            }
            Collections.sort(notSolvedScores);
            int cnt = 0;
            for (Integer notSolvedScore : notSolvedScores) {
                notSolvedScore = -notSolvedScore;
                currentScore += notSolvedScore;
                cnt++;
                if (max < currentScore) {
                    out.println(cnt);
                    break;
                }
            }
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
