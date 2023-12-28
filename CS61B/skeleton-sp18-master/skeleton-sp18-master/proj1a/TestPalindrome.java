import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome(){
        String d = "asdfgfdsa";
        String b = "asdfghj";
        assertTrue(palindrome.isPalindrome(d));
        assertFalse(palindrome.isPalindrome(b));
    }

    @Test
    public void testisPalindromeOffByOne(){
        String d = "flake";
        String b = "asdfghj";
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(d, cc));
        assertFalse(palindrome.isPalindrome(b, cc));
    }

    @Test
    public void testisPalindromeOffByN(){
        CharacterComparator cc = new OffByN(5);
        assertTrue(palindrome.isPalindrome("abchgf", cc));
        assertFalse(palindrome.isPalindrome("abcdefg", cc));

    }
}
