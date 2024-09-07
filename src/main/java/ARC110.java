import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ARC110 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < a.length; i++) {
            a[i]= scanner.nextInt()-1;
        }
        int[] pos = new int[N];
        for (int i = 0; i < N; i++) {
            pos[a[i]]=i;
        }

        boolean[] done = new boolean[N-1];
        List<Integer> answers = new ArrayList<>();
        int cur = 0;
        while(cur < N) {
            int curPos = pos[cur];
            if(curPos==cur) {
                cur++;
                continue;
            }
            //System.out.println(curPos);
            for (int i = curPos-1; i >= cur; i--) {
                if(done[i]) {
                    System.out.println(-1);
                    return;
                }
                swap(a, pos, i, i+1);
                done[i]=true;
                answers.add(i+1);
            }
            cur++;
        }
        if(answers.size()==N-1) {
            for (Integer answer : answers) {
                out.println(answer+" ");
            }
        } else {
            System.out.println(-1);
        }

    }

    static void swap(int[] a,int[] pos, int x,int y) {
        int ax = a[x];
        int ay = a[y];
        int tmp = pos[ax];
        pos[ax]=pos[ay];
        pos[ay] = tmp;
        a[x]=ay;
        a[y]=ax;
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
