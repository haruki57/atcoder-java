import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ABC353E {
    static int MOD = 998244353;
    static int INF = Integer.MAX_VALUE/2;

    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        String[] s = new String[N];
        Trie trie = new Trie();
        for (int i = 0; i < s.length; i++) {
            s[i]= scanner.next();
            trie.insert(s[i]);
        }
        long ans = 0;
        for (int i = 0; i < N; i++) {
            //System.out.println("search:"+trie.search(s[i]));
            ans += trie.search(s[i])-s[i].length();
        }
        System.out.println(ans/2);
    }


    static class Trie {

        TrieNode root = new TrieNode();

        /** Initialize your data structure here. */
        public Trie() {

        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            TrieNode currNode = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                TrieNode child = currNode.getChildNodes().get(ch);
                if (child == null) {
                    TrieNode newNode = new TrieNode();
                    currNode.getChildNodes().put(ch, newNode);
                    currNode = newNode;
                }
                else {
                    currNode = child;
                }
                currNode.cnt++;
                //System.out.println(ch+" "+ currNode.cnt);
            }
            currNode.setWord(true);
        }

        /** Returns if the word is in the trie. */

        public long search(String word) {
            if (word == null || word.isEmpty()) {
                return 0;
            }
            return searchPrefix(word, root);
        }

        private long searchPrefix(String prefix, TrieNode root) {
            if (prefix == null || prefix.isEmpty() || root == null) {
                return 0;
            }
            TrieNode currNode = root;
            long ret = 0;
            for (int i = 0; i < prefix.length(); i++) {
                char ch = prefix.charAt(i);
                TrieNode child = currNode.getChildNodes().get(ch);
                if (child == null) {
                    return ret;
                }
                currNode = child;
                ret += currNode.cnt;
            }
            return ret;
        }

        private static class TrieNode {
            //「後続の文字 → 後続の文字に対応する子ノード」のマップ
            // 例えば、「root -> "a" -> "p" -> "p" -> "l" -> "e"」というツリーがあったとして、
            // "a" のノードにいる時、"p" を key とする子ノードがあれば、"ap" までは持っていると判断する。
            final private Map<Character, TrieNode> childNodes = new HashMap<>();
            long cnt = 0;

            // このノードが単語(終端)かどうかのフラグ
            private boolean isWord = false;

            private Map<Character, TrieNode> getChildNodes() {
                return childNodes;
            }

            private boolean isWord() {
                return isWord;
            }

            private void setWord(boolean word) {
                isWord = word;
            }
        }
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
