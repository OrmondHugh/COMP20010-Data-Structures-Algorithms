import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class WordsCollisions {

    static int hash(String s, int a) {

        int result = s.charAt(0);

        for (int i = 1; i < s.length(); i++) {
            result = result*a + s.charAt(i);
        }

        return result;
    }

    static int hashCyclic(String s) {

        int result = 0;

        for(int i = 0; i < s.length(); i++) {
            result = (result << 7) | (result >>> 25);
            result += (int)s.charAt(i);
        }

        return result;
    }

    static int hashOld(String s) {
        int hash = 0;

        int skip = Math.max(1, s.length() / 8);
        for (int i = 0; i < s.length(); i += skip) {
            hash = (hash * 37) + s.charAt(i);
        }

        return hash;
    }

    public static void main(String[] args) {

        try {
            // Set up the scanner reading the words from the text
            String currentFilePath = new File("").getAbsolutePath();
            FileReader listOfWords = new FileReader(currentFilePath + "\\src\\words.txt");
            Scanner s = new Scanner(listOfWords);

            // Array list where we will store the hash values and then check for collisions
            ArrayList<Integer> hashCodes = new ArrayList<>();
            int collisions = 0;

            // Run for a = 41
            while (s.hasNext()) {

                String currentWord = s.next();

                int hashCode = hash(currentWord, 41);

                if (hashCodes.contains(hashCode)) {
                    collisions++;
                } else {
                    hashCodes.add(hashCode);
                }
            }
            System.out.println("Collisions for a=41: " + collisions);

            // Run the same for a = 17
            // Reset the collisions count, the arraylist and the file reader each time we test a function
            collisions = 0;
            ArrayList<Integer> hashCodes2 = new ArrayList<>();
            FileReader listOfWords2 = new FileReader(currentFilePath + "\\src\\words.txt");
            Scanner s2 = new Scanner(listOfWords2);
            while (s2.hasNext()) {

                String currentWord = s2.next();

                int hashCode = hash(currentWord, 17);

                if (hashCodes2.contains(hashCode)) {
                    collisions++;
                } else {
                    hashCodes2.add(hashCode);
                }
            }
            System.out.println("Collisions for a=17: " + collisions);

            // Now test for collisions with the cyclic hash function
            collisions = 0;
            ArrayList<Integer> hashCodes3 = new ArrayList<>();
            FileReader listOfWords3 = new FileReader(currentFilePath + "\\src\\words.txt");
            Scanner s3 = new Scanner(listOfWords3);
            while (s3.hasNext()) {

                String currentWord = s3.next();

                int hashCode = hashCyclic(currentWord);

                if (hashCodes3.contains(hashCode)) {
                    collisions++;
                } else {
                    hashCodes3.add(hashCode);
                }
            }
            System.out.println("Collisions for cyclic has with shift value of 7: " + collisions);

            // And finally with the old java hashing function
            collisions = 0;
            ArrayList<Integer> hashCodes4 = new ArrayList<>();
            FileReader listOfWords4 = new FileReader(currentFilePath + "\\src\\words.txt");
            Scanner s4 = new Scanner(listOfWords4);
            while (s4.hasNext()) {

                String currentWord = s4.next();

                int hashCode = hashOld(currentWord);

                if (hashCodes4.contains(hashCode)) {
                    collisions++;
                } else {
                    hashCodes4.add(hashCode);
                }
            }
            System.out.println("Collisions for old java hash code: " + collisions);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
