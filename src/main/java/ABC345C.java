import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ABC345C {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        char[] s = scanner.next().toCharArray();
        int[] cnt = new int[26];
        long ans = 0;
        for (int i = 0; i < s.length; i++) {
            cnt[s[i]-'a']++;
            if (cnt[s[i]-'a']>=2) {
                ans=1;
            }
        }

        int[][] sum = new int[s.length+1][26];
        for (int i = 1; i <= s.length; i++) {
            for (int j = 0; j < 26; j++) {
                sum[i][j] = sum[i-1][j] + (s[i-1]-'a'==j ? 1:0);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < sum.length; j++) {
                //System.out.print(sum[j][i] + " ");
            }
            //System.out.println();
        }
        for (int i = 0; i < s.length-1; i++) {
            for (int j = 0; j < 26; j++) {
                if (s[i]-'a'==j) {continue;}
                ans += sum[s.length][j]-sum[i][j];
            }
        }
        System.out.println(ans);
/*
        Set<String> set = new HashSet<>();
        for (int i = 0; i < s.length; i++) {
            for (int j = i+1; j < s.length; j++) {
                char tmp = s[i];
                s[i]=s[j];
                s[j]=tmp;
                set.add(String.valueOf(s));
                tmp = s[i];
                s[i]=s[j];
                s[j]=tmp;
            }
        }
        System.out.println(set.size());
 */
    }

    public static void main(final String[] args) {
        PrintWriter out = new PrintWriter(System.out);
        FastScanner scanner = new FastScanner();
        run(scanner, out);
        out.flush();
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
