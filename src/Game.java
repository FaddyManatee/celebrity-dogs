import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Card> deck;
    private ArrayList<Card> player;
    private ArrayList<Card> cpu;
    private Stat curStat;
    private boolean cpuStat;
    private int hand;

    public Game(String path) {
        deck = readFile(path);
        player = new ArrayList<>();
        cpu = new ArrayList<>();
        cpuStat = false;  // Player chooses their stat first.
    }

    public void start() {
        clearScreen();
        displayMenu();

        Integer i = null;
        //while (i == null)
            i = promptHandSize();
        dealCards();

        while (true) {
            System.out.println("-------------------------------------");
            if (!cpuStat) {
                turnPlayer();
                System.out.println("(CPU)");
                System.out.println(cpu.get(0).toString());
            }
            else {
                turnCPU();
            }
            evaluate();
            
            // REMOVE
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // REMOVE

            if (player.size() == hand * 2) {
                System.out.println("Player wins the game!");
                break;
            }

            if (cpu.size() == hand * 2) {
                System.out.println("CPU wins the game!");
                break;
            }
        }
    }

    private void clearScreen() {
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
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
        clearScreen();

        if (input.compareTo("2") == 0) {
            try {
                System.out.println("Closing the game...");
                Thread.sleep(1000);
                clearScreen();
                System.exit(0);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            System.out.println(System.in.available());
        }
        catch (Exception e) {}
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
        // Scanner scanner = new Scanner(System.in);
        // int input;

        // try {
        //     System.out.print("Enter number of starting cards per player: ");
        //     input = Integer.parseInt(scanner.nextLine());

        //     while (input < 4 || input > deck.size() || input % 2 != 0) {
        //         System.out.println("\nTry again");
        //         System.out.print("Enter: ");
        //         input = Integer.parseInt(scanner.nextLine());
        //     }
        // }
        // catch (Exception e) {
        //     return null;
        // }
        // finally {
        //     scanner.close();
        //     clearScreen();
        // }
    
        hand = 4;
        return 4;
    }

    private void dealCards() {
        Collections.shuffle(deck);

        for (int x = 0; x < hand; ++x) {
            player.add(deck.remove(0));
            cpu.add(deck.remove(0));
        }
    }

    private void turnPlayer() {
        System.out.println("(You)");
        System.out.println(player.get(0).toString());

        curStat = Stat.values()[promptStat()];
        clearScreen();
    }

    private void turnCPU() {
        System.out.println("(CPU)");
        System.out.println(cpu.get(0).toString());
        System.out.println("(You)");
        System.out.println(player.get(0).toString());

        Random r = new Random();
        curStat = Stat.values()[r.nextInt(4)];
        clearScreen();
    }

    private int promptStat() {
        // TODO
        return 0;
    }

    private void evaluate() {
        int stat1 = 0;
        int stat2 = 0;
        boolean cpuWins = false;  // Assume initially CPU to win is false. Player wins if the round is a draw.
        boolean draw = false;

        if (cpuStat)
            System.out.println(String.format("CPU chose %s", curStat.toString()));
        else
            System.out.println(String.format("You chose %s", curStat.toString()));

        if (curStat == Stat.EXERCISE) {
            stat1 = player.get(0).getStatExercise();
            stat2 = cpu.get(0).getStatExercise();
            System.out.println(String.format("You: %s vs. CPU: %s (Highest EXERCISE wins)", stat1, stat2));

            if (stat2 > stat1)
                cpuWins = true;
        }
        else if (curStat == Stat.INTELLIGENCE) {
            stat1 = player.get(0).getStatIntelligence();
            stat2 = cpu.get(0).getStatIntelligence();
            System.out.println(String.format("You: %s vs. CPU: %s (Highest INTELLIGENCE wins)", stat1, stat2));

            if (stat2 > stat1)
                cpuWins = true;
        }
        else if (curStat == Stat.FRIENDLINESS) {
            stat1 = player.get(0).getStatFriendliness();
            stat2 = cpu.get(0).getStatFriendliness();
            System.out.println(String.format("You: %s vs. CPU: %s (Highest FRIENDLINESS wins)", stat1, stat2));

            if (stat2 > stat1)
                cpuWins = true;
        }
        else if (curStat == Stat.DROOL) {
            stat1 = player.get(0).getStatDrool();
            stat2 = cpu.get(0).getStatDrool();
            System.out.println(String.format("You: %s vs. CPU: %s (Lowest DROOL wins)", stat1, stat2));

            if (stat2 < stat1)
                cpuWins = true;
        }

        if (stat2 == stat1)
            draw = true;

        if (cpuWins) {
            System.out.println("You lose this round!");
            cpu.add(cpu.remove(0));
            cpu.add(player.remove(0));
            cpuStat = true;
        }
        else {
            if (draw)
                System.out.println("You win this round! (by draw)");
            else
                System.out.println("You win this round!");
            player.add(player.remove(0));
            player.add(cpu.remove(0));
            cpuStat = false;
        }
    }
}
