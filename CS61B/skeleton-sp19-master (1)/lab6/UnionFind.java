public class UnionFind {

    private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++){
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= parent.length || vertex < 0){
            throw new IllegalArgumentException("It isn't a valid index");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -parent[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        int long1 = sizeOf(v1);
        int long2 = sizeOf(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 != root2) {
            if (long2 >= long1) {
                parent[root2] += parent[root1];
                parent[root1] = root2;
            } else {
                parent[root1] += parent[root2];
                parent[root2] = root1;
            }
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int r = vertex;
        int s = vertex;
        int track;
        while (parent[r] >= 0){
            r = parent[r];
        }
        while (parent[s] >= 0){
            track = parent[s];
            if (parent[s] >= 0){
                parent[s] = r;
            }
            s = track;
        }
        return r;
    }

    public static void main(String[] args){
        UnionFind a = new UnionFind(10);
        a.union(1, 8);
        a.union(1, 9);
        a.union(4, 6);
        a.union(1, 5);
        a.union(6, 7);
        a.union(1, 7);
        System.out.println(a.sizeOf(1));
    }
}
