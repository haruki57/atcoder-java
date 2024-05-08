import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC346D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        int[] costs = new int[N];
        Arrays.setAll(costs, i -> scanner.nextInt());
        long[] sum0 = new long[N+1];
        long[] sum0re = new long[N+1];
        long[] sum1 = new long[N+1];
        long[] sum1re = new long[N+1];

        for (int i = 0; i < N; i++) {
            char c0 = '0', c1 = '1';
            if (i%2==1) {
                char tmp = c0;
                c0 = c1;
                c1 = tmp;
            }
            // 0101...
            sum0[i+1]=sum0[i]+(s[i]==c0?0:costs[i]);
            // 1010...
            sum1[i+1]=sum1[i]+(s[i]==c1?0:costs[i]);

        }

        for (int i = N-1; i >= 0; i--) {
            char c0 = '0';
            char c1 = '1';
            if (((N-(i+2))%2)==0) {
                char tmp = c0;
                c0 = c1;
                c1 = tmp;
            }
            // ...1010
            sum0re[i]=sum0re[i+1]+(s[i]==c0?0:costs[i]);
            // ...0101
            sum1re[i]=sum1re[i+1]+(s[i]==c1?0:costs[i]);
        }

        /*
        System.out.println(Arrays.toString(sum0));
        System.out.println(Arrays.toString(sum1));
        System.out.println(Arrays.toString(sum0re));
        System.out.println(Arrays.toString(sum1re));

         */


        long ans = (long)INF*INF;
        for (int i = 0; i < N-1; i++) {
            long[] sum, sumRe;
            // ....00....
            if (i%2==0) {
                sum=sum0;
            } else {
                sum=sum1;
            }
            if ((N-(i+2))%2==0) {
                sumRe=sum0re;
            } else {
                sumRe=sum1re;
            }
            long l = (s[i]=='0'?0:costs[i]);
            l += (s[i+1]=='0'?0:costs[i+1]);
            //System.out.println(l);
            //System.out.println(i+" "+sum[i]);
            //System.out.println((i+2)+" "+sumRe[i+2]);
            //System.out.println();
            ans = Math.min(ans, sum[i]+l+sumRe[i+2]);

            // ....11....
            if (sum == sum0) {
                sum = sum1;
            } else {
                sum = sum0;
            }
            if (sumRe == sum0re) {
                sumRe = sum1re;
            } else {
                sumRe = sum0re;
            }

            l = (s[i]=='1'?0:costs[i]);
            l += (s[i+1]=='1'?0:costs[i+1]);
            ans = Math.min(ans, sum[i]+l+sumRe[i+2]);
        }
        System.out.println(ans);
    }

    private static long calc(char[] s, char[] good, int[] costs) {
        long ret = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]!=good[i]) {
                ret += costs[i];
            }
        }
        return ret;
    }

    private static void makeGood(char[] good, int x, int zeroOrOne) {
        good[x]=good[x+1]=(char)('0'+zeroOrOne);
        char c = zeroOrOne==0?'1':'0';
        for (int i = x-1; i >= 0; i--) {
            good[i]=c;
            c = (char)('1'-c);
        }
        c = zeroOrOne==0?'1':'0';
        for (int i = x+2; i < good.length; i++) {
            good[i]=c;
            c = (char)('1'-c);
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
