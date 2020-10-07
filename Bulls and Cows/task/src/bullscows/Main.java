package bullscows;

public final class Main {
    public static void main(String[] args) {
        Game.create().ifPresent(Game::run);
    }
}
