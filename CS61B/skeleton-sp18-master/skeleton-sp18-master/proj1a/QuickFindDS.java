public class QuickFindDS implements DisjointSets{
    private int[] parent;

    public QuickFindDS(int num){
        parent = new int[num];
        for (int i = 0; i < num; i++){
            parent[i] = i;
        }
    }

    private int find(int p){
        while (parent[p] >= 0) {
            p = parent[p];
        }
        return p;
    }

    @Override
    public void connect(int p, int q){
        int i = find(p);
        int j = find(q);
        parent[i] = j;
    }

    @Override
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }
}
