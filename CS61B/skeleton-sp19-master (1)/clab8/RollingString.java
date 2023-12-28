/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private int prehashnumber= 0;

    private int first;

    private int length;


    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        this.length = length;
        for (int i = 0; i < s.length(); i++){
            int ithchar = s.charAt(i);
            prehashnumber *= UNIQUECHARS;
            prehashnumber += ithchar;
            if (i == 0){
                first = ithchar;
            }
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        int minusone = first;
        for (int i = 1; i < length; i++){
            minusone *= UNIQUECHARS;
        }
        prehashnumber -= minusone;
        prehashnumber *= UNIQUECHARS;
        prehashnumber += c;
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        int num = prehashnumber;
        for (int i = 0; i < length; i++){
            int mod = num % UNIQUECHARS;
            if (mod == 0){
                mod = UNIQUECHARS;
            }
            strb.append((char) mod);
            num = (num - mod) / UNIQUECHARS;
        }
        return strb.reverse().toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return length;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        RollingString other = (RollingString)o;
        return prehashnumber == other.prehashnumber;
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        return prehashnumber / PRIMEBASE;
    }

    public static void main(String[] args){
        RollingString a = new RollingString("abcd", 4);
        RollingString b = new RollingString("abcd", 4);
        System.out.println(a.equals(b));
        a.addChar('e');
        System.out.println(a.toString());
        System.out.println(a.length);
    }
}
