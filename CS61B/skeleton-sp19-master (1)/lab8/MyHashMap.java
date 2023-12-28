import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;
    private int size;
    private Node<K, V>[] bucket;

    public MyHashMap(){
        this.initialSize = 16;
        this.loadFactor = 0.75;
        bucket = new Node[initialSize];
        size = 0;
    }

    public MyHashMap(int initialSize){
        this.initialSize = initialSize;
        this.loadFactor = 0.75;
        bucket = new Node[initialSize];
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor){
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bucket = new Node[initialSize];
        size = 0;
    }

    public void clear(){
        bucket = new Node[initialSize];
        size = 0;
    }

    public boolean containsKey(K key){
        return get(key) != null;
    }

    public V get(K key){
        int num = gethashnum(key, initialSize);
        Node<K, V> p = find(bucket[num], key);
        if (p != null){
            return p.getValue();
        }
        else {
            return null;
        }
    }

    public int size(){
        return size;
    }

    private Node<K, V> find(Node<K, V> p, K key){
        while (p != null){
            if (p.getKey() .equals(key)){
                return p;
            }
            p = p.getNext();
        }
        return null;
    }


    public void put(K key, V value){
        int num = gethashnum(key, initialSize);
        Node<K, V> p = find(bucket[num], key);
        if (p == null){
            Node<K, V> newp = new Node<>(key, value);
            Node<K, V> oldnext = bucket[num];
            newp.next = oldnext;
            bucket[num] = newp;
            size += 1;
            if ((double) size / initialSize >= loadFactor){
                resize();
            }
        }
        else {
            p.value = value;
        }
    }

    public Set<K> keySet(){
        Set<K> aset = new HashSet<>();
        for (int i = 0; i < initialSize; i++){
            Node<K, V> p = bucket[i];
            while (p != null){
                aset.add(p.getKey());
                p = p.getNext();
            }
        }
        return aset;
    }

    private void resize(){
        Node<K, V>[] newbucket = new Node[2 * initialSize];
        for (int i = 0; i < initialSize; i++){
            Node<K, V> p = bucket[i];
            while (p != null){
                int newnum = gethashnum(p.getKey(), 2 * initialSize);
                Node<K, V> oldp = p.getNext();
                p.next = newbucket[newnum];
                newbucket[newnum] = p;
                p = oldp;
            }
        }
        bucket = newbucket;
        initialSize *= 2;
    }

    private class Hasher implements Iterator<K>{
        int begin = 0;
        int num;

        public Hasher(){
            num = size();
        }

        public boolean hasNext(){
            return begin < num;
        }

        public K next(){
            K item = other(0, begin);
            begin += 1;
            return item;
        }
    }

    public Iterator<K> iterator(){
        return new Hasher();
    }

    public V remove(K key){
        int num = gethashnum(key, initialSize);
        Node<K, V> p = bucket[num];
        if (p.getKey().equals(key)){
            V item = p.getValue();
            bucket[num] = p.getNext();
            size -= 1;
            return item;
        }
        while (p.getNext().getKey().equals(key)){
            p = p.getNext();
        }
        V item = p.getNext().value;
        p.next = p.getNext().getNext();
        return item;
    }

    public V remove(K key, V value){
        int num = gethashnum(key, initialSize);
        Node<K, V> p = bucket[num];
        if (p.getKey().equals(key) && p.getValue().equals(value)){
            bucket[num] = p.getNext();
            size -= 1;
            return value;
        }
        while (p.getNext().getKey().equals(key)){
            p = p.getNext();
        }
        if (p.getNext().getValue().equals(value)){
            p.next = p.getNext().getNext();
            return value;
        }
        return null;
    }

    private static class Node<K, V>{
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey(){
            return key;
        }

        public V getValue(){
            return value;
        }

        public Node<K, V> getNext(){
            if (this.next !=null){
                return this.next;
            }
            return null;
        }
    }

    public K other(int num, int step){
        if (bucket[num] == null){
            return other(num + 1, step);
        }
        else {
            return otherhelp(bucket[num], num, step);
        }
    }

    public K otherhelp(Node<K, V> node, int num, int step){
        if (step == 0){
            return node.getKey();
        }
        else {
            if (node.getNext() == null){
                return other(num + 1, step - 1);
            }
            else {
                return otherhelp(node.getNext(), num, step - 1);
            }
        }
    }


    private int gethashnum(K key, int initialSize){
        return (key.hashCode() & 0x7fffffff) % initialSize;
    }

    public static void main(String[] args){
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 20; i++) {
            b.put("hi" + i, 1);
        }
        for (String str: b){
            System.out.println(str);
        }
    }
}
