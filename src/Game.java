import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private ArrayList<Card> deck;
    private ArrayList<Card> player;
    private ArrayList<Card> cpu;
    private Stat curStat;
    private boolean cpuStat;
    private int hand;

    private static final String ANSI_RESET   = "\u001B[0m";
    private static final String ANSI_RED     = "\u001B[31m";
    private static final String ANSI_GREEN   = "\u001B[32m";
    private static final String ANSI_YELLOW  = "\u001B[33m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_CYAN    = "\u001B[36m";

    public Game(String path) {
        scanner = new Scanner(System.in);
        deck = readFile(path);
        player = new ArrayList<>();
        cpu = new ArrayList<>();
        cpuStat = false;  // Determines turn. Player chooses their stat first.
    }

    public void start() {
        displayMenu();

        int input = 0;
        while (input == 0)
            input = promptHandSize();
        dealCards();

        while (true) {
            System.out.println("----------------------------------------");
            if (!cpuStat) {
                turnPlayer();
            }
            else {
                turnCPU();
            }
            evaluate();

            if (player.size() != 0 && cpu.size() != 0)
                promptContinue();

            if (player.size() == hand * 2) {
                printlnCol("Player wins the game!", ANSI_YELLOW);
                break;
            }

            if (cpu.size() == hand * 2) {
                printlnCol("CPU wins the game!", ANSI_YELLOW);
                break;
            }
        }
        System.out.println("----------------------------------------");
    }

    public void reset() {
        while (player.size() != 0) {
            deck.add(player.remove(0));
        }

        while (cpu.size() != 0) {
            deck.add(cpu.remove(0));
        }
        cpuStat = false;
    }

    private void printlnCol(String text, String colour) {
        StringBuilder sb = new StringBuilder();
        sb.append(colour);
        sb.append(text);
        sb.append(ANSI_RESET);
        System.out.println(sb.toString());
    }

    private void displayMenu() {
        String input;

        printlnCol("Celebrity Dogs", ANSI_CYAN);
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

        if (input.compareTo("2") == 0) {
            try {
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

    private int promptHandSize() {
        String input;
        int num;

        try {
            System.out.print("Enter number of starting cards per player: ");
            input = scanner.next();
            num = Integer.parseInt(input);

            while (num < 4 || num > deck.size() || num % 2 != 0) {
                System.out.println("\nTry again");
                System.out.print("Enter: ");
                input = scanner.nextLine();
                num = Integer.parseInt(input);
            }
        }
        catch (Exception e) {
            return 0;
        }
    
        hand = num;
        return num;
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
        
        System.out.println(String.format("You chose %s\n", curStat.toString()));
        System.out.println("(CPU)");
        System.out.println(cpu.get(0).toString());
    }

    private void turnCPU() {
        System.out.println("(CPU)");
        System.out.println(cpu.get(0).toString());
        System.out.println("(You)");
        System.out.println(player.get(0).toString());

        Random r = new Random();
        curStat = Stat.values()[r.nextInt(4)];

        try {
            System.out.println("CPU is thinking...");
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(String.format("CPU chose %s\n", curStat.toString()));
    }

    private int promptStat() {
        String input;
        int stat = 0;

        System.out.print("Choose a stat E, I, F or D: ");
        input = scanner.nextLine();
        input = input.toUpperCase();

        while (input.compareTo("E") != 0 &&
               input.compareTo("I") != 0 &&
               input.compareTo("F") != 0 &&
               input.compareTo("D") != 0)
        {
            System.out.println("\nTry again");
            System.out.print("Enter: ");
            input = scanner.nextLine();
            input = input.toUpperCase();
        }

        switch (input.charAt(0)) {
            case 'E':
                stat = Stat.EXERCISE.ordinal();
                break;
        
            case 'I':
                stat = Stat.INTELLIGENCE.ordinal();
                break;

            case 'F':
                stat = Stat.FRIENDLINESS.ordinal();
                break;

            case 'D':
                stat = Stat.DROOL.ordinal();
                break;
        }
        return stat;
    }

    private void promptContinue() {
        System.out.print("Press ENTER to continue");
        scanner.nextLine();
    }

    private String winning(int stat) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_GREEN);
        sb.append(stat);
        sb.append(ANSI_RESET);
        return sb.toString();
    }

    private String losing(int stat) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANSI_RED);
        sb.append(stat);
        sb.append(ANSI_RESET);
        return sb.toString();
    }

    private void evaluate() {
        int statPlayer = 0;
        int statCPU = 0;
        boolean cpuWins = false;  // Assume initially CPU to win is false. Player wins if the round is a draw.
        boolean draw = false;

        switch (curStat.ordinal()) {
            case 0:  // EXERCISE
                statPlayer = player.get(0).getStatExercise();
                statCPU = cpu.get(0).getStatExercise();

                if (statCPU > statPlayer)
                    cpuWins = true;           
                break;
        
            case 1:  // INTELLIGENCE
                statPlayer = player.get(0).getStatIntelligence();
                statCPU = cpu.get(0).getStatIntelligence();

                if (statCPU > statPlayer)
                    cpuWins = true;
                break;
            
            case 2:  // FRIENDLINESS
                statPlayer = player.get(0).getStatFriendliness();
                statCPU = cpu.get(0).getStatFriendliness();

                if (statCPU > statPlayer)
                    cpuWins = true;
                break;
            
            case 3:  // DROOL
                statPlayer = player.get(0).getStatDrool();
                statCPU = cpu.get(0).getStatDrool();

                if (statCPU < statPlayer)
                    cpuWins = true;
                break;
        }

        if (statCPU == statPlayer)
            draw = true;

        String colPlayer;
        String colCPU;

        if (cpuWins) {
            colPlayer = losing(statPlayer);
            colCPU = winning(statCPU);
        }
        else if (!cpuWins && draw) {
            colPlayer = winning(statPlayer);
            colCPU = winning(statCPU);
        }
        else {
            colPlayer = winning(statPlayer);
            colCPU = losing(statCPU);
        }

        if (curStat != Stat.DROOL) {
            System.out.println(
                String.format("You: %s vs. CPU: %s (Highest %s wins)", colPlayer, colCPU, curStat.toString()));
        }
        else {
            System.out.println(
                String.format("You: %s vs. CPU: %s (Lowest %s wins)", colPlayer, colCPU, curStat.toString()));
        }

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (cpuWins) {
            printlnCol("You lose this round!", ANSI_RED);
            cpu.add(player.remove(0));
            cpu.add(cpu.remove(0));
            cpuStat = true;
        }
        else {
            if (draw)
                printlnCol("You win this round! (by draw)", ANSI_GREEN);
            else
                printlnCol("You win this round!", ANSI_GREEN);
            
            player.add(cpu.remove(0));
            player.add(player.remove(0));
            cpuStat = false;
        }
        printlnCol(String.format("Cards: Player has %d, CPU has %d", player.size(), cpu.size()), ANSI_MAGENTA);
    }
}
