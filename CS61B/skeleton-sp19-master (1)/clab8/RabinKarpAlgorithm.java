public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input.length() < pattern.length()){
            return -1;
        }
        StringBuilder split = new StringBuilder();
        for (int i = 0; i < pattern.length(); i++){
            split.append(input.charAt(i));
        }

        int step = input.length() - pattern.length() + 1;
        RollingString patternroll = new RollingString(pattern, pattern.length());
        RollingString inputroll = new RollingString(split.toString(), pattern.length());

        int index = 0;
        for (int i = pattern.length(); i < input.length(); i++){
            if (patternroll.equals(inputroll)){
                return index;
            }
            else {
                inputroll.addChar(input.charAt(i));
                index += 1;
            }
        }
        return -1;
    }

}
