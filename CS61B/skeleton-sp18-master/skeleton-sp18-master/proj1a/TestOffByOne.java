import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testisOffByOne(){
        char a = 'a';
        char b = 'b';
        char k = 'k';
        assertTrue(offByOne.equalChars(a, b));
        assertFalse(offByOne.equalChars(a, k));
    }
}
