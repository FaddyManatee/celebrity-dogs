public class Main {
    public static void main(String[] args) {
        Game.clearScreen();
        Game.displayMenu();

        Game g = new Game();
        g.start();
    }
}
