import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC342D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static List<Long> bunkai(long  N) {
        long rem = N;
        List<Long> ret = new ArrayList<>();
        for (long i = 2; i * i <= N; i++) {
            while (rem % i == 0) {
                rem /= i;
                ret.add(i);
            }
        }
        if (rem != 1) {
            ret.add(rem);
        }
        return ret;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());

        Map<Long, Long> cntByNum2 = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            List<Long> bunkai = bunkai(a[i]);
            Map<Long, Long> cntByNum = new HashMap<>();
            for (Long l : bunkai) {
                if (!cntByNum.containsKey(l)) {
                    cntByNum.put(l, 0L);
                }
                cntByNum.put(l, cntByNum.get(l)+1);
            }
            long sum = 1;
            for (Map.Entry<Long, Long> longIntegerEntry : cntByNum.entrySet()) {
                Long key = longIntegerEntry.getKey();
                Long num = longIntegerEntry.getValue();
                if (num % 2 == 1) {
                    sum *= key;
                }
            }
            if (!cntByNum2.containsKey(sum)) {
                cntByNum2.put(sum, 0L);
            }
            cntByNum2.put(sum, cntByNum2.get(sum) + 1);
        }
        long cnt = 0;
        for (Map.Entry<Long, Long> longIntegerEntry : cntByNum2.entrySet()) {
            if (longIntegerEntry.getKey() == 0) {
                long value = longIntegerEntry.getValue();
                // (5 + 4 + 3 + 2 + 1) - (3 + 2 + 1)
                // 6*5 / 2             - 4 * 3 / 2
                cnt += ((long)N) * (N-1) /2 - (N-value)*(N-value-1)/2;
                continue;
            }
            long value = longIntegerEntry.getValue();

            cnt += value * (value-1) / 2;
        }
        System.out.println(cnt);
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
