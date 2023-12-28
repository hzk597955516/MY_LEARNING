package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> minheap;
    private HashMap<T, Integer> itemMapIndex;
    private int size;

    public ArrayHeapMinPQ(){
        minheap = new ArrayList<>();
        itemMapIndex = new HashMap<>();
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }
    public boolean contains(T item){
        if (isEmpty()){
            return false;
        }
        return itemMapIndex.containsKey(item);
    }

    public void add(T item, double priority){
        if (contains(item)){
            throw new IllegalArgumentException();
        }
        minheap.add(new PriorityNode(item, priority));
        size += 1;
        itemMapIndex.put(item, size() - 1);
        swinup(size - 1);
    }

    public void changePriority(T item, double priority){
        if (isEmpty() || !contains(item)){
            throw new NoSuchElementException();
        }
        int index = itemMapIndex.get(item);
        double oldPriority = minheap.get(index).getPriority();
        minheap.get(index).setPriority(priority);
        if (oldPriority < priority){
            swindown(index);
        }
        else {
            swinup(index);
        }
    }

    public T removeSmallest(){
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        T item = getSmallest();
        swap(0, size() - 1);
        minheap.remove(size() - 1);
        itemMapIndex.remove(item);
        size -= 1;
        swindown(0);
        return item;
    }

    public T getSmallest(){
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        return minheap.get(0).getItem();
    }

    private int leftchild(int k){
        return k * 2 + 1;
    }

    private int rightchild(int k){
        return k * 2 + 2;
    }

    private int parent(int k){
        return (k - 1) / 2;
    }

    private void swap(int k1, int k2){
        PriorityNode t = minheap.get(k2);
        itemMapIndex.put(minheap.get(k1).getItem(), k2);
        itemMapIndex.put(minheap.get(k2).getItem(), k1);
        minheap.set(k2, minheap.get(k1));
        minheap.set(k1, t);
    }

    private void swinup(int k){
        if (minheap.get(parent(k)).getPriority() > minheap.get(k).getPriority()){
            swap(k, parent(k));
            swinup(parent(k));
        }
    }

    private void swindown(int k){
        int smallest = k;
        if (leftchild(k) <= size() - 1 && smaller(leftchild(k), k)){
            smallest = leftchild(k);
        }
        if (rightchild(k) <= size() - 1 && smaller(rightchild(k), smallest)){
            smallest = rightchild(k);
        }
        if (smallest != k){
            swap(k, smallest);
            swindown(smallest);
        }
    }

    private boolean smaller(int i, int j){
        return minheap.get(i).getPriority() < minheap.get(j).getPriority();
    }

    private class PriorityNode{
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        ArrayHeapMinPQ<Integer> minHeap = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 200000; i += 1) {
            minHeap.add(i, 100000 - i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        long start2 = System.currentTimeMillis();
        for (int j = 0; j < 200000; j += 1) {
            minHeap.changePriority(j, j + 1);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
}
