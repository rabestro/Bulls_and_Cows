package bullscows;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Game implements Runnable {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        System.out.println("Please, enter the secret code's length:");
        final var length = Integer.parseInt(scanner.next("[1-9]|10"));
        final var secretCode = SecretCode.getCode(length);
        final var codePattern = Pattern.compile("\\d{" + secretCode.length() + '}');
        System.out.println("Okay, let's start a game!");
        int turn = 0;
        while (true) {
            turn++;
            System.out.println("Turn " + turn);
            try {
                SecretCode.Grade grade = secretCode.getGrade(scanner.next(codePattern));
                System.out.println("Grade: " + grade);
                if (grade.isGuessed()) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: incorrect length of guess");
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

}
