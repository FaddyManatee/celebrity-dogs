public class Main {
    public static void main(String[] args) {
        Game g = new Game("dogs.txt");
        while (true) {
            if (!g.start())
                break;
            g.reset();
        }
    }
}
