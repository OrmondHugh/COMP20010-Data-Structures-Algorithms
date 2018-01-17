import java.util.*;
import java.io.FileReader;
import java.io.File;

public class WordCount {

    // Class storing a word and its frequency in the passage
    // This will be used to make finding the 10 most frequent words more simple
    public static class WordTuple {

        private String word;
        private int freq;

        public WordTuple(String word, int freq) {
            this.word = word;
            this.freq = freq;
        }

        public WordTuple(WordTuple w) {
            this.word = w.getWord();
            this.freq = w.getFreq();
        }

        public int getFreq() {
            return freq;
        }

        public String getWord() {
            return word;
        }

    }

    public static void mainn(String[] args) {

        try {
            // Set up the scanner reading the words from the text
            String currentFilePath = new File("").getAbsolutePath();
            FileReader sampleText = new FileReader(currentFilePath + "\\src\\sample_text.txt");
            Scanner s = new Scanner(sampleText);

            // Define the hashmap we will be using to store the frequencies of the words
            HashMap wordMap = new HashMap(250);

            // Loop through the whole text, adding each word to the hash map
            while (s.hasNext()) {
                String str = s.next();

                // If the word contains punctuation, it should be stripped
                if (
                    str.substring(str.length()-1).equals(".") ||
                    str.substring(str.length()-1).equals(",") ||
                    str.substring(str.length()-1).equals(";") ||
                    str.substring(str.length()-1).equals("?") ||
                    str.substring(str.length()-1).equals(":")
                   ) {
                    str = str.substring(0,str.length()-1);
                }


                // If the hash map doesn't contain the word, add it
                // If it does, increment its value
                if (!(wordMap.containsKey(str))) {
                    wordMap.put(str, 1);
                } else {
                    int currFreq = (int)wordMap.get(str);

                    currFreq += 1;

                    wordMap.put(str, currFreq);

                }
            }

            ArrayList<WordTuple> wordTuples = new ArrayList<>();

            // Iterate through each key and create a WordsTuple object for each word
            Set<String> wordSet = wordMap.keySet();
            for(String w : wordSet) {

                // Make a tuple with each word and its frequency
                WordTuple tmpTuple = new WordTuple(w, (int)wordMap.get(w));

                wordTuples.add(tmpTuple);
            }

            // Simple bubble sort to sort the list to find the 10 most used words
            for (int i = wordTuples.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {

                    if(wordTuples.get(j).getFreq() < wordTuples.get(j+1).getFreq()) {
                        Collections.swap(wordTuples, j, j+1);
                    }
                }
            }

            // Finally print out the 10 most frequent words in order
            for (int i = 1; i < 11; i++) {
                System.out.println(i + ": " + "\"" + wordTuples.get(i-1).getWord() + "\" Frequency: " + wordTuples.get(i-1).getFreq());
            }

        } catch (Exception e){
            System.out.println(e.getMessage());

            System.exit(1);
        }

        System.exit(0);
    }
}
