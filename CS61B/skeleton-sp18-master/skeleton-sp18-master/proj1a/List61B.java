public interface List61B<Item> {
    public void addLast(Item x);
    public Item get(int i);
    public int size();
    public Item removeLast();
    public void insert(Item x, int position);
    public void addFirst(Item x);
    public Item getFirst();
    default public void print(){
        for (int i = 0; i < size(); i++){
            System.out.print(get(i) + " ");
        }
    }
}
