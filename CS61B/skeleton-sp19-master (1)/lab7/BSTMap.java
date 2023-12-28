import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BST MAP;

    private class BST{

        private K key;
        private V value;
        private int size;
        private BST left, right;

        public BST(K key, V value){
            this.key = key;
            this.value = value;
            this.size = 1;
            this.left = null;
            this.right = null;
        }

        BST get(K k){
            if (k.equals(key)){
                return this;
            }
            else{
                int cmp = k.compareTo(key);
                if (cmp > 0){
                    if (this.right != null) {
                        return right.get(k);
                    }
                    else {
                        return null;
                    }
                }
                else {
                    if (this.left != null){
                        return left.get(k);
                    }
                    else {
                        return null;
                    }
                }
            }
        }
    }

    private class BSTer implements Iterator<K>{
        int num;
        int begin = 1;

        public BSTer(){
            num = size();
        }

        public boolean hasNext(){
            return begin <= num;
        }

        public K next(){
            K item = printhelp(MAP, begin).key;
            begin += 1;
            return item;
        }

    }

    public void clear(){
        MAP = null;
    }

    public boolean containsKey(K key){
        if (MAP == null){
            return false;
        }
        else {
            return MAP.get(key) != null;
        }
    }

    public V get(K key){
        if (MAP == null){
            return null;
        }
        else {
            if (containsKey(key)){
                return MAP.get(key).value;
            }
            else {
                return null;
            }
        }
    }

    public int size() {
        return size(MAP);
    }

    public int size(BST x){
        if (x == null){
            return 0;
        }
        else {
            return x.size;
        }
    }

    public void put(K key, V value){
        if (MAP == null){
            MAP = new BST(key, value);
        }
        else {
            puthelp(MAP, key, value);
        }
    }

    private void puthelp(BST M, K key, V value){
        int cmp = key.compareTo(M.key);
        if (cmp > 0){
            if (M.right != null) {
                puthelp(M.right, key, value);
            }
            else {
                M.right = new BST(key, value);
            }
        }
        else if (cmp < 0){
            if (M.left != null) {
                puthelp(M.left, key, value);
            }
            else {
                M.left = new BST(key, value);
            }
        }
        else {
            M.value = value;
        }
        M.size = 1 + size(M.right) + size(M.left);
    }

    public void printorder(){
        for (int i = 1; i <= size(); i++){
            System.out.println(printhelp(MAP,i).key);
        }
    }

    public BST printhelp(BST M, int x){
        if (x - size(M.left) == 1){
            return M;
        }
        else {
            if (x <= size(M.left)){
                return printhelp(M.left, x);
            }
            else {
                return printhelp(M.right, x - size(M.left) - 1);
            }
        }
    }

    public Set<K> keySet() {
        Set<K> BSTSet = new HashSet<>();
        for (int i = 1; i <= size(); i ++){
            BSTSet.add(printhelp(MAP, i).key);
        }
        return BSTSet;
    }

    public V remove(K key) {
        if (!containsKey(key)){
            return null;
        }
        else {
            V value = get(key);
            MAP = remove(MAP, key);
            return value;
        }
    }

    public V remove(K key, V value) {
        if (!containsKey(key)){
            return null;
        }
        else if (get(key) != value){
            return null;
        }
        MAP = remove(MAP, key);
        return value;
    }

    private BST remove(BST x, K key){
        int cmp = key.compareTo(x.key);
        if (cmp < 0){
            x.left = remove(x.left, key);
        }
        else if (cmp > 0){
            x.right = remove(x.right, key);
        }
        else {
            if (x.right != null){
                BST nodes = deleteMin(x.right);
                BST delectnode = min(x.right);
                delectnode.right = nodes;
                delectnode.left = x.left;
                return delectnode;
            }
            else {
                return x.left;
            }
        }
        x.size = size(x.right) + size(x.left) + 1;
        return x;
    }

    private BST deleteMin(BST x){
        if (x.left == null){
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private BST min(BST x){
        if (x.left == null){
            return x;
        }
        return min(x.left);
    }

    public void print(){
        print(MAP);
    }
    public void print(BST node){
        if (node != null){
            print(node.left);
            System.out.println(node.key);
            print(node.right);
        }
    }


    public Iterator<K> iterator() {
        return new BSTer();
    }

    public static void main(String[] arg){
        BSTMap<Integer, Integer> a = new BSTMap<>();
        a.put(5, 2);
        a.put(2, 8);
        a.put(3, 5);
        a.put(7, 9);
        a.put(11, 3);
        a.put(4, 1);
        a.put(18, 5);
        a.put(9, 1);
        a.print();
        a.printorder();
    }
}
