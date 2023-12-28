import org.junit.Test;
import static org.junit.Assert.*;
public class MapHelper {
    public static <k, v> v get(ArrayMap<k, v> ma, k key){
        int index = ma.get_key(key);
        if (index < 0){
            return null;
        }
        return ma.get(key);
    }

    public static <k extends Comparable<k>, v> k maxKey(ArrayMap<k, v> ma){
        ArrayDeque<k> keylist = ma.keys();
        k largest = keylist.get(0);
        for (int i = 0; i < keylist.size(); i++){
            if (keylist.get(i).compareTo(largest) > 0){
                largest = keylist.get(i);
            }
        }
        return largest;
    }

    @Test
    public void test(){
        ArrayMap<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("horse", 3);
        m.put("fish", 9);
        m.put("house", 10);
        Integer actual = MapHelper.get(m, "fish");
        Integer expected = 9;
        assertEquals(expected, actual);
    }
}
