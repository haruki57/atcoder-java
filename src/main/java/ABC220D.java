import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ABC220D {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        int M = 200;
        Arrays.setAll(a, i -> scanner.nextInt());
        List<Integer>[] list = new List[M];
        for (int i = 0; i < list.length; i++) {
            list[i] = new ArrayList<>();
        }
        for (int bit = 1; bit < 1<<Math.min(10, N); bit++) {
            int len = Integer.toBinaryString(bit).length();
            long sum = 0;
            for (int i = 0; i < len; i++) {
                if ((bit&(1<<i)) == 0) {
                    continue;
                }
                sum += a[i];
            }
            list[(int)(sum%M)].add(bit);
        }
        for (int i = 0; i < list.length; i++) {
            if (list[i].size()>=2) {
                int bit1 = list[i].get(0);
                int bit2 = list[i].get(1);
                List<Integer> list1 = new ArrayList<>();

                int len = Integer.toBinaryString(bit1).length();
                for (int j = 0; j < len; j++) {
                    if ((bit1&(1<<j)) == 0) {
                        continue;
                    }
                    list1.add(j+1);
                }
                System.out.println("Yes");
                System.out.print(list1.size()+" ");
                for (Integer integer : list1) {
                    System.out.print(integer+" ");
                }
                System.out.println();

                List<Integer> list2 = new ArrayList<>();
                len = Integer.toBinaryString(bit2).length();
                for (int j = 0; j < len; j++) {
                    if ((bit2&(1<<j)) == 0) {
                        continue;
                    }
                    list2.add(j+1);
                }
                System.out.print(list2.size()+" ");
                for (Integer integer : list2) {
                    System.out.print(integer+" ");
                }
                System.out.println();
                return;
            }
        }
        System.out.println("No");
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
