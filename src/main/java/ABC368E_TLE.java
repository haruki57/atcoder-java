import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC368E_TLE {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        long[] x = new long[M];
        x[0]=scanner.nextLong();
        List<long[]>[]g = new List[N];
        for (int i = 0; i < g.length; i++) {
            g[i]=new ArrayList<>();
        }
        long[][] trains = new long[M][4];
        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt()-1;
            int b = scanner.nextInt()-1;
            int s = scanner.nextInt();
            int t = scanner.nextInt();
            g[a].add(new long[]{b,s,t,i});
            trains[i][0]=a;
            trains[i][1]=b;
            trains[i][2]=s;
            trains[i][3]=t;
        }

        PriorityQueue<long[]> q = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return -Long.compare(o1[0], o2[0]);
            }
        });
        q.add(new long[]{x[0], 0});

        boolean[] visited = new boolean[M];
        while(!q.isEmpty()) {
            long[] poll = q.poll();
            long curX = poll[0];
            int curTrain = (int)poll[1];
            int start = (int)trains[curTrain][0];
            int goal = (int)trains[curTrain][1];
            long t = trains[curTrain][3];
            if(visited[curTrain]) {
                continue;
            }
            visited[curTrain]=true;
            //System.out.println();
            //System.out.println(curTrain+" "+goal+" "+t);
            for (long[] nextTrains : g[goal]) {
                int next = (int)nextTrains[0];
                long s = nextTrains[1];
                int nextTrain = (int)nextTrains[3];
                //System.out.println(nextTrain+" "+s);
                if(t <= s && t + x[curTrain] > s) {
                    if(x[nextTrain] < t+x[curTrain]-s) {
                        x[nextTrain] = Math.max(x[nextTrain], t+x[curTrain]-s);
                        q.add(new long[]{x[nextTrain], nextTrain});
                    }
                }

            }
        }
        for (int i = 1; i < x.length; i++) {
            out.print(x[i]+" ");
        }
        out.println();


        /*
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        boolean[] visited = new boolean[N];
        while(!q.isEmpty()) {
            int curNode = q.poll();
            //System.out.println("curNode:"+curNode);
            visited[curNode]=true;
            for (long[] curTrain : g[curNode]) {
                int next = (int)curTrain[0];
                long t = curTrain[2];
                int trainNum = (int)curTrain[3];
                //System.out.println();
                //System.out.println("curNode:"+curNode+" "+next+" "+t+" "+trainNum);
                for (long[] nextTrain : g[next]) {
                    long s = nextTrain[1];
                    int train2Num = (int)nextTrain[3];
                    //System.out.println("next:"+s+" "+train2Num);
                    if(t <= s && t + x[trainNum] > s) {
                        x[train2Num] = Math.max(x[train2Num], t+x[trainNum]-s);
                        //System.out.println("x:"+" "+x[train2Num]);
                    }
                }
                if(!visited[next]) {
                    visited[next]=true;
                    q.add(next);
                }
            }
        }

         */

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
