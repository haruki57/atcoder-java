import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC114D {
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
    static void run (final FastScanner scanner, final PrintWriter out){
        /*
        boolean isPrime[] = new boolean[101];
        Arrays.fill(isPrime, true);
        isPrime[0]=isPrime[1]=false;
        isPrime[2]=true;
        for (int i = 0; i * i<= isPrime.length; i++) {
            if(isPrime[i]) {
                for (int j = i+i; j < isPrime.length; j+=i) {
                    isPrime[j]=false;
                }
            }
        }
        int cnt = 0;
        //long ll = 11L*11*5*5*7*7*7*7*11*11;
        long ll = 32400;
        for (int j = 1; j <= ll; j++) {
            if (ll%j==0) {
                System.out.println(bunkai(j));
                cnt++;
            }
            if (cnt>=76){
                break;
            }
        }
        System.out.println(ll+" "+cnt);
*/
        /*
        for (int i = 1; i < 1000000000; i++) {
            int cnt = 0;
            for (int j = 1; j < i + 1; j++) {
                if (i%j==0) {
                    cnt++;
                }
                if (cnt>=76){
                    break;
                }
            }
            if (cnt==75) {
                System.out.println(i+" "+cnt);
                System.out.println((bunkai(i)));
            }
        }
         */
        //2        2        2        3        3        5
        int N = scanner.nextInt();
        int[] cnts = new int[101];// 2, 3, 5
        for (int i = 1; i <= N; i++) {
            List<Long> bunkai = bunkai(i);
            for (long l : bunkai) {
                cnts[(int)l]++;
            }
        }
        //System.out.println(Arrays.toString(cnts));
        long more74 = 0;
        long more24 = 0;
        long more14 = 0;
        long more4 = 0;
        long more2 = 0;
        for (int i = 0; i < cnts.length; i++) {
            if (cnts[i]>=74) {
                more74++;
            }
            if (cnts[i]>=24) {
                more24++;
            }
            if (cnts[i]>=14) {
                more14++;
            }
            if (cnts[i]>=4) {
                more4++;
            }
            if (cnts[i]>=2) {
                more2++;
            }
        }
        long ans = 0;
        //System.out.println(more24+" "+more14+" "+more4+" "+more2);
        ans += more4 * (more4-1) / 2 * (more2-2);
        ans += more14 * (more4-1);
        ans += more24 * (more2-1);
        ans += more74;
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
