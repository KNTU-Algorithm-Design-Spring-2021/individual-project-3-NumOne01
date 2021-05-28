import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class JamesBond {
    Dictionary dictionary;

    JamesBond() {
        dictionary = Dictionary.getInstance();
    }

    /**
     * an utility function used to decode a string
     * this functions uses backtrack algorithm
     * nodes are substrings of string starting with from argument
     * to end of string
     * nodes which contains invalid word (based on dataset) will be pruned
     *
     * @param codedString coded string
     * @param from        beginning index of string
     * @param words       list of strings consists of possible words
     * @param answers     array of strings consists of possible answers
     */
    private void decodeUtil(String codedString, int from, LinkedList<String> words, LinkedList<String> answers) {
        if (from >= codedString.length()) {
            String decodedString = join(words, " ");
            answers.add(decodedString);
        } else {
            String word;
            for (int i = from; i < codedString.length(); i++) {
                word = codedString.substring(from, i + 1);
                if (isPromising(word)) {
                    words.add(word);
                    decodeUtil(codedString, i + 1, words, answers);
                    words.pop();
                }
            }
        }
    }

    /**
     * promising functions for backtrack algorithm
     * if a word is valid (based on dataset) it is promising otherwise
     * it is not
     *
     * @param word the word to check
     * @return whether this word is promising or not
     */
    private boolean isPromising(String word) {
        return dictionary.isValidWord(word);
    }

    /**
     * decode a strings which words are not separated with white space
     *
     * @param codedString coded string
     * @return array of strings consists of possible answers
     */
    public String[] decode(String codedString) {
        LinkedList<String> answers = new LinkedList<>();
        LinkedList<String> words = new LinkedList<>();
        decodeUtil(codedString, 0, words, answers);

        return listToArray(answers);
    }

    /**
     * joins the strings of a list of strings with specified separator
     *
     * @param words     list of words
     * @param separator separator used to join strings
     * @return joined string
     */
    private String join(LinkedList<String> words, String separator) {
        StringBuilder joinedString = new StringBuilder(separator);

        for (String word : words) {
            joinedString.append(word).append(" ");
        }

        return joinedString.toString().trim();
    }

    /**
     * converts a list of strings to array
     *
     * @param list list of strings
     * @return an array of strings
     */
    private String[] listToArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * counts count of promising children
     *
     * @param codedString coded string
     * @return count of promising children
     */
    private int countPromisingChildren(String codedString) {
        int from = 0, countOfPromisingChildren = 0;
        String word;
        for (int i = from; i < codedString.length(); i++) {
            word = codedString.substring(from, i + 1);
            if (isPromising(word)) {
                countOfPromisingChildren++;
            }
        }
        return countOfPromisingChildren;
    }

    private int countChildren(String codedString) {
        return (int) Math.pow(2, codedString.length());
    }

    /**
     * counts number of calls for algorithm
     *
     * @param codedString coded string
     * @return count of calls
     */
    private int countOfCalls(String codedString) {
        int from = 0, countOfPromisingChildren, countOfNodes = 1, countOfChildren = 0;
        double averageWordLength = dictionary.getTotalWordsLength() / dictionary.getCountOfWords();

        do {
            countOfPromisingChildren = countPromisingChildren(codedString.substring(from));
            from = (int) (Math.random() * averageWordLength + from);
            if (from > codedString.length()) break;
            countOfChildren = countChildren(codedString.substring(from));
            countOfNodes += countOfPromisingChildren * countOfChildren;

        } while (countOfPromisingChildren != 0 && from < codedString.length());
        return countOfNodes;
    }

    /**
     * estimates time complexity of algorithm
     * based on monte carlo algorithm
     *
     * @return number of nodes which algorithm checks
     */
    public double estimateTimeComplexity(String codedString) {
        int size = 200;
        double estimatedTime = 0;
        for (int i = 0; i < size; i++) {
            estimatedTime += countOfCalls(codedString);
        }

        return estimatedTime / size;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        JamesBond jamesBond = new JamesBond();
        String codedString = in.next();
        String[] decodedStrings = jamesBond.decode(codedString);
        for (String decodedString : decodedStrings) {
            System.out.println(decodedString);
        }
        System.out.println("Time Complexity : " + jamesBond.estimateTimeComplexity(codedString));
    }
}
