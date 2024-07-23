import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC161D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int K = scanner.nextInt();

        /*

        Use Priority Queue

        PriorityQueue<Long> q = new PriorityQueue<>();
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 1; i < 10; i++) {
            set.add((long)i);
            q.add((long)i);
        }

        while(!q.isEmpty()) {
            long cur = q.poll();
            if (cur >= 1000000000L) {
                continue;
            }
            for (int i = -1; i <= 1; i++) {
                int next = (int)((cur%10)+i);
                if (next>=0&&next<=9) {
                    q.add(cur*10+next);
                    set.add(cur*10+next);
                }
            }
        }

        for (Long l : set) {
            K--;
            if(K==0){
                System.out.println(l);
                return;
            }
        }
         */

        /*

        Use recursive func

        List<Long> nums=new ArrayList<Long>();
        for (int i = 1; i < 10; i++) {
            rec(1, i, nums);
        }
        Collections.sort(nums);
        System.out.println(nums.get(K-1));
         */


        /*
        give up

        long[][] cnt=new long[100][10];
        for (int i = 0; i < 10; i++) {
            cnt[1][i]=1;
        }
        for (int i = 0; i < 10; i++) {
            if(i==9 || i==0) {
                cnt[2][i]=2;
            } else {
                cnt[2][i]=3;
            }
        }
        for (int i = 2; i < cnt.length; i++) {
            for (int j = 0; j < cnt[i].length; j++) {
                if(j==0) {
                    cnt[i][j]=cnt[i-1][0]+cnt[i-1][1];
                } else if(j==9) {
                    cnt[i][j]=cnt[i-1][8]+cnt[i-1][9];
                } else {
                    cnt[i][j]=cnt[i-1][j-1]+cnt[i-1][j]+cnt[i-1][j+1];
                }
            }
        }

         */

    }

    private static void rec(int digit, long cur, List<Long> nums) {
        nums.add(cur);
        if (digit==10) {
            return;
        }
        for (int j = -1; j <= 1; ++j) {
            int add = (int)((cur % 10) + j);
            if (add >= 0 && add<= 9) rec(digit+1, cur*10+add, nums);
        }

    }

    static boolean isLunlun(long n) {
        long cur = n%10;
        n=n/10;
        while(n > 0) {
            if (Math.abs(cur-n%10)>=2) {
                return false;
            }
            cur = n%10;
            n/=10;
        }
        return true;
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
