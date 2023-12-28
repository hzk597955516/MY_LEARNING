public class OffByN implements CharacterComparator {
    int N;
    public OffByN(int n){
        N = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int l = x - y;
        if (l == N || l == -N){
            return true;
        }
        else {
            return false;
        }
    }
}
