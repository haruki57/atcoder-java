import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class ARC182B {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        /*
        {
            System.out.println(score(new int[]{3, 5}, 3));
            System.out.println(score(new int[]{4,5,6}, 3));
            System.out.println(score(new int[]{1,2,3}, 2));
            System.out.println(score(new int[]{662933, 967505, 876482, 840117, 1035841, 651549, 543175, 781219}, 20));
            int[] a = new int[8];
            for (int i = 0; i < a.length; i++) {
                a[i]=(1<<19)|(i<<16);
                //System.out.println(Integer.toBinaryString(1<<19)+" "+Integer.toBinaryString(i<<18));
                //System.out.println(Integer.toBinaryString((1<<19)|(i<<18)));
            }
            System.out.println(score(a, 20));

            int[] ar = new int[]{662933, 967505, 876482, 840117, 1035841, 651549, 543175, 781219};
            Arrays.sort(ar);
            for (int i = 0; i < ar.length; i++) {
                for (int j = 0; j < 25; j++) {
                    if((1<<j) <= ar[i] && ar[i] <= (1<<(j+1))) {
                        //System.out.println(j+" "+Integer.toBinaryString(ar[i]));

                    }
                }
            }
        }
         */

        int T = scanner.nextInt();
        while(T-->0) {
            int N = scanner.nextInt();
            int K = scanner.nextInt();
            if((1<<K)-1 <= N) {
                for (int i = 0; i < N; i++) {
                    out.print(Math.max(1, ((1<<K)-(i+1)))+" ");
                }
                out.println();
                continue;
            }
            int k = 0;
            while(N > (1<<k)) {
                k++;
            }
            int[] a = new int[N];
            for (int i = 0; i < N; i++) {
                //out.print(Integer.toBinaryString(a[i])+" ");

                var sb = new StringBuilder(Integer.toBinaryString(i));
                while(sb.length() < K-1) {
                    sb.insert(0, 0);
                }
                //System.out.println("sb="+sb);
                int ii = Integer.parseInt(sb.reverse().toString(), 2);
                //System.out.println(ii+" "+Integer.toBinaryString(ii));
                //a[i]=((1<<(K-1))|(ii<<(K-k-1)));
                a[i]=((1<<(K-1))|(ii));
                out.print(a[i]+" ");

            }
            out.println();
            for (int i = 0; i < a.length; i++) {
                //System.out.println(Integer.toBinaryString(a[i]));
            }
            //System.out.println("score="+score(a, K));
        }

    }

    static long score(int[] a, int k) {
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j <= k; j++) {
                set.add(a[i] / (1<<j));
            }
        }
        //System.out.println(set);
        return set.size();
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
