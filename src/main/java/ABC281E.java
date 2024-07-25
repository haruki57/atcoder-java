import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ABC281E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static long sum = 0;
    static int leftSize = 0, rightSize = 0;
    static int N,M,K;
    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        M = scanner.nextInt();
        K = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt());

        MultiSet left = new MultiSet();
        MultiSet right = new MultiSet();
        for (int i = 0; i < M; i++) {
            push(a[i], left, right);
        }
        //out.println(left.map);
        //out.println(right.map);
        out.println(left.sum);

        for (int i = M; i < N; i++) {
            push(a[i], left, right);
            pop(a[i-M], left, right);
            //out.println(left.map);
            //out.println(right.map);
            out.println(left.sum);
        }

        /*
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();
        for (int i = 0; i < M; i++) {
            push(a[i],left, right);
            //out.println(left);
            //out.println(right);
        }
        out.println(sum);
        for (int i = M; i < a.length; i++) {
            push(a[i], left, right);
            //out.println("push:" + a[i]);
            //out.println(left);
            //out.println(right);
            pop(a[i-M], left, right);
            //out.println("pop:" + a[i-M]);

            //out.println(left);
            //out.println(right);
            out.println(sum);
        }

         */
    }

    private static void pop(long x, MultiSet left, MultiSet right) {
        if (left.map.containsKey(x)) {
            left.erase(x);
        } else {
            right.erase(x);
        }
        if (left.size < K) {
            var firstKey = right.map.firstKey();
            right.erase(firstKey);
            left.insert(firstKey);
        }
    }

    private static void push(long x, MultiSet left, MultiSet right) {
        left.insert(x);
        if (left.size > K) {
            var lastKey = left.map.lastKey();
            left.erase(lastKey);
            right.insert(lastKey);
        }
    }

    private static void pop(int x, TreeMap<Integer, Integer> left, TreeMap<Integer, Integer> right) {
        if (left.getOrDefault(x, 0) > 0) {
            leftSize--;
            var nextPut = left.get(x)-1;
            if (nextPut == 0) {
                left.remove(x);
            } else {
                left.put(x, nextPut);
            }
            sum-=x;
        } else {
            rightSize--;
            var nextPut = right.get(x)-1;
            if (nextPut == 0) {
                right.remove(x);
            } else {
                right.put(x, right.get(x) - 1);
            }

        }

        if (leftSize < K) {
            var firstKey = right.firstKey();
            var nextPut = right.get(firstKey)-1;
            if (nextPut==0) {
                right.remove(firstKey);
            } else {
                right.put(firstKey, nextPut);
            }
            rightSize--;
            left.put(firstKey, left.getOrDefault(firstKey, 0) + 1);
            leftSize++;
            sum+=firstKey;
        }
    }


    static class MultiSet {
        // You can access map directly but don't mutate it.
        public TreeMap<Long, Integer> map;
        int size = 0;
        long sum = 0;
        public MultiSet() {
            map = new TreeMap<>();
        }
        public void insert(long x) {
            map.put(x, map.getOrDefault(x, 0)+1);
            size++;
            sum += x;
        }
        public void erase(long x) {
            var nextPut = map.getOrDefault(x, 0)-1;
            if (nextPut == -1) {
                return;
            }
            if (nextPut == 0) {
                map.remove(x);
            } else {
                map.put(x, nextPut);
            }
            size--;
            sum -= x;
        }
    }

    private static void push(int x, TreeMap<Integer, Integer> left, TreeMap<Integer, Integer> right) {
        left.put(x, left.getOrDefault(x, 0)+1);
        leftSize++;
        sum+=x;
        if (leftSize > K) {
            leftSize--;
            var lastKey = left.lastKey();
            sum-=lastKey;
            int nextPut = left.get(lastKey)-1;
            if (nextPut==0) {
                left.remove(lastKey);
            } else {
                left.put(lastKey, left.get(lastKey)-1);
            }
            right.put(lastKey, right.getOrDefault(lastKey, 0)+1);
            rightSize++;
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
