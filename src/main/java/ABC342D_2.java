import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC342D_2 {
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
        int[] cnts = new int[2*100000+9];
        for (int i = 0; i < N; i++) {
            if (a[i]==0) {
                cnts[0]++;
                continue;
            }
            List<Long> bunkai = bunkai(a[i]);
            Long[] array = bunkai.toArray(new Long[]{});
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j].equals(array[j+1])) {
                    array[j]=array[j+1]=-1L;
                }
            }
            long l = 1;
            for (Long aLong : array) {
                if(aLong!=-1) {
                    l*=aLong;
                }
            }
            cnts[(int)l]++;
        }
        long ans = (long)N*(N-1)/2 - (long)(N-cnts[0]-1)*(N-cnts[0])/2;
        //System.out.println(Arrays.toString(cnts));
        //System.out.println(ans);
        for (int i = 1; i < cnts.length; i++) {
            ans += (long)(cnts[i])*(cnts[i]-1)/2;
        }
        System.out.println(ans);
        /*
        for (int i = 0; i < N; i++) {
            cnts[a[i]]++;
        }
        Set<Long> heihou = new HashSet<>();
        for (long i = 0; i < 2 * 100000 + 9; i++) {
            heihou.add(i*i);
        }
        for (int i = 0; i < cnts.length; i++) {
            if (cnts[i]==0) {
                continue;
            }
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
