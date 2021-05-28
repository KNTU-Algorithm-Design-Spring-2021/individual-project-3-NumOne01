import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Dictionary {
    private String filePath;
    private HashMap<String, Integer> words;

    Dictionary(String filPath) {
        this.filePath = filPath;
        words = new HashMap<>();
        readData();
    }

    private void readData() {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                words.put(data, 1);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean isValidWord(String word) {

        return words.get(word.toLowerCase(Locale.ROOT)) != null;
    }
}
