import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator OffByN = new OffByN(5);

    @Test
    public void testOffByN(){
        assertTrue(OffByN.equalChars('a', 'f'));
        assertFalse(OffByN.equalChars('f', 'h'));
    }
}
