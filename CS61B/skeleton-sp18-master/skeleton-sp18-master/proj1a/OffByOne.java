public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int l = x - y;
        if (l == -1 || l == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
