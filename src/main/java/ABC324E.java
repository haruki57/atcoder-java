import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC324E {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] t = scanner.next().toCharArray();

        int[] matchF = new int[N];
        int[] matchB = new int[N];
        /*
        TreeSet<Integer>[] sets = new TreeSet[26];
        for (int i = 0; i < 26; i++) {
            sets[i]=new TreeSet<>();
        }
        for (int i = 0; i < t.length; i++) {
            sets[(t[i]-'a')].add(i);
        }
        for (int i = 0; i < sets.length; i++) {
            System.out.println(sets[i]);
        }

         */
        for (int i = 0; i < N; i++) {
            char[] s =scanner.next().toCharArray();
            int jj = 0;
            int cnt = 0;
            for (int j = 0; j < s.length; j++) {
                if (s[j]==t[jj]) {
                    cnt++;
                    jj++;
                    if (jj>=t.length) {
                        break;
                    }
                }
            }
            matchF[i]=cnt;
            jj = t.length-1;
            cnt = 0;
            for (int j = s.length-1; j >= 0; j--) {
                if (s[j]==t[jj]) {
                    cnt++;
                    jj--;
                    if (jj<0){
                        break;
                    }
                }
            }
            matchB[i]=cnt;
        }
        Arrays.sort(matchF);
        Arrays.sort(matchB);

        /*
        for (int i = 0; i < matchF.length/2; i++) {
            int tmp = matchF[i];
            matchF[i] = matchF[matchF.length - i - 1];
            matchF[matchF.length - i - 1] = tmp;
        }

         */
        //System.out.println(Arrays.toString(matchF));
        //System.out.println(Arrays.toString(matchB));
        long ans = 0;
        for (int i = 0; i < matchF.length; i++) {
            int ok = matchB.length, ng = -1;
            while(Math.abs(ok-ng)>1) {
                int mid = (ok+ng)/2;
                if (matchF[i]+matchB[mid] >= t.length) {
                    ok = mid;
                } else {
                    ng = mid;
                }
            }
            if (ok != matchB.length) {
                ans += matchB.length - ok;
            }
            //System.out.println(ok);
            /*
            while(ok >= 0 && matchF[i]+matchB[ok] >= t.length) {
                ok--;
            }
            ans += matchB.length-ok+1;
            System.out.println(i+" "+ok+" "+ans);
             */
        }
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
