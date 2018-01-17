import java.util.Scanner;

public class StringPermute {
    public static void permute(String str, String prevLetter) {
        // Base Case:
        // When we find a single character or and empty string, print and and go to a new
        // line for the next permutation
        if (str.length() < 2) {
            if (prevLetter != null) {
                System.out.println(prevLetter + str);
            }
        } else {
            // Loop through each letter, and permute the string for the case where each letter is first
            for (int i = 0; i < str.length(); i ++) {

                String firstLetter = str.substring(i,i+1);

                String remainingLetters = str.substring(0,i) + str.substring(i+1);

                if (prevLetter != null) {
                    permute(remainingLetters, prevLetter + firstLetter);
                } else {
                    permute(remainingLetters, firstLetter);
                }

            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a string");
        String str = s.next();

        permute(str, null);
    }
}
