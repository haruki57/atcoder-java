import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ABC344E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static class Node {
        Node prev, next;
        int num;
    }

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());

        Map<Integer, Node> map = new HashMap<>();
        Node first = new Node();
        first.num = 0;
        Node last = new Node();
        last.num = INF;
        Node cur = first;
        map.put(0, first);
        map.put(INF, last);
        for (int i = 0; i < a.length; i++) {
            Node n = new Node();
            n.num = a[i];
            cur.next = n;
            n.prev = cur;
            cur = n;
            map.put(n.num, n);
        }
        cur.next = last;
        last.prev = cur;
        cur = first;
        while(cur!=null)  {
            //System.out.println(cur.num);
            cur = cur.next;
        }

        int Q = scanner.nextInt();
        for (int i = 0; i < Q; i++) {
            int t = scanner.nextInt();
            if (t==1) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Node newNode = new Node();
                newNode.num = y;
                map.put(y, newNode);
                cur = map.get(x);
                Node nextnext = cur.next;
                cur.next = newNode;
                newNode.prev = cur;
                newNode.next = nextnext;
                nextnext.prev = newNode;
            } else {
                int x = scanner.nextInt();
                Node node = map.get(x);
                Node prev = node.prev;
                Node next = node.next;
                /*
                out.println(prev.num);
                out.println(node.num);
                out.println(next.num);
                out.println();

                 */
                prev.next = next;
                next.prev = prev;
            }
            /*
            cur = first.next;
            while(cur.num!=INF)  {
                out.print(cur.num+" ");
                cur = cur.next;
            }
            out.println();
            out.println();
            cur = last.prev;
            while(cur.num!=0)  {
                out.print(cur.num+" ");
                cur = cur.prev;
            }
            out.println();
            out.println();

             */
        }
        cur = first.next;
        while(cur.num!=INF)  {
            out.print(cur.num+" ");
            cur = cur.next;
        }


        out.println();
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
