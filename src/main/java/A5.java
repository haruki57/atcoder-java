import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class A5 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static String NO = "UNSOLVABLE";

    static char[] s1, s2, s3;
    static int[] map = new int[256];
    static void run (final FastScanner scanner, final PrintWriter out) {
        s1 = new StringBuilder(scanner.next()).reverse().toString().toCharArray();
        s2 = new StringBuilder(scanner.next()).reverse().toString().toCharArray();
        if (s1.length > s2.length) {
            var temp = s1;
            s1 = s2;
            s2 = temp;
        }
        s3 = new StringBuilder(scanner.next()).reverse().toString().toCharArray();
        if (rec(0, 0, new boolean[10])) {
            System.out.println("Yes");
        } else {
            System.out.println(NO);
        }
    }

    static boolean rec(int digit, int kuriagari, boolean[] used) {
        if (digit >= s1.length) {
            for (int i = 'a'; i <= 'z'; i++) {
                System.out.print(map[i]+" ");
            }
            System.out.println();
            return true;
        }
        for (int i = 0; i < 10; i++) {
            if (i==0&&digit == s1.length-1)continue;
            if (used[i])continue;
            for (int j = 0; j < 10; j++) {
                if (j==0&&digit==s2.length-1)continue;
                int k = (i+j+kuriagari)%10;
                if (used[j]||used[k]) {
                    continue;
                }
                int c1 = s1[digit];
                int c2 = s2[digit];
                int c3 = s3[digit];
                if (c1==c2&&i!=j) {
                    continue;
                }
                if (c1!=c2&&i==j) {
                    continue;
                }

                if (c1==c3&&i!=k) {
                    continue;
                }
                if (c1!=c3&&i==k) {
                    continue;
                }

                if (c3==c2&&k!=j) {
                    continue;
                }
                if (c3!=c2&&k==j) {
                    continue;
                }

                System.out.println(c1+" "+c2+" "+c3);
                System.out.println(i+" "+j+" "+k);
                map[c1]=i;
                map[c2]=j;
                map[c3]=k;
                used[i]=used[j]=used[k]=true;
                if (rec(digit+1, i+j>=10 ? 1 : 0, used)) {
                    return true;
                }
                used[i]=used[j]=used[k]=false;
            }
        }
        return false;
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
