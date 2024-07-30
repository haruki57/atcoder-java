// https://atcoder.jp/contests/practice2/submissions/50278120

public class ALPC_D_AC {
    public static void main(String[] args) {
        ContestScanner scan = new ContestScanner();
        final int n = scan.nextInt();
        final int m = scan.nextInt();
        char[][] s = scan.nextCharMatrix(n, m);

        final int[] di = {0, 0, -1, 1};
        final int[] dj = {1, -1, 0, 0};
        MaxFlow mf = new MaxFlow(n*m + 2);
        for(int i = 0;i < n; ++i){
            for(int j = (i&1)==0?0:1;j < m; j += 2){
                if(s[i][j] == '#')continue;
                for(int d = 0;d < 4; ++d){
                    int ni = i + di[d];
                    int nj = j + dj[d];
                    if(!inGrid(ni, nj, n, m))continue;
                    if(s[ni][nj] == '#')continue;
                    mf.addEdge(m*i+j, m*ni+nj, 1);
                }
            }
        }
        int f = n*m;
        int g = n*m+1;
        for(int i = 0;i < n; ++i){
            for(int j = 0;j < m; ++j){
                if(s[i][j] == '#')continue;
                if((i+j & 1) == 0){
                    mf.addEdge(f, i*m+j, 1);
                }else{
                    mf.addEdge(i*m+j, g, 1);
                }
            }
        }

        long max_flow = mf.maxFlow(f, g);
        for(MaxFlow.CapEdge e: mf.getEdges()) {
            if(e.from == f || e.to == g || e.flow == 0)continue;
            int fi = e.from/m;
            int fj = e.from - fi*m;
            int gi = e.to/m;
            int gj = e.to - gi*m;

            if(fi == gi){
                if(fj < gj){
                    s[fi][fj] = '>';
                    s[gi][gj] = '<';
                }else{
                    s[fi][fj] = '<';
                    s[gi][gj] = '>';
                }
            }else {
                if(fi < gi){
                    s[fi][fj] = 'v';
                    s[gi][gj] = '^';
                }else{
                    s[fi][fj] = '^';
                    s[gi][gj] = 'v';
                }
            }
        }
        ContestPrinter cp = new ContestPrinter();
        cp.println(max_flow);
        for(int i = 0;i < n; ++i){
            for(int j = 0;j < m; ++j){
                cp.print(s[i][j]);
            }
            cp.println();
        }


        cp.close();
    }

    private static boolean inGrid(int i, int j, int h, int w){
        return (0 <= i && i < h) && (0 <= j && j < w);
    }

}

class MaxFlow {
    private static final class InternalCapEdge {
        final int to;
        final int rev;
        long cap;
        InternalCapEdge(int to, int rev, long cap) { this.to = to; this.rev = rev; this.cap = cap; }
    }
    public static final class CapEdge {
        public final int from, to;
        public final long cap, flow;
        public CapEdge(int from, int to, long cap, long flow) { this.from = from; this.to = to; this.cap = cap; this.flow = flow; }
        @Override
        public boolean equals(Object o) {
            if (o instanceof CapEdge) {
                CapEdge e = (CapEdge) o;
                return from == e.from && to == e.to && cap == e.cap && flow == e.flow;
            }
            return false;
        }
    }
    private static final class IntPair {
        final int first, second;
        IntPair(int first, int second) { this.first = first; this.second = second; }
    }

    public static final long INF = Long.MAX_VALUE;

    private final int n;
    private final java.util.ArrayList<IntPair> pos;
    private final java.util.ArrayList<InternalCapEdge>[] g;

    @SuppressWarnings("unchecked")
    public MaxFlow(int n) {
        this.n = n;
        this.pos = new java.util.ArrayList<>();
        this.g = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            this.g[i] = new java.util.ArrayList<>();
        }
    }

    public int addEdge(int from, int to, long cap) {
        rangeCheck(from, 0, n);
        rangeCheck(to, 0, n);
        nonNegativeCheck(cap, "Capacity");
        int m = pos.size();
        pos.add(new IntPair(from, g[from].size()));
        int fromId = g[from].size();
        int toId = g[to].size();
        if (from == to) toId++;
        g[from].add(new InternalCapEdge(to, toId, cap));
        g[to].add(new InternalCapEdge(from, fromId, 0L));
        return m;
    }

    private InternalCapEdge getInternalEdge(int i) {
        return g[pos.get(i).first].get(pos.get(i).second);
    }

    private InternalCapEdge getInternalEdgeReversed(InternalCapEdge e) {
        return g[e.to].get(e.rev);
    }

    public CapEdge getEdge(int i) {
        int m = pos.size();
        rangeCheck(i, 0, m);
        InternalCapEdge e = getInternalEdge(i);
        InternalCapEdge re = getInternalEdgeReversed(e);
        return new CapEdge(re.to, e.to, e.cap + re.cap, re.cap);
    }

    public CapEdge[] getEdges() {
        CapEdge[] res = new CapEdge[pos.size()];
        java.util.Arrays.setAll(res, this::getEdge);
        return res;
    }

    public void changeEdge(int i, long newCap, long newFlow) {
        int m = pos.size();
        rangeCheck(i, 0, m);
        nonNegativeCheck(newCap, "Capacity");
        if (newFlow > newCap) {
            throw new IllegalArgumentException(
                    String.format("Flow %d is greater than the capacity %d.", newCap, newFlow)
            );
        }
        InternalCapEdge e = getInternalEdge(i);
        InternalCapEdge re = getInternalEdgeReversed(e);
        e.cap = newCap - newFlow;
        re.cap = newFlow;
    }

    public long maxFlow(int s, int t) {
        return flow(s, t, INF);
    }

    public long flow(int s, int t, long flowLimit) {
        rangeCheck(s, 0, n);
        rangeCheck(t, 0, n);
        long flow = 0L;
        int[] level = new int[n];
        int[] que = new int[n];
        int[] iter = new int[n];
        while (flow < flowLimit) {
            bfs(s, t, level, que);
            if (level[t] < 0) break;
            java.util.Arrays.fill(iter, 0);
            while (flow < flowLimit) {
                long d = dfs(t, s, flowLimit - flow, iter, level);
                if (d == 0) break;
                flow += d;
            }
        }
        return flow;
    }

    private void bfs(int s, int t, int[] level, int[] que) {
        java.util.Arrays.fill(level, -1);
        int hd = 0, tl = 0;
        que[tl++] = s;
        level[s] = 0;
        while (hd < tl) {
            int u = que[hd++];
            for (InternalCapEdge e : g[u]) {
                int v = e.to;
                if (e.cap == 0 || level[v] >= 0) continue;
                level[v] = level[u] + 1;
                if (v == t) return;
                que[tl++] = v;
            }
        }
    }

    private long dfs(int cur, int s, long flowLimit, int[] iter, int[] level) {
        if (cur == s) return flowLimit;
        long res = 0;
        int curLevel = level[cur];
        for (int itMax = g[cur].size(); iter[cur] < itMax; iter[cur]++) {
            int i = iter[cur];
            InternalCapEdge e = g[cur].get(i);
            InternalCapEdge re = getInternalEdgeReversed(e);
            if (curLevel <= level[e.to] || re.cap == 0) continue;
            long d = dfs(e.to, s, Math.min(flowLimit - res, re.cap), iter, level);
            if (d <= 0) continue;
            e.cap += d;
            re.cap -= d;
            res += d;
            if (res == flowLimit) break;
        }
        return res;
    }

    public boolean[] minCut(int s) {
        rangeCheck(s, 0, n);
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int ptr = 0;
        stack[ptr++] = s;
        visited[s] = true;
        while (ptr > 0) {
            int u = stack[--ptr];
            for (InternalCapEdge e : g[u]) {
                int v = e.to;
                if (e.cap > 0 && !visited[v]) {
                    visited[v] = true;
                    stack[ptr++] = v;
                }
            }
        }
        return visited;
    }

    private void rangeCheck(int i, int minInclusive, int maxExclusive) {
        if (i < 0 || i >= maxExclusive) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d out of bounds for length %d", i, maxExclusive)
            );
        }
    }

    private void nonNegativeCheck(long cap, String attribute) {
        if (cap < 0) {
            throw new IllegalArgumentException(
                    String.format("%s %d is negative.", attribute, cap)
            );
        }
    }
}

final class ContestPrinter implements AutoCloseable {
    private final java.io.OutputStream out;
    private final int buflen = 1<<18;
    private int pos = 0;
    private final byte[] buf = new byte[buflen];
    private static final byte[] Digits = {  '0','0', '0','1', '0','2', '0','3', '0','4', '0','5', '0','6', '0','7', '0','8', '0','9', '1','0', '1','1', '1','2', '1','3', '1','4', '1','5', '1','6', '1','7', '1','8', '1','9',
            '2','0', '2','1', '2','2', '2','3', '2','4', '2','5', '2','6', '2','7', '2','8', '2','9', '3','0', '3','1', '3','2', '3','3', '3','4', '3','5', '3','6', '3','7', '3','8', '3','9',
            '4','0', '4','1', '4','2', '4','3', '4','4', '4','5', '4','6', '4','7', '4','8', '4','9', '5','0', '5','1', '5','2', '5','3', '5','4', '5','5', '5','6', '5','7', '5','8', '5','9',
            '6','0', '6','1', '6','2', '6','3', '6','4', '6','5', '6','6', '6','7', '6','8', '6','9', '7','0', '7','1', '7','2', '7','3', '7','4', '7','5', '7','6', '7','7', '7','8', '7','9',
            '8','0', '8','1', '8','2', '8','3', '8','4', '8','5', '8','6', '8','7', '8','8', '8','9', '9','0', '9','1', '9','2', '9','3', '9','4', '9','5', '9','6', '9','7', '9','8', '9','9'
    };
    private final byte[] numbuf = new byte[25];

    public ContestPrinter() {
        this.out = System.out;
    }
    public ContestPrinter(java.io.OutputStream out) {
        this.out = out;
    }

    private void write(byte[] bytes) {
        this.write(bytes, 0, bytes.length);
    }

    private void write(byte[] bytes, int begin, int len) {
        if(pos + len > buflen) {
            try {
                this.out.write(buf, 0, pos);
                this.out.write(bytes, begin, len);
                pos = 0;
            } catch (java.io.IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.arraycopy(bytes, begin, buf, pos, len);
            pos += len;
        }
    }

    private void write(byte b) {
        if(pos == buflen) {
            this.flush();
        }
        this.buf[pos++] = b;
    }

    private static String dtos(double x, int n) {
        StringBuilder sb = new StringBuilder();
        if(x < 0){
            sb.append('-');
            x = -x;
        }
        x += Math.pow(10, -n)/2;
        sb.append((long)x);
        sb.append(".");
        x -= (long)x;
        for(int i = 0;i < n;++i){
            x *= 10;
            sb.append((int)x);
            x -= (int)x;
        }
        return sb.toString();
    }

    // calculate the size required to represent a given number in a String
    private static int calcNumStringSize(long x) {
        final long v = x > 0 ? -x : x; // Positive numbers don't over flow into negative number
        final int needSign = x < 0 ? 1 : 0;
        long pow10 = -10;
        for(int i = 1;i < 19; ++i){
            if(v > pow10)return i + needSign;
            pow10 *= 10L;
        }
        return 19 + needSign;
    }

    private void writeAsAsciiBytes(long x) {
        final int digits = calcNumStringSize(x);
        if(x < 0){
            numbuf[0] = '-';
        }else{
            x = -x;
        }
        int pos = digits;
        while (x <= -100) {
            final long q = x/100;
            final int r = (int)( q*100 - x ) << 1;
            x = q;
            --pos;
            numbuf[pos] = Digits[r+1];
            --pos;
            numbuf[pos] = Digits[r];
        }
        final int idx = (int)-x << 1;
        if(x <= -10){
            --pos;
            numbuf[pos] = Digits[idx+1];
            --pos;
            numbuf[pos] = Digits[idx];
        }else{
            --pos;
            numbuf[pos] = Digits[idx+1];
        }

        this.write(numbuf, 0, digits);
    }

    // methods print
    public final void print(String s) {
        this.write(s.getBytes());
    }
    public final void print(boolean x) {
        this.print(String.valueOf(x));
    }
    public final void print(char x) {
        this.print(String.valueOf(x));
    }
    public final void print(byte x) {
        this.print(String.valueOf(x));;
    }
    public final void print(short x) {
        this.print(String.valueOf(x));
    }
    public final void print(int x) {
        this.writeAsAsciiBytes(x);
    }
    public final void print(long x) {
        this.writeAsAsciiBytes(x);
    }
    public final void print(float x) {
        this.print(dtos(x, 20));
    }
    public final void print(double x) {
        this.print(dtos(x, 20));
    }

    // methods println
    public final void println() {
        this.write((byte)'\n');
    }
    public final void println(boolean x){
        this.print(x);
        this.println();
    }
    public final void println(String s) {
        this.print(s);
        this.println();
    }
    public final void println(char x) {
        this.print(x);
        this.println();
    }
    public final void println(byte x) {
        this.print(x);
        this.println();
    }
    public final void println(short x) {
        this.print(x);
        this.println();
    }
    public final void println(int x) {
        this.print(x);
        this.println();
    }
    public final void println(long x) {
        this.print(x);
        this.println();
    }
    public final void println(float x) {
        this.print(x);
        this.println();
    }
    public final void println(double x) {
        this.print(x);
        this.println();
    }

    @Override
    public final void close() {
        try {
            this.flush();
            this.out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public final void flush() {
        try {
            this.out.write(buf, 0, pos);
            pos = 0;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void printlnArray(int[] array, String separator){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(array[i]);
            this.print(separator);
        }
        this.println(array[n-1]);
    }
    public void printlnArray(int[] array){
        this.printlnArray(array, " ");
    }
    public void printlnArray(int[] array, String separator, java.util.function.IntUnaryOperator map){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(map.applyAsInt(array[i]));
            this.print(separator);
        }
        this.println(map.applyAsInt(array[n-1]));
    }
    public void printlnArray(int[] array, java.util.function.IntUnaryOperator map){
        this.printlnArray(array, " ", map);
    }

    public void printlnArray(long[] array, String separator){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(array[i]);
            this.print(separator);
        }
        this.println(array[n-1]);
    }
    public void printlnArray(long[] array){
        this.printlnArray(array, " ");
    }
    public void printlnArray(long[] array, String separator, java.util.function.LongUnaryOperator map){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(map.applyAsLong(array[i]));
            this.print(separator);
        }
        this.println(map.applyAsLong(array[n-1]));
    }
    public void printlnArray(long[] array, java.util.function.LongUnaryOperator map){
        this.printlnArray(array, " ", map);
    }
    public <T> void printlnArray(T[] array, String separator){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(array[i].toString());
            this.print(separator);
        }
        this.println(array[n-1].toString());
    }
    public <T> void printlnArray(T[] array){
        this.printlnArray(array, " ");
    }
    public <T> void printlnArray(T[] array, String separator, java.util.function.UnaryOperator<T> map){
        int n = array.length;
        if(n==0){
            this.println();
            return;
        }
        for(int i=0; i<n-1; ++i){
            this.print(map.apply(array[i]).toString());
            this.print(separator);
        }
        this.println(map.apply(array[n-1]).toString());
    }
    public <T> void printlnArray(T[] array, java.util.function.UnaryOperator<T> map){
        this.printlnArray(array, " ", map);
    }
}


final class ContestScanner {
    private final java.io.InputStream in;
    private final byte[] buffer;
    private final static long MUL_LIMIT = -Long.MIN_VALUE/10;
    private int ptr = 0;
    private int buflen = 0;

    public ContestScanner(java.io.InputStream in) {
        this.in = in;
        this.buffer = new byte[1<<14];
    }
    public ContestScanner(java.io.File file) throws java.io.FileNotFoundException {
        this(new java.io.FileInputStream(file));
    }
    public ContestScanner() {
        this(System.in);
    }

    private boolean hasNextByte() {
        if (ptr < buflen) return true;
        ptr = 0;
        try {
            buflen = in.read(buffer);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return buflen > 0;
    }
    private byte readByte() {
        if(!hasNextByte())throw new java.util.NoSuchElementException();
        return buffer[ptr++];
    }
    private static boolean isPrintableChar(int c) {
        return 33 <= c && c <= 126;
    }
    private static boolean isNumber(int c) {
        return 48 <= c && c <= 57;
    }
    public final boolean hasNext() {
        while(hasNextByte() && !isPrintableChar(buffer[ptr]))++ptr;
        return hasNextByte();
    }

    public final String next() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        byte b = readByte();
        while(isPrintableChar(b)) {
            sb.appendCodePoint(b);
            if(!hasNextByte())break;
            b = readByte();
        }
        return sb.toString();
    }
    public final long nextLong1() {
        long n = this.nextLong();
        if(n == Long.MIN_VALUE) throw new NumberFormatException();
        return --n;
    }
    public final long nextLong() {
        if (!hasNext()) throw new java.util.NoSuchElementException();
        byte b = readByte();
        final boolean minus = b == '-';
        if (minus) {
            b = readByte();
        }

        long n = 0;
        while(isPrintableChar(b)) {
            if (!isNumber(b) || n < MUL_LIMIT)throw new NumberFormatException();// 10倍オーバーフローチェック
            n *= 10;
            final long digit = b - '0';
            if (n < Long.MIN_VALUE + digit)throw new NumberFormatException();// 減算オーバーフローチェック
            n -= digit;
            if(!hasNextByte())break;
            b = readByte();
        }
        if(minus) {
            return n;
        } else {
            if(n == Long.MIN_VALUE)throw new NumberFormatException();
            return -n;
        }
    }
    public final int nextInt1() {
        long n = nextLong1();
        if(n < Integer.MIN_VALUE || n > Integer.MAX_VALUE) throw new NumberFormatException();
        return (int)n;
    }
    public final int nextInt() {
        long nl = nextLong();
        if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
        return (int) nl;
    }
    public final double nextDouble() {
        return Double.parseDouble(next());
    }

    public final long[] nextLongArray(int length){
        long[] array = new long[length];
        for(int i=0; i<length; ++i) array[i] = this.nextLong();
        return array;
    }
    public final long[] nextLongArray1(int length) {
        long[] array = new long[length];
        for(int i = 0;i < length; ++i) array[i] = this.nextLong1();
        return array;
    }
    public final long[] nextLongArray(int length, java.util.function.LongUnaryOperator map){
        long[] array = new long[length];
        for(int i=0; i<length; ++i) array[i] = map.applyAsLong(this.nextLong());
        return array;
    }
    public final int[] nextIntArray(int length){
        int[] array = new int[length];
        for(int i=0; i<length; ++i) array[i] = this.nextInt();
        return array;
    }
    public final int[] nextIntArray1(int length) {
        int[] array = new int[length];
        for(int i = 0;i < length; ++i)array[i] = this.nextInt1();
        return array;
    }
    public final int[] nextIntArray(int length, java.util.function.IntUnaryOperator map){
        int[] array = new int[length];
        for(int i=0; i<length; ++i) array[i] = map.applyAsInt(this.nextInt());
        return array;
    }
    public final double[] nextDoubleArray(int length){
        double[] array = new double[length];
        for(int i=0; i<length; ++i) array[i] = this.nextDouble();
        return array;
    }
    public final double[] nextDoubleArray(int length, java.util.function.DoubleUnaryOperator map){
        double[] array = new double[length];
        for(int i=0; i<length; ++i) array[i] = map.applyAsDouble(this.nextDouble());
        return array;
    }

    public final long[][] nextLongMatrix(int height, int width){
        long[][] mat = new long[height][width];
        for(int h=0; h<height; h++) for(int w=0; w<width; w++){
            mat[h][w] = this.nextLong();
        }
        return mat;
    }
    public final long[][] nextLongMatrix1(int height, int width) {
        long[][] mat = new long[height][width];
        for(int i = 0;i < height; ++i){
            for(int j = 0;j < width; ++j){
                mat[i][j] = this.nextLong1();
            }
        }
        return mat;
    }
    public final int[][] nextIntMatrix(int height, int width){
        int[][] mat = new int[height][width];
        for(int h=0; h<height; h++) for(int w=0; w<width; w++){
            mat[h][w] = this.nextInt();
        }
        return mat;
    }
    public final int[][] nextIntMatrix1(int height, int width) {
        int[][] mat = new int[height][width];
        for(int i = 0;i < height; ++i){
            for(int j = 0;j < width; ++j){
                mat[i][j] = this.nextInt1();
            }
        }
        return mat;
    }
    public final double[][] nextDoubleMatrix(int height, int width){
        double[][] mat = new double[height][width];
        for(int h=0; h<height; h++) for(int w=0; w<width; w++){
            mat[h][w] = this.nextDouble();
        }
        return mat;
    }

    public final char[][] nextCharMatrix(int height, int width){
        char[][] mat = new char[height][width];
        for(int h=0; h<height; h++){
            String s = this.next();
            for(int w=0; w<width; w++){
                mat[h][w] = s.charAt(w);
            }
        }
        return mat;
    }
}
