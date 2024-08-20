import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC221D_WA {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long[][] ab = new long[N][2];
        for (int i = 0; i < N; i++) {
            ab[i][0]=scanner.nextInt();
            ab[i][1]=ab[i][0]+scanner.nextInt()-1;
        }
        ansTle(ab);

        var map = asshuku(ab);
        //System.out.println(map);
        for (int i = 0; i < ab.length; i++) {
            //System.out.println(Arrays.toString(ab[i]));
        }
        int[] imos = new int[N*10];
        for (int i = 0; i < ab.length; i++) {
            imos[(int)ab[i][0]]++;
            imos[(int)ab[i][1]+1]--;
        }
        int[] sum = new int[imos.length];
        int temp = 0;
        for (int i = 0; i < imos.length; i++) {
            temp += imos[i];
            sum[i]=temp;
        }
        var mapRe = new HashMap<Long, Long>();
        for (Map.Entry<Long, Long> longLongEntry : map.entrySet()) {
            mapRe.put(longLongEntry.getValue(), longLongEntry.getKey());
        }

        //System.out.println(Arrays.toString(imos));
        System.out.println(mapRe);
        System.out.println(Arrays.toString(sum));
        long[] answers = new long[N+1];
        for (int i = 1; i < sum.length; i++) {
            if(sum[i]==0) {
                continue;
            }
            int ii = i+1;
            while(sum[ii]==sum[i]) {
                ii++;
            }
            ii--;
            System.out.println(sum[i]+" "+i+" "+ii);
            if(i==ii){
                i--;
            }
            Long r = mapRe.get((long)ii);
            Long l = mapRe.get((long)(i));
            if(r!=null&&l!=null) {
                answers[sum[ii]]+=(r-l)+1;
            } else {
                answers[sum[ii]]+=r;
            }
            i=ii;

            /*
            if(sum[i]==sum[i-1]) {
                Long r = mapRe.get((long)i);
                Long l = mapRe.get((long)(i-1));
                answers[sum[i]]+=(r-l);
                System.out.println(r+" "+l);
            } else {
                answers[sum[i]]++;
            }
            System.out.println(Arrays.toString(answers));

             */
        }
        for (int i = 1; i <= N; i++) {
            out.print(answers[i]+" ");
        }
        out.println();
    }

    static void ansTle(long[][] ab) {
        int[] num = new int[100];
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < ab.length; j++) {
                if(ab[j][0] <= i && i <= ab[j][1]) {
                    num[i]++;
                }
            }
        }
        long[] answers = new long[ab.length+1];
        for (int i : num) {
            answers[i]++;
        }
        System.out.println("answersTle: ");
        for (int i = 1; i < answers.length; i++) {
            System.out.print(answers[i]+" ");
        }
        System.out.println();
        System.out.println("------");
    }

    static Map<Long, Long> asshuku(long[][] ab) {
        TreeSet<Long> set = new TreeSet<>();
        for (long[] longs : ab) {
            set.add(longs[0]);
            set.add(longs[1]);
        }
        Map<Long, Long> map = new TreeMap<>();
        long cnt = 1;
        for (Long l : set) {
            map.put(l, cnt++);
        }
        for (long[] longs : ab) {
            longs[0]=map.get(longs[0]);
            longs[1]=map.get(longs[1]);
        }
        return map;
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
