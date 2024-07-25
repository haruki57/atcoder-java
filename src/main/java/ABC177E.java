import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC177E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static List<Integer> primes = new ArrayList<>();
    static boolean[] isPrime = new boolean[1000000+9];
    static int[] minFactor = new int[1000000+9];

    static {
        Arrays.fill(isPrime, true);
        isPrime[0]=isPrime[1]=false;
        minFactor[0]=0;
        minFactor[1]=1;
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                minFactor[i]=i;
                for (int j = i+i; j < isPrime.length; j+=i) {
                    isPrime[j]=false;
                    if (minFactor[j] == 0) {
                        minFactor[j]=i;
                    }

                }
            }
        }
        for (int i = 0; i < isPrime.length; i++) {
            if(isPrime[i]){
                primes.add(i);
            }
        }
    }

    static Set<Integer> bunkai(long N) {
        Set<Integer> ret = new HashSet<>();

        if (isPrime[(int)N]) {
            ret.add((int)N);
            return ret;
        }
        int tmp = (int)N;
        while(tmp > 1) {
            int fact = minFactor[tmp];
            ret.add((int)fact);
            tmp /= fact;
        }
        return ret;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        long allGcd = gcd(a[0], a[1]);
        for (int i = 2; i < N; i++) {
            allGcd = gcd(allGcd, a[i]);
        }
        boolean isPairwise = true;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            for (Integer l : bunkai(a[i])) {
                if (set.contains(l)) {
                    isPairwise=false;
                }
                set.add(l);
            }
        }
        if (isPairwise){
            System.out.println("pairwise coprime");
        } else if(allGcd == 1 ){
            System.out.println("setwise coprime");
        } else {
            System.out.println("not coprime");
        }
    }


    static long gcd(long x, long y) {
        if (x<y) {
            long tmp = x;
            x = y;
            y = tmp;
        }
        if (x%y==0) {
            return y;
        }

        return gcd(y, x % y);
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
