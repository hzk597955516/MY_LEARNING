package byow.Core;

import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardinputSource implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                return Character.toUpperCase(StdDraw.nextKeyTyped());
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
