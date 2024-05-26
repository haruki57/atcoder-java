import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC252D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());
        Arrays.sort(a);

        int[] cnts = new int[2*100000+9];
        //int[] cnts = new int[20];

        int[] sum = new int[cnts.length];
        for (int i = 0; i < a.length; i++) {
            cnts[a[i]]++;
        }
        sum[0]=cnts[0];
        for (int i = 1; i < cnts.length; i++) {
            sum[i]=sum[i-1]+cnts[i];
        }
        int first = -1, last=-1;
        for (int i = 0; i < cnts.length; i++) {
            if (cnts[i]>0) {
                first=i;
                break;
            }
        }
        for (int i = cnts.length-1; i >= 0; i--) {
            if (cnts[i]>0) {
                last=i;
                break;
            }
        }
        //System.out.println(first+" "+last);
        long ans = 0;
        for (int i = first+1; i < last; i++) {

            ans += (long)(sum[i-1])*(sum[sum.length-1]-sum[i])*cnts[i];
//            System.out.println(i+" "+cnts[i]+" "+ans);
        }
        System.out.println(ans);
                        /*
                        int[] sum2 = new int[cnts.length];
        sum2[0]=cnts[0]*cnts[0];
        for (int i = 1; i < cnts.length; i++) {
            sum2[i]=sum2[i-1]+cnts[i]*cnts[i];
        }
        System.out.println(Arrays.toString(cnts));
        System.out.println(Arrays.toString(sum));
        System.out.println(Arrays.toString(sum2));
        long ans = 0;
        for (int i = 0; i < N; i++) {
            int rest = N-i-1;
            int combi = rest*(rest-1)/2;
            long sum1sum = sum[sum.length-1]-sum[i+1];
            int sum2sum = sum2[sum2.length-1]-sum2[i+1];
            //ans += (sum1sum*sum1sum - sum2sum)/2*a[i];
            ans += cnts[i]*(sum1sum*sum1sum-sum2sum);
            System.out.println(ans);
            System.out.println(cnts[i]+" "+sum2sum+" "+sum1sum+" "+combi);
        }

        System.out.println(ans);
        System.out.println();

        long ansTEL = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    if (a[i]==a[j]||a[j]==a[k]) {
                        continue;
                    }
                    ansTEL++;
                }
            }
            System.out.println(i+" "+ansTEL);
        }
        System.out.println(ansTEL);

        for (int i = 0; i < N; i++) {
            int j = -1;
            {
                int ok = N, ng = i;
                while (Math.abs(ok - ng) > 1) {
                    int mid = (ok + ng) / 2;
                    if (a[mid] != a[i]) {
                        ok = mid;
                    } else {
                        ng = mid;
                    }
                }
                if (ok==N) {
                    continue;
                }
                j = ok;
            }
            int k = -1;
            {
                int ok = N, ng = j;
                while (Math.abs(ok - ng) > 1) {
                    int mid = (ok + ng) / 2;
                    if (a[mid] != a[j]) {
                        ok = mid;
                    } else {
                        ng = mid;
                    }
                }
                if (ok==N) {
                    continue;
                }
                j = ok;
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
