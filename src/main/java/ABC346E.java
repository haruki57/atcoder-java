import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC346E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int H = scanner.nextInt();
        int W = scanner.nextInt();
        int M = scanner.nextInt();
        int[][] q = new int[M][3];
        for (int i = 0; i < M; i++) {
            int t = scanner.nextInt();
            int a = scanner.nextInt();
            int x = scanner.nextInt();
            q[i][0]=t;
            q[i][1]=a;
            q[i][2]=x;
        }
        TreeSet<Integer> hs = new TreeSet<>();
        TreeSet<Integer> ws = new TreeSet<>();
        TreeMap<Integer, Long> cnts = new TreeMap<>();
        int Q = M;
        while(Q>=1){
            Q--;
            int t = q[Q][0];
            int a = q[Q][1];
            int x = q[Q][2];
            if(t==1) {
                if(hs.contains(a)) {
                    continue;
                }
                cnts.put(x, cnts.getOrDefault(x, 0L) + W-ws.size());
                hs.add(a);
            } else {
                if(ws.contains(a)) {
                    continue;
                }
                cnts.put(x, cnts.getOrDefault(x, 0L) + H-hs.size());
                ws.add(a);
            }
        }
        long sum = 0;
        int cnt = 0;
        for (Map.Entry<Integer, Long> integerIntegerEntry : cnts.entrySet()) {
            if(integerIntegerEntry.getKey() == 0) {
                continue;
            }
            if(integerIntegerEntry.getValue() > 0) {
                cnt++;
            }
            sum += integerIntegerEntry.getValue();
        }
        long cnt0 = (long)H*W - sum;
        if(cnt0>0) {
            cnts.put(0, (long)H*W - sum);
            cnt++;
        }

        out.println(cnt);
        for (Map.Entry<Integer, Long> integerLongEntry : cnts.entrySet()) {
            if(integerLongEntry.getValue() > 0) {
                out.println(integerLongEntry.getKey()+" "+integerLongEntry.getValue());
            }
        }
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
