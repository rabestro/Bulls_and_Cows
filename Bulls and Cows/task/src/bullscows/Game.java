package bullscows;

import java.util.Scanner;

public final class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private final SecretCode secretCode;

    private Game(SecretCode secretCode) {
        this.secretCode = secretCode;
    }

    public static Game create() {
        int codeLength;
        while (true) {
            System.out.println("Please, enter the secret code's length:");
            codeLength = Integer.parseInt(scanner.nextLine());
            if (codeLength > 0 && codeLength <= 36) {
                break;
            }
            System.out.println("Error: incorrect length value");
        }
        int codeSymbols;
        while (true) {
            System.out.println("Input the number of possible symbols in the code:");
            codeSymbols = Integer.parseInt(scanner.nextLine());
            if (codeSymbols >= codeLength && codeSymbols <= 36) {
                break;
            }
            System.out.println("Error: incorrect number of symbols");
        }
        final var secretCode = SecretCode.create(codeLength, codeSymbols);
        System.out.println("The secret is prepared: " + secretCode);
        return new Game(secretCode);
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
