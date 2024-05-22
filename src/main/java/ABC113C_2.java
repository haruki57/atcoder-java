import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.*;

public class ABC113C_2 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        List<int[]>[] list = new List[N];
        for (int i = 0; i < list.length; i++) {
            list[i]=new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int p = scanner.nextInt()-1;
            int y = scanner.nextInt();
            list[p].add(new int[]{y, i, -1, p+1});
        }

        for (int i = 0; i < N; i++) {
            Collections.sort(list[i], new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0]-o2[0];
                }
            });
            for (int j = 0; j < list[i].size(); j++) {
                list[i].get(j)[2]=j+1;
            }
        }
        List<int[]> intList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int[] ints : list[i]) {
                //System.out.println(Arrays.toString(ints));
                intList.add(ints);
            }
        }
        Collections.sort(intList, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1]-o2[1];
            }
        });
        for (int[] ints : intList) {
            out.println(pad(String.valueOf(ints[3]), '0', 6)+pad(String.valueOf(ints[2]), '0', 6));
        }
    }

    static String pad(String s, char c, int len) {
        if (s.length() >= len) {
            return s;
        }
        char[] ret = new char[len];
        for (int i = 0; i < len - s.length(); i++) {
            ret[i] = c;
        }
        for (int i = 0; i < s.length(); i++) {
            ret[i + len - s.length()] = s.charAt(i);
        }
        return new String(ret);
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
