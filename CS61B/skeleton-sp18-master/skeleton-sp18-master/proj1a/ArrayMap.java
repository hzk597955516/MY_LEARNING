public class ArrayMap<k, v> {
    private int size;
    private k[] key;
    private v[] value;
    public ArrayMap(){
        key = (k[]) new Object[100];
        value = (v[]) new Object[100];
        size = 0;
    }

    public int get_key(k key){
        for (int i = 0; i < size; i++){
            if (this.key[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public void put(k key, v value){
        int index = get_key(key);
        if (index < 0){
            index = size;
            size += 1;
        }
        this.key[index] = key;
        this.value[index] = value;
    }

    public boolean containsKey(k key){
        return get_key(key) > -1;
    }

    public v get(k key){
        return this.value[get_key(key)];
    }

    public ArrayDeque<k> keys(){
        ArrayDeque Keyss = new ArrayDeque();
        for (int i = 0; i < size; i++){
            Keyss.addLast(this.key[i]);
        }
        return Keyss;
    }

    public int size(){
        return size;
    }

    public static void main(String[] args){
        ArrayMap<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("horse", 3);
        m.put("fish", 9);
        m.put("house", 10);
        System.out.println(m.get("fish"));
    }
}
