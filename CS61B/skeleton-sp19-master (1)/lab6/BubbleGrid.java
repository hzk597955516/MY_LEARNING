
public class BubbleGrid {
    private UnionFind grid_relative;
    private int[] real_gird;

    private int row;
    private int column;
    private int N;

    public BubbleGrid(int[][] grid){
        row = grid.length;
        column = grid[0].length;
        N = row * column;
        grid_relative = new UnionFind(N + 1);
        real_gird = new int[N];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                real_gird[i * column + j] = grid[i][j];
            }
        }
    }

    public void HelpFun(int i){
            if (i < column){
                grid_relative.union(i, N);
            }
            else{
                if (i < N - column) {
                    if (real_gird[i + column] == 1) {
                        grid_relative.union(i + column, i);
                    }
                }
                if (real_gird[i - column] == 1){
                    grid_relative.union(i-column, i);
                }

                if (i % column == 0 || i % column == column - 1) {
                    if (i % column == 0) {
                        if (real_gird[i + 1] == 1) {
                            grid_relative.union(i + 1, i);
                        }
                    }
                    if (i % column == column - 1) {
                        if (real_gird[i - 1] == 1) {
                            grid_relative.union(i - 1, i);
                        }
                    }
                }
                else{
                    if (real_gird[i + 1] == 1) {
                        grid_relative.union(i + 1, i);
                    }
                    if (real_gird[i - 1] == 1) {
                        grid_relative.union(i - 1, i);
                    }
                }
            }

    }

    public int[] popBubbles(int[][] darts){
        for (int i = 0; i < darts.length; i++){
            int n = darts[i][0] * column + darts[i][1];
            if (real_gird[n] == 1) {
                real_gird[n] = 0;
            }
            else{
                real_gird[n] = 2;
            }
        }

        for (int i = 0; i < N; i++){
            if (real_gird[i] == 1) {
                HelpFun(i);
            }
        }

        int f_size = grid_relative.sizeOf(N);
        int[] resualt = new int[darts.length];

        for (int i = darts.length - 1 ; i >= 0; i--){
            int n = darts[i][0] * column + darts[i][1];
            if (real_gird[n] == 0) {
                real_gird[n] = 1;
                HelpFun(n);
                resualt[i] = grid_relative.sizeOf(N) - 1 - f_size;
                f_size = grid_relative.sizeOf(N);
            }
        }

        return resualt;
    }

    public static void main(String[] args){
        int [][] grid = {{1, 1, 0, }, {1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int [][] dark = {{1, 0}};
        BubbleGrid a = new BubbleGrid(grid);

        int[] h = a.popBubbles(dark);
        for (int i = 0; i < h.length; i++) {
            System.out.println(h[i]);
        }

    }
}
