package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid_2D;
    private int num_of_open;
    private int N;
    private WeightedQuickUnionUF grid_1D;

    public Percolation(int N){
        grid_2D = new int[N][N];
        grid_1D = new WeightedQuickUnionUF(N * N + N + 1);
        num_of_open = 0;
        this.N = N;

        for (int i = 0; i < N; i++){
            for (int j = 2; j < N; j++){
                grid_2D[i][j] = 0;
            }
        }
    }

    public void open(int row, int col){
        if (grid_2D[row][col] != 1) {
            is_outside(row, col);
            grid_2D[row][col] = 1;
            num_of_open += 1;
            Topcheck(row, col);
            Bottoncheck(row, col);
            Leftcheck(row, col);
            Rightcheck(row, col);
        }
    }

    public boolean isOpen(int row, int col){
        is_outside(row, col);
        return grid_2D[row][col] == 1;
    }

    public boolean isFull(int row, int col){
        is_outside(row, col);
        int OneDnum = TowDtoOneD(row, col);
        return grid_1D.connected(OneDnum, N * N + N);
    }

    public int numberOfOpenSites(){
        return num_of_open;
    }

    public boolean percolates(){
        int k = 0;
        for (int i = 0; i < N; i++){
            if (grid_1D.connected(N * N + N, N * N + i)){
                k = 1;
            }
            if (k == 1){
                return true;
            }
        }
        return false;
    }

    private int TowDtoOneD(int row, int col){
        is_outside(row, col);
        return row * N + col;
    }

    private void Topcheck(int row, int col){
        int OneDnum = TowDtoOneD(row, col);
        if (row == 0){
            grid_1D.union(OneDnum, N * N + N);
        }
        else {
            if (isOpen(row - 1, col)){
                grid_1D.union(OneDnum, TowDtoOneD(row - 1, col));
            }
        }
    }

    private void Bottoncheck(int row, int col){
        int OneDnum = TowDtoOneD(row, col);
        if (row == N - 1){
            grid_1D.union(OneDnum, N * N + col);
        }
        else {
            if (isOpen(row + 1, col)){
                grid_1D.union(OneDnum, TowDtoOneD(row + 1, col));
            }
        }
    }

    private void Leftcheck(int row, int col){
        int OneDnum = TowDtoOneD(row, col);
        if (col != 0){
            if (isOpen(row, col - 1)){
                grid_1D.union(OneDnum, TowDtoOneD(row, col - 1));
            }
        }
    }

    private void Rightcheck(int row, int col){
        int OneDnum = TowDtoOneD(row, col);
        if (col != N - 1){
            if (isOpen(row, col + 1)){
                grid_1D.union(OneDnum, TowDtoOneD(row, col + 1));
            }
        }
    }
    private void is_outside(int row, int col){
        if (row < 0 || col < 0 || row >= N || col >= N){
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args){
        Percolation a = new Percolation(3);
        a.open(0, 0);
        a.open(1, 0);
        System.out.println(a.percolates());
        a.open(2, 0);
        System.out.println(a.percolates());
    }
}
