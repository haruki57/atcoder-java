import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ABC324D_Trie_TLE {
    static int MOD = 1000000007;
    static int INF = Integer.MAX_VALUE/2;


    static Set<String> set = new HashSet<>();
    static void run (final FastScanner scanner, final PrintWriter out) {
        int N = scanner.nextInt();
        char[] s = scanner.next().toCharArray();
        Trie trie = new Trie();
        for (long i = 1; i * i< 10000000000000L; i++) {
            if (digitNum(i*i) > N) {
                break;
            }
            trie.insert(pad(String.valueOf(i*i), '0', N));
        }
        backtrack(s, trie, 0, new StringBuilder());
        // System.out.println(backtrack(s, trie, 0, new StringBuilder()));
        System.out.println(set.size());
    }

    private static int backtrack(char[] s, Trie trie, int digit, StringBuilder sb) {
        if (digit == s.length) {
            set.add(sb.toString());
            return 1;
        }
        int ret = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i]==0) {
                continue;
            }
            char tmp = s[i];
            s[i]=0;
            sb.append(tmp);
            if (trie.startsWith(sb)) {
                ret += backtrack(s, trie, digit+1, sb);
            }
            sb.deleteCharAt(sb.length()-1);
            s[i]=tmp;
        }
        return ret;
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


    static int digitNum(long l) {
        if (l==0) {
            return 1;
        }
        int ret =0;
        while(l>0) {
            ret++;
            l/=10;
        }
        return ret;
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
            }
            currNode.setWord(true);
        }

        /** Returns if the word is in the trie. */

        public boolean search(StringBuilder word) {
            if (word == null || word.length() == 0) {
                return false;
            }
            TrieNode node = searchPrefix(word, root);
            if (node == null) {
                return false;
            }
            return node.isWord();
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(StringBuilder prefix) {
            if (prefix == null || prefix.length() == 0) {
                return false;
            }
            TrieNode node = searchPrefix(prefix, root);
            if (node == null) {
                return false;
            }
            return true;
        }

        private TrieNode searchPrefix(StringBuilder prefix, TrieNode root) {
            if (prefix == null || prefix.length() == 0 || root == null) {
                return null;
            }
            TrieNode currNode = root;

            for (int i = 0; i < prefix.length(); i++) {
                char ch = prefix.charAt(i);
                TrieNode child = currNode.getChildNodes().get(ch);
                if (child == null) {
                    return null;
                }
                currNode = child;
            }
            return currNode;
        }

        private static class TrieNode {
            //「後続の文字 → 後続の文字に対応する子ノード」のマップ
            // 例えば、「root -> "a" -> "p" -> "p" -> "l" -> "e"」というツリーがあったとして、
            // "a" のノードにいる時、"p" を key とする子ノードがあれば、"ap" までは持っていると判断する。
            private Map<Character, TrieNode> childNodes = new HashMap<>();

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
