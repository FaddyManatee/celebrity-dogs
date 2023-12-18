public class Dog {
    private String name;
    private int exercise;
    private int intelligence;
    private int friendliness;
    private int drool;

    /**
     * Creates a fully initialized instance of Dog from a comma seperated list of values.
     * @param s of the form: name,exercise,intelligence,friendliness,drool
     */
    public Dog(String s) {
        String[] values = s.split(",");
        
        name         = values[0];
        exercise     = Integer.parseInt(values[1]);
        intelligence = Integer.parseInt(values[2]);
        friendliness = Integer.parseInt(values[3]);
        drool        = Integer.parseInt(values[4]);
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
