public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> message = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++){
            Character i_th_word = word.charAt(i);
            message.addLast(i_th_word);
        }
        return message;
    }

    public boolean isPalindrome(String word){
        Deque<Character> message = wordToDeque(word);
        while (message.size() > 1){
            if (message.removeFirst() != message.removeLast()){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> message = wordToDeque(word);
        return Help_of_3(message, cc);
    }

    public boolean isPalindrome1(String word){
        Deque<Character> message = wordToDeque(word);
        return Help_of_2(message);
    }

    public boolean Help_of_2(Deque T){
        if (T.size() <= 1){
            return true;
        }
        else if (T.removeFirst() == T.removeLast()){
            return Help_of_2(T);
        }
        return false;
    }

    public boolean Help_of_3(Deque T, CharacterComparator cc){
        if (T.size() <= 1){
            return true;
        }
        else if (cc.equalChars((char)T.removeFirst(), (char)T.removeLast())){
            return Help_of_3(T, cc);
        }
        return false;
    }
}
