import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC357_2 {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int[] a = new int[N];
        Arrays.setAll(a, i -> scanner.nextInt()-1);
        System.out.println(functionalGraph(a));
    }

    static long functionalGraph(int[] x) {
        long ret = 0;
        int N = x.length;
        int[] inCnt = new int[N];
        for (int i = 0; i < x.length; i++) {
            inCnt[x[i]]++;
        }
        List<Integer> inZeros = new LinkedList<>();
        for (int i = 0; i < inCnt.length; i++) {
            if(inCnt[i]==0) {
                inZeros.add(i);
            }
        }
        Set<Integer> visited = new HashSet<>();
        long[] memo = new long[N];
        for (Integer i : inZeros) {
            visited.add(i);
            Queue<Integer> q = new LinkedList<>();
            List<Integer> path = new ArrayList<>();
            Set<Integer> visitedLocal = new HashSet<>();
            q.add(i);
            visitedLocal.add(i);
            visited.add(i);
            int loopStart = -1;
            int lastVisited = -1;
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                int next = x[cur];
                path.add(cur);
                visited.add(cur);
                visitedLocal.add(cur);
                if(visitedLocal.contains(next)) {
                    loopStart = next;
                    lastVisited=next;
                    break;
                }
                if(visited.contains(next)) {
                    lastVisited=next;
                    break;
                }
                q.add(next);
            }
            if(loopStart==-1) {
                // existing loop
                int i0=path.size();
                for (Integer p : path) {
                    memo[p]=memo[lastVisited]+i0--;
                }
            } else {
                List<Integer> pathToLoop = new ArrayList<>();
                for (Integer integer : path) {
                    if(integer==lastVisited){
                        break;
                    }
                    pathToLoop.add(integer);
                }
                List<Integer> loop = new ArrayList<>();
                for (int j = pathToLoop.size(); j < path.size(); j++) {
                    loop.add(path.get(j));
                }
                long loopSize = loop.size();
                for (Integer p : loop) {
                    memo[p]= loopSize;
                }
                int i0 = pathToLoop.size();
                for (Integer p : pathToLoop) {
                    memo[p] = loopSize + i0--;
                }
            }
        }
        for (long l : memo) {
            ret += l;
        }

        // 独立したループ
        for (int i = 0; i < N; i++) {
            if(visited.contains(i)) {
                continue;
            }
            visited.add(i);
            Queue<Integer> q = new LinkedList<>();
            List<Integer> path = new ArrayList<>();
            q.add(i);
            while(!q.isEmpty()) {
                Integer cur = q.poll();
                int next = x[cur];
                path.add(cur);
                visited.add(cur);
                if(visited.contains(next)) {
                    break;
                }
                q.add(next);
            }
            long size = path.size();
            ret += size * size;
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
