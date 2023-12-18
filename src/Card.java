import java.util.Random;

public class Card {
    private String name;
    private int exercise;
    private int intelligence;
    private int friendliness;
    private int drool;

    /**
     * Creates a fully initialized instance of Card with random stats using a given name.
     * @param s the name of the dog that this object represents.
     */
    public Card(String s) {
        Random r = new Random();

        name = s;
        exercise     = r.nextInt(5 - 1)   + 1;
        intelligence = r.nextInt(100 - 1) + 1;
        friendliness = r.nextInt(10 - 1)  + 1;
        drool        = r.nextInt(10 - 1)  + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ ");
        sb.append(name);
        sb.append(", ");
        sb.append(exercise);
        sb.append(", ");
        sb.append(intelligence);
        sb.append(", ");
        sb.append(friendliness);
        sb.append(", ");
        sb.append(drool);
        sb.append(" }");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public int getStatExercise() {
        return exercise;
    }

    public int getStatIQ() {
        return intelligence;
    }

    public int getStatFriend() {
        return friendliness;
    }

    public int getStatDrool() {
        return drool;
    }
}
