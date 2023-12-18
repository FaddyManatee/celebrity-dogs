import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) { 
        ArrayList<Dog> dogs = readFile();

        for (Dog d : dogs) {
            System.out.println(d.toString());
        }
    }

    public static ArrayList<Dog> readFile() {
        ArrayList<Dog> result = new ArrayList<>();

        // Read data from dogs.txt.
        try (BufferedReader br = new BufferedReader(new FileReader("../dogs.txt"))) {
            String line = br.readLine();

            while (line != null) {
                result.add(new Dog(line));
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Error File 'dogs.txt' not found");
        }
        catch (IOException e) {
            System.err.println("I/O error occurred whilst reading 'dogs.txt'");
        }
        return result;
    }
}
