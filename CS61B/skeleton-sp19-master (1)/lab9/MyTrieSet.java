import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B{
    public Node root;
    private int n;

    private static class Node {
        private Node[] next = new Node[128];
        private boolean is;

        public Node(boolean x){
            is = x;
        }
    }

    public void clear(){
        root = null;
    }

    public boolean contains(String key){
        return get(root, key, 0) != null;
    }

    public void add(String key){
        if (root == null){
            root = new Node(true);
        }
        Node x = root;
        for (int i = 0; i < key.length(); i++){
            int c = key.charAt(i);
            if (x.next[c] == null){
                x.next[c] = new Node(false);
            }
            if (i == key.length() - 1){
                x.next[c].is = true;
            }
            x = x.next[c];
        }
    }

    public List<String> keysWithPrefix(String prefix){
        List<String> keys = new ArrayList<>();
        Node begin = listget(root, prefix, 0);
        if (begin != null) {
            help(begin, keys, prefix);
        }
        return keys;
    }

    private void help(Node x, List<String> str, String key){
        for (int i = 0; i < 128; i++) {
            if (x.next[i] != null) {
                char c = (char) i;
                String newkey = key + c;
                if (x.next[i].is){
                    str.add(newkey);
                }
                help(x.next[i], str, newkey);
            }
        }
    }


    private boolean isgo(Node[] x){
        for (int i = 0; i < x.length; i++){
            if (x[i] != null){
                return true;
            }
        }
        return false;
    }

    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    }

    private Node get(Node x, String key, int l){
        if (x == null){
            return null;
        }
        if (l == key.length() && x.is){
            return x;
        }
        if (l == key.length()){
            return null;
        }
        char c = key.charAt(l);
        return get(x.next[c], key, l + 1);
    }

    public Node listget(Node x, String key, int l){
        if (x == null){
            return null;
        }
        if (l == key.length()){
            return x;
        }
        char c = key.charAt(l);
        return listget(x.next[c], key, l + 1);
    }

    public static void main(String[] args){
        MyTrieSet a = new MyTrieSet();
        a.add("abc");
        a.add("abcd");
        a.add("abcde");
        a.add("acb");
        a.add("bac");
        List<String> w = a.keysWithPrefix("a");
    }
}
