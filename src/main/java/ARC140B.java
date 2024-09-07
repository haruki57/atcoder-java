import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ARC140B {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        int[] cnts = new int[200009];
        for (int i = 0; i < s.length-2; i++) {
            if(s[i]=='A'&&s[i+1]=='R'&&s[i+2]=='C') {
                int l = i-1;
                int r = i+3;
                int cnt = 0;
                while(l >= 0 && r < s.length && s[l]=='A'&&s[r]=='C') {
                    l--;
                    r++;
                    cnt++;
                }
                cnts[cnt]++;
            }
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 0; i < cnts.length; i++) {
            for (int j = 0; j < cnts[i]; j++) {
                q.add(i);
            }
        }
        int even = 1, odd = 0;
        int ans = 0;
        for (int i = 0; i < 10; i++) {
            //System.out.print(cnts[i]+" ");
        }
        //System.out.println();
        while(!q.isEmpty()) {
            int cnt = q.poll();
            ans++;
            if(cnt==0) {
                even++;
            } else {
                if(even > 0) {
                    even--;
                    q.add(cnt-1);
                } else {
                    even++;
                    //q.add(cnt-1);
                }
            }
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
