import java.util.Random;

enum Stat {
    EXERCISE,
    INTELLIGENCE,
    FRIENDLINESS,
    DROOL
}

public class Card {
    private String name;
    private int exercise;
    private int intelligence;
    private int friendliness;
    private int drool;

    /**
     * Creates a fully initialized instance of Card with random stats using a given name.
     * @param name the name of the dog that this object represents.
     */
    public Card(String name) {
        Random r = new Random();

        this.name    = name;
        exercise     = r.nextInt(5 - 1)   + 1;
        intelligence = r.nextInt(100 - 1) + 1;
        friendliness = r.nextInt(10 - 1)  + 1;
        drool        = r.nextInt(10 - 1)  + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name);
        sb.append("\nE: ");
        sb.append(exercise);
        sb.append("\nI: ");
        sb.append(intelligence);
        sb.append("\nF: ");
        sb.append(friendliness);
        sb.append("\nD: ");
        sb.append(drool);
        sb.append("\n");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public int getStatExercise() {
        return exercise;
    }

    public int getStatIntelligence() {
        return intelligence;
    }

    public int getStatFriendliness() {
        return friendliness;
    }

    public int getStatDrool() {
        return drool;
    }
}
