import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC216E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        long K = scanner.nextInt();
        int[] a = new int[N+1];
        for (int i = 0; i < a.length-1; i++) {
            a[i]= scanner.nextInt();
        }
        a[a.length-1]=0;
        Arrays.sort(a);
        if (a.length==2) {
            long aa = a[1];
            if (aa <= K) {
                System.out.println(aa*(aa+1)/2);
            } else {
                System.out.println(sum(aa, K));
            }
            return;
        }

        for (int i = 0; i < a.length/2; i++) {
            int ii = a.length-i-1;
            int tmp = a[i];
            a[i]=a[ii];
            a[ii]=tmp;
        }

        long ans = 0;
        long max=a[0], next=a[0];
        int idx = 1;
        //System.out.println(Arrays.toString(a));
        while(true) {
            while(max == next && idx < a.length) {
                next = a[idx];
                idx++;
            }
            int count = idx-1;
            long diff = max-next;
            if (count * diff < K) {
                //System.out.println("1:"+max+" "+next+" "+idx+" "+diff+" "+K+" "+count);
                ans += sum(max, diff) * count;
                K -= (long)count*diff;
                max = next;
            } else {
                long height =(K/count);
                long rest =K%count;
                //System.out.println("2:"+K+" "+count+" "+height+" "+rest);
                ans += sum(max, height)*count;
                //System.out.println(ans);
                max = max - height;

                ans += max * rest;
                //System.out.println(ans);
                break;
            }
            //System.out.println(ans);
            //System.out.println();
            if (idx == a.length) {
                break;
            }
        }
        System.out.println(ans);
        /*
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i : a) {
            q.add(-i);
        }
        long ans = 0;
        while(K-->0) {
            int p = -q.poll();
            if (p<0) {
                break;
            }
            ans += p;
            p--;
            q.add(-p);
        }
        System.out.println(ans);

         */
    }

    static long sum(long x, long k) {
        return x*(x+1)/2 - (k-x)*(k-x-1)/2;
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
