package bullscows;

import java.util.Scanner;

public final class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private final SecretCode secretCode;

    private Game(SecretCode secretCode) {
        this.secretCode = secretCode;
    }

    public static Game getGame() {
        while (true) {
            System.out.println("Please, enter the secret code's length:");
            final var length = Integer.parseInt(scanner.nextLine());
            if (length > 0 && length <= 10) {
                return new Game(SecretCode.getCode(length));
            }
            System.out.println("Error: incorrect length value");
        }
    }

    @Override
    public void run() {
        System.out.println("Okay, let's start a game!");
        int turn = 0;
        while (true) {
            turn++;
            System.out.println("Turn " + turn);
            final var grade = secretCode.getGrade(scanner.nextLine());
            System.out.println("Grade: " + grade);
            if (grade.isGuessed()) {
                break;
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

}
