import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Game {
    private ArrayList<Card> deck;
    private ArrayList<Card> player;
    private ArrayList<Card> cpu;
    private int hand;

    public Game(String path) {
        deck = readFile(path);
    }

    public void start() {
        clearScreen();
        displayMenu();

        Integer i = null;
        while (i == null)
            i = promptHandSize();
        dealCards();

        while (true) {
            if (player.size() == 0) {
                // Player won the game.
                break;
            }

            if (cpu.size() == 0) {
                // Computer won the game.
                break;
            }
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Celebrity Dogs");
        System.out.println("1) Play Game");
        System.out.println("2) Quit\n");
        System.out.print("Enter choice: ");
        input = scanner.nextLine();

        while (input.compareTo("1") != 0 &&
               input.compareTo("2") != 0)
        {
            System.out.println("\nTry again");
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

    private ArrayList<Card> readFile(String path) {
        ArrayList<Card> result = new ArrayList<>();

        // Read data from dogs.txt.
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while (line != null) {
                result.add(new Card(line));
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(String.format("Error: File '%s' not found", path));
        }
        catch (IOException e) {
            System.err.println(String.format("Error: I/O error occurred whilst reading file '%s'", path));
        }
        return result;
    }

    private Integer promptHandSize() {
        Scanner scanner = new Scanner(System.in);
        int input;

        try {
            System.out.print("Enter number of starting cards per player: ");
            input = Integer.parseInt(scanner.nextLine());

            while (input < 4 || input > deck.size() || input % 2 != 0) {
                System.out.println("\nTry again");
                System.out.print("Enter: ");
                input = Integer.parseInt(scanner.nextLine());
            }
        }
        catch (Exception e) {
            return null;
        }
        finally {
            scanner.close();
        }
        clearScreen();
    
        hand = input;
        return input;
    }

    private void dealCards() {
        Collections.shuffle(deck);

        for (int x = 0; x < hand; ++x) {
            player.add(deck.remove(0));
            cpu.add(deck.remove(0));
        }
    }
}
