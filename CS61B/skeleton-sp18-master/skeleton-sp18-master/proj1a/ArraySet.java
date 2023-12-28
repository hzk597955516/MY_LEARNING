public class ArraySet<T> {
    private int size;
    private T[] sets;
    public ArraySet(){
        sets = (T[])new Object[100];
        size = 0;
    }

    public boolean contains(T x){
        for (int i = 0; i < size; i++){
            if (sets[i].equals(x)){
                return true;
            }
        }
        return false;
    }

    public void add(T x){
        if (x == null){
            throw new IllegalArgumentException("can't add null");
        }
        if (contains(x)){
            return;
        }
        sets[size] = x;
        size += 1;
    }

    public int size(){
        return size;
    }

    public static int f3(int n){
        if (n <= 1){
            return 1;
        }
        return f3(n-1) + f3(n-1);
    }

    public static void main(String[] args){
        ArraySet<String> s = new ArraySet<>();
        s.add("horse");
        s.add("fish");
        s.add("house");
        s.add("fish");
        System.out.println(s.contains("horse"));
        System.out.println(s.size());
    }
}
