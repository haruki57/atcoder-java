import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ABC270E_TLE {
    static int MOD = 998244353;
    static long INF = Long.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long K = scanner.nextLong();
        long[] a = new long[N];
        Arrays.setAll(a, i -> scanner.nextLong());
        long[] sorted = new long[N+1];
        for (int i = 0; i < a.length; i++) {
            sorted[i]=a[i];
        }
        sorted[sorted.length-1]=INF;
        Arrays.sort(sorted);
        PriorityQueue<Long> q = new PriorityQueue<>();
        for (int i = 0; i < a.length; i++) {
            q.add(a[i]);
        }
        int idx = 0;
        long minus = 0;
        long restN = N;
        long restK = K;
        while(true) {
            long min = q.poll();
            int originalIdx = idx;
            for (; idx < sorted.length; idx++) {
                if (min < sorted[idx]) {
                    break;
                }
            }
            int cnt = (idx - originalIdx);
            min = min - minus;
            for (int i = 0; i < cnt - 1; i++) {
                q.poll();
            }
            minus += min;
            restK -= restN * min;
            if (restK < 0) {
                restK += restN * min;
                while(restK > 0){
                    System.out.println("final:" + restN+" "+restK);
                    for (int i = 0; i <N; i++) {
                        if (a[i]<=0) {
                            continue;
                        }
                        a[i]--;
                        restK--;
                        if (restK <= 0) {
                            break;
                        }
                    }
                }
                for (int i = 0; i < N; i++) {
                    out.print(Math.max(a[i], 0)+" ");
                }
                out.println();
                return;
            }
            restN -= cnt;
            for (int i = 0; i < N; i++) {
                a[i]-=min;
            }
            //System.out.println(restN+" "+restK);
            //System.out.println(Arrays.toString(a));
            //System.out.println(cnt);
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
