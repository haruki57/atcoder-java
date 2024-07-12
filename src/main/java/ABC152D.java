import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC152D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long ans = 0;
        long[][] memo = new long[10][10];
        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], INF);
        }
        for (int i = 1; i <= N; i++) {
            int top = String.valueOf(i).charAt(0)-'0';
            int bottom = String.valueOf(i).charAt(String.valueOf(i).length()-1)-'0';
            if (bottom==0) {
                continue;
            }
            //System.out.println(i+" "+top+" "+bottom+" "+ans);
            if (memo[top][bottom]!=INF) {
                ans+=memo[top][bottom];
                continue;
            }
            long subAns = 0;
            if (top == bottom && top <= N) {
                subAns++;
            }
            if (bottom*10+top<=N) {
                subAns++;
            }
            for (int digit = 3;; digit++) {
                int max = bottom * (int)Math.pow(10, digit-1) +
                        ((int)Math.pow(10, digit-2)-1)*10 +
                        top;
                //System.out.println("max:"+max);
                if (max > N) {

                    for (int j = 0; ; j++) {
                        int max2 = bottom * (int)Math.pow(10, digit-1) +
                                j*10 +
                                top;
                        //System.out.println("max2:"+max2);
                        if (max2>N) {
                            break;
                        }
                        subAns++;
                    }
                    break;
                } else {
                    subAns+=((int)Math.pow(10, digit-2));
                }
                //System.out.println("subAns:"+subAns);
            }
//            System.out.println("subAns:"+subAns);
//            System.out.println();
            ans += subAns;
            memo[top][bottom]=subAns;
        }
        System.out.println(ans);
        //System.out.println(tle(N));
    }

    static long tle(long N) {
        long ret = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                String s = String.valueOf(i);
                String t = String.valueOf(j);
                if (s.charAt(0)==t.charAt(t.length()-1) && s.charAt(s.length()-1) == t.charAt(0)) {
                    ret++;
                }
            }
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
