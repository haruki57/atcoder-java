import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC312F {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;
    static int M;
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        M = scanner.nextInt();
        List<Integer> no = new ArrayList<>();
        List<Integer> yes = new ArrayList<>();
        List<Integer> open = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int type = scanner.nextInt();;
            if(type==0) {
                no.add(scanner.nextInt());
            } else if(type==1) {
                yes.add(scanner.nextInt());
            } else {
                open.add(scanner.nextInt());
            }
        }
        Collections.sort(no);
        Collections.sort(yes);
        Collections.sort(open);


        long low = 0;
        long high = open.size();

        int cnt = 100;
        while(cnt-- >= 0) {
            long c1 = (low+low+high)/3;
            long c2 = (low+high+high)/3;
            var calc1 = calc(no, yes, open, c1);
            var calc2 = calc(no, yes, open, c2);
            //System.out.println(low+" "+high);
            //System.out.println(c1+" "+c2);
            //System.out.println(calc1+" "+calc2);
            //System.out.println();
            if (calc1 < calc2) {
                low = c1;
            } else {
                high = c2;
            }
        }
        long ans = 0;
        for (int i = (int)low-10; i < (int)high + 10; i++) {
            if (i < 0) {
                continue;
            }
            ans = Math.max(ans, calc(no, yes, open, i));
        }
        System.out.println(ans);
        //System.out.println(low+" "+high);
        //long mid = (high+low)/2;
    }

    private static long calc(List<Integer> no, List<Integer> yes, List<Integer> open, long c1) {
        long canOpen = 0;
        int m = M;
        for (int i = 0; i < c1; i++) {
            int ii = open.size()-i-1;
            if (ii < 0) {
                break;
            }
            canOpen += open.get(ii);
            m--;
        }
        if (m<=0) {
            return 0;
        }
        List<Integer> merged = new ArrayList<>();
        merged.addAll(no);
        for (int i = 0; i < canOpen; i++) {
            int ii = yes.size()-i-1;
            if (ii<0){
                break;
            }
            merged.add(yes.get(ii));
        }
        Collections.sort(merged);
        long ret = 0;
        for (int i = 0; i < m; i++) {
            int ii = merged.size()-i-1;
            if (ii < 0) {
                break;
            }
            ret += merged.get(ii);
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
