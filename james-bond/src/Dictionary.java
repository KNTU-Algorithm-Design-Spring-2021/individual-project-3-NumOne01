import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Dictionary {
    private final String filePath = "src/The_Oxford_3000.txt";
    private HashMap<String, Integer> words = new HashMap<>();
    private static Dictionary dictionary;
    private double totalWordsLength = 0;

    private Dictionary() {
        readData();
    }

    public static Dictionary getInstance() {
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    private void readData() {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                words.put(data, 1);
                totalWordsLength += data.length();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public double getCountOfWords() {
        return words.size();
    }

    public double getTotalWordsLength() {
        return totalWordsLength;
    }

    public boolean isValidWord(String word) {
        return words.get(word.toLowerCase(Locale.ROOT)) != null;
    }
}
