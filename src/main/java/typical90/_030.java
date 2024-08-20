package typical90;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class _030 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static int N, K;
    static Set<Long> set = new HashSet<>();
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        K = scanner.nextInt();
        System.out.println(rec(1, -1, 0));
        //System.out.println(set.size());
        /*
        long ans = 0;
        for (int i = 2; i <= N; i++) {
            if (bunkai(i).size() >= K) {
                ans++;
            }
        }
        System.out.println(ans);

         */
    }

    static long rec(long cur, int min, int unique) {
        long ret = 0;
        //System.out.println(cur +" "+unique);
        if(set.contains(cur)){
            //System.out.println("dup " + cur);
        }
        //set.add(cur);

        if(unique >= K) {
            ret++;
        }
        for (int j = Math.max(min, 0); j < primes.size(); j++) {
            int i = primes.get(j);
            long next = cur*i;
            if(next > N) {
                break;
            }
            if(j>min) {
                ret += rec(next, j, unique+1);
            } else {
                ret += rec(next, j, unique);
            }
        }
        return ret;
    }

    static List<Integer> primes = new ArrayList<>();
    static boolean[] isPrime = new boolean[10000000+9];
    static int[] minFactor = new int[10000000+9];

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
