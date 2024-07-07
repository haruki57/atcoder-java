import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC361D {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        String S = scanner.next();
        String T = scanner.next();
        //int[] a = new int[N/2+1];
        //Arrays.fill(a, -1);
        StringBuilder sb1 = new StringBuilder(S+"__");
        StringBuilder sb2 = new StringBuilder(T+"__");
        //System.out.println(sb1);
        //System.out.println(sb2);
        Map<String, Integer> dist = new HashMap<>();

        /*
        System.out.println(sb2);
        int idx = sb2.indexOf("_");
        for (int i = 0; i < sb2.length(); i++) {
            StringBuilder next = new StringBuilder(sb2.toString());
            if (next.charAt(i)=='_') {
                continue;
            }
            char c = next.charAt(i);
            next.replace(idx, idx+1, new String(new char[]{c}));
            next.replace(i, i+1, "_");
            System.out.println(next);
        }

         */
        Queue<C> queue = new LinkedList<>();
        queue.add(new C(sb1, 0));
        int ans = INF;
        while(!queue.isEmpty()) {
            C cur = queue.poll();
            StringBuilder sb = cur.sb;
            int depth = cur.depth;
            //System.out.println(depth);
            if (eq(sb, sb2)) {
                ans = Math.min(ans, depth);
                continue;
            }
            //System.out.println(sb+" "+sb2+" "+depth);
            int idx = sb.indexOf("__");
            for (int i = 0; i < sb.length()-1; i++) {
                StringBuilder next = new StringBuilder(sb.toString());
                char c1 = next.charAt(i);
                char c2 = next.charAt(i+1);
                if (c1=='_'||c2=='_'){
                    continue;
                }
                next.replace(idx, idx+2, new String(new char[]{c1, c2}));
                next.replace(i, i+2, "__");
                String nextS = next.toString();
                if (dist.containsKey(nextS)) {
                    continue;
                }
                dist.put(nextS, depth);
                queue.add(new C(next, depth+1));
                //ret = Math.min(ret, bt(next, sb2, dist, depth+1));
            }
        }
        if (ans==INF) {
            ans=-1;
        }
        System.out.println(ans);

    }
    static class C {
        StringBuilder sb;
        int depth;
        public C(StringBuilder a, int b) {
            sb = a;
            depth = b;
        }
    }

    private static int bt(StringBuilder sb1, StringBuilder sb2,
                          Map<String, Integer> dist, int depth) {
        if (eq(sb1, sb2)) {
            return depth;
        }
        System.out.println(sb1+" "+sb2+" "+depth);
        int ret = INF;
        int idx = sb1.indexOf("__");
        for (int i = 0; i < sb1.length()-1; i++) {
            StringBuilder next = new StringBuilder(sb1.toString());
            char c1 = next.charAt(i);
            char c2 = next.charAt(i+1);
            if (c1=='_'||c2=='_'){
                continue;
            }
            next.replace(idx, idx+2, new String(new char[]{c1, c2}));
            next.replace(i, i+2, "__");
            String nextS = next.toString();
            System.out.println(nextS);
            if (dist.containsKey(nextS) && dist.get(nextS) < depth) {
                continue;
            }
            dist.put(nextS, depth);
            ret = Math.min(ret, bt(next, sb2, dist, depth+1));
        }
        return ret;
    }

    private static boolean eq (StringBuilder sb1, StringBuilder sb2 ) {
        return sb1.toString().equals(sb2.toString());
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
