import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Card> deck;
    private ArrayList<Card> cpu;
    private ArrayList<Card> player;
    private int size;

    public Game() {
        deck = readFile();
        askHandSize();
    }

    public void start() {
        
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Celebrity Dogs");
        System.out.println("1) Play Game");
        System.out.println("2) Quit\n");
        System.out.print("Enter: ");
        input = scanner.nextLine();

        while (input.compareTo("1") != 0 &&
               input.compareTo("2") != 0)
        {
            System.out.println("Try again");
            System.out.print("Enter: ");
            input = scanner.nextLine();
        }
        scanner.close();

        if (input.compareTo("2") == 0) {
            try {
                clearScreen();
                System.out.println("Closing the game...");
                Thread.sleep(1000);
                System.exit(0);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private ArrayList<Card> readFile() {
        ArrayList<Card> result = new ArrayList<>();

        // Read data from dogs.txt.
        try (BufferedReader br = new BufferedReader(new FileReader("dogs.txt"))) {
            String line = br.readLine();

            while (line != null) {
                result.add(new Card(line));
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

    private void askHandSize() {
        clearScreen();
        // TODO: hand size less than 4, or greater than deck size, or odd <-> reject and re-prompt.
        dealCards();
    }

    private void dealCards() {
        //  TODO: shuffle cards and
    }
}
