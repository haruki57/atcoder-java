import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC375D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        while(true) {
            char[] s = scanner.next().toCharArray();
            /*
            char[] s = new char[10];
            for (int i = 0; i < s.length; i++) {
                s[i] = (char)((char)(Math.random()*3) + (char)'A');
            }

             */
            /*
            s = new char[200000];
            for (int i = 0; i < s.length; i++) {
                s[i]='A';
            }

             */
            List<Integer>[] cnts = new List[26];
            for (int i = 0; i < cnts.length; i++) {
                cnts[i]=new ArrayList<>();
            }
            for (int i = 0; i < s.length; i++) {
                cnts[s[i]-'A'].add(i);
            }
            long ans = 0;
            for (List<Integer> cnt : cnts) {
                //System.out.println(cnt);
                if(cnt.size()<=1) {
                    continue;
                }
                if(cnt.size()==2) {
                    int x = cnt.get(0);
                    int y = cnt.get(1);
                    ans += y-x-1;
                    continue;
                }
                long[] sum = new long[cnt.size()+1];
                for (int i = 0; i < cnt.size(); i++) {
                    sum[i+1]=sum[i]+cnt.get(i);
                }
                for (int i = 0; i < cnt.size(); i++) {
                    long cur = cnt.get(i);
                    if(i>0) {
                        long left = cnt.get(i-1);
                        if(cur-left == 1) {
                            ans += cur * (i-1) - sum[i-1] - (i-1);
                            //System.out.println("hoge"+" "+(i-1)+" "+cur+" "+sum[i-1]);
                        } else {
                            ans += cur * i - sum[i] - i;
                        }

                    }
                    /*
                    if(i+1 < cnt.size()) {
                        long right = cnt.get(i+1);
                        int rightNum = cnt.size() - i-1;
                        if(right-cur == 1) {
                            //ans += (sum[sum.length-1]-sum[i+2]) - cur * (rightNum-1) - (rightNum-1);
                        } else {
                            //ans += (sum[sum.length-1]-sum[i+1]) - cur * (rightNum) - (rightNum);
                        }
                    }

                     */
                }
            }

            System.out.println(ans);
            break;
        }
    }

    static long ansTLE(char[] s) {
        long ans = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = i+1; j < s.length; j++) {
                for (int k = j+1; k < s.length; k++) {
                    if(s[i]==s[k]) {
                        ans++;
                    }
                }
            }
        }
        return ans;
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
