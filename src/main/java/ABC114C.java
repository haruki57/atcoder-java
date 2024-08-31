import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC114C {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        if(N<357) {
            System.out.println(0);
            return;
        }
        int[] a = new int[100];
        Arrays.fill(a, 1);
        a[0]=7;
        a[1]=5;
        a[2]=3;
        int ans = 1;
        while(true) {
            next(a);

            //System.out.println(toLong(a));
            if(N < toLong(a)) {
                break;
            }
            ans++;
        }
        System.out.println(ans);
    }

    private static long toLong(int[] a) {
        long ret = 0;
        for (int i = 0; i < a.length; i++) {
            if(a[i]==1) {
                break;
            }
            ret *= 10;
            ret += a[i];
        }
        return Long.parseLong(new StringBuilder(String.valueOf(ret)).reverse().toString());
    }

    private static void next(int[] a) {
        while(true) {
            rec(a, 0);
            //System.out.println(Arrays.toString(a));
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < a.length; i++) {
                if(a[i]==1) {
                    break;
                }
                set.add(a[i]);
            }
            if(set.size() == 3) {
                break;
            }
        }
        //System.out.println();
    }

    private static void rec(int[] a, int depth) {
        if(a[depth]<=5) {
            a[depth]+=2;
            return;
        }
        a[depth]=3;
        rec(a, depth+1);
    }

    /*
    private static int[] next(int[] a) {
        int[] b = nextPerm(a);
        return b;
    }

     */

    static int[] nextPerm(int[] a) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : a) {
            list.add(i);
        }
        int pivotPos = -1;
        int pivot = 0;
        for (int i=list.size()-2; i>=0; i--) {
            if (list.get(i) < list.get(i+1)) {
                pivotPos = i;
                pivot = list.get(i);
                break;
            }
        }
        if (pivotPos==-1 && pivot==0) return null;
        int L = pivotPos+1, R = list.size()-1;
        int minPos = -1;
        int min = Integer.MAX_VALUE;
        for (int i=R; i>=L; i--) {
            if (pivot < list.get(i)) {
                if (list.get(i) < min) {
                    min = list.get(i);
                    minPos = i;
                }
            }
        }
        Collections.swap(list, pivotPos, minPos);
        Collections.sort(list.subList(L, R+1));
        int[] ret = new int[a.length];
        for (int i=0; i<list.size(); i++) {
            ret[i]=list.get(i);
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
