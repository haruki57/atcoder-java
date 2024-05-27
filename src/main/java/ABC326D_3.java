import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ABC326D_3 {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;

    static class Permutations {
        private int[] perm;
        private boolean[] used;
        private int N;
        ArrayList<int[]> perms = new ArrayList<>();

        public Permutations(int n) {
            this.N=n;
            used=new boolean[N];
            perm=new int[N];
            perm(0);
        }
        private void perm(int depth) {
            if (depth == N) {
                int[] idxArr = new int[N];
                for (int i = 0; i < N; i++) {
                    idxArr[i] = perm[i];
                }
                perms.add(idxArr);
                return;
            }
            for (int i = 0; i < N; i++) {
                if (used[i]) {
                    continue;
                }
                used[i] = true;
                perm[depth] = i;
                perm(depth + 1);
                used[i] = false;
            }
        }
    }

    static int N;
    static char[] s;
    static char[] t;
    static char[][] tile;

    static void run (final FastScanner scanner, final PrintWriter out) {
        N = scanner.nextInt();
        s = scanner.next().toCharArray();
        t = scanner.next().toCharArray();
        tile = new char[N][N];
        abc = new char[N];
        abc[0]='A';
        abc[1]='B';
        abc[2]='C';
        perm = new Permutations(N);
        for (int i = 0; i < N - 3; i++) {
            abc[i+3]='.';
        }

        for (int i = 0; i < N; i++) {
            Arrays.fill(tile[i], '.');
        }
        bt(0);
        System.out.println("No");
        //System.out.println(cnt);
    }
    static int cnt = 0;
    static Permutations perm = new Permutations(N);
    static char[] abc = new char[N];

    static void bt(int depth) {
        if (depth==N) {
            if (abc()){

                boolean ng = false;
                for (int i = 0; i < N; i++) {
                    char first2 = 0;
                    for (int j = 0; j < N; j++) {
                        if(tile[j][i]!='.') {
                            first2=tile[j][i];
                            break;
                        }
                    }
                    if (first2==0) {
                        ng=true;
                        break;
                    }
                    if (first2!=t[i]) {
                        ng=true;
                        break;
                    }
                }
                if (!ng) {
                    out();
                    System.exit(0);
                }
            }
            return;
        }
        for (int[] ints : perm.perms) {
            char[] newAbc = new char[N];
            for (int i = 0; i < ints.length; i++) {
                newAbc[i]=abc[ints[i]];
            }
            char first = 0;
            for (int i = 0; i < newAbc.length; i++) {
                if (newAbc[i]!='.') {
                    first=newAbc[i];
                    break;
                }
            }
            if (first!=s[depth]) {
                continue;
            }
            for (int i = 0; i < N; i++) {
                tile[depth][i]=newAbc[i];
            }

            boolean ok = true;
            for (int i = 0; i < N; i++) {
                char first2 = 0;
                for (int j = 0; j < N; j++) {
                    if(tile[j][i]!='.') {
                        first2=tile[j][i];
                        break;
                    }
                }
                if (first2==0) {
                    continue;
                }
                if (first2!=t[i]) {
                    ok=false;
                    break;
                }
            }
            if (ok) {
                out();
                bt(depth+1);
            }
        }
    }

    static boolean abc() {
        for (int i = 0; i < N; i++) {
            int[] row = new int[3];
            for (int j = 0; j < N; j++) {
                if(tile[j][i]!='.') {
                    row[tile[j][i]-'A']++;
                }
            }
            if (row[0]==1&&row[1]==1&&row[2]==1){

            } else {
                return false;
            }
        }
        return true;
    }


    static void out() {
        System.out.println("Yes");
        for (int i = 0; i < N; i++) {
            System.out.println(tile[i]);
        }
        System.out.println();
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
