import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC349D {
    static int MOD = 1000000007;
    static long INF = Long.MAX_VALUE/2;

    static List<Long> twos = new ArrayList<>();
    static List<long[]> answers = new ArrayList<>();
    static void run (final FastScanner scanner, final PrintWriter out) {
        long L = scanner.nextLong();
        long R = scanner.nextLong();
        for (int i = 0; i < 62; i++) {
            twos.add((long)Math.pow(2, i));
        }
        Collections.reverse(twos);
        //System.out.println(twos);
        rec(L, R);
        out.println(answers.size());
        for (long[] answer : answers) {
            out.println(answer[0] + " " + answer[1]);
        }
    }

    private static void rec(long L, long R) {
        //System.out.println(L + " " + R);
        if (L >= R) {
            return;
        }
        for (Long two : twos) {
            if (R-L < two) {
                continue;
            }
//            System.out.println(two);
            long ok = INF, ng=-1;
            while(Math.abs(ok-ng) > 1) {
                long mid = (ok+ng)/2;
                if (two*mid >= L) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
//            System.out.println(two*ok + " " + two*(ok+1));
            if (L <= two*ok && two*(ok+1) <= R) {
                rec(L, two*ok);
                answers.add(new long[]{two*ok, two*(ok+1)});
                rec(two*(ok+1), R);
                return;
            }


            /*
            for (int i = 0; i < 10000000; i++) {
                if (two*i > R) {
                    break;
                }
                //System.out.println(two*i + " "+ two*(i+1));
                if (L <= two*i && two*(i+1) <= R) {
                    rec(L, two*i);
                    answers.add(new long[]{two*i, two*(i+1)});
                    rec(two*(i+1), R);
                    return;
                }

            }

             */
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
