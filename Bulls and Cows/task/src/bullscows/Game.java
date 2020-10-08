package bullscows;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.Optional.empty;

public final class Game implements Runnable {
    private static final Pattern PATTERN_NUMBER = Pattern.compile("[1-9]|[12][0-9]|3[0-6]");
    private static final Scanner scanner = new Scanner(System.in);
    private final SecretCode secretCode;

    private Game(final SecretCode secretCode) {
        this.secretCode = secretCode;
    }

    public static Optional<Game> create() {
        System.out.println("Please, enter the secret code's length:");
        var input = scanner.nextLine();
        if (!PATTERN_NUMBER.matcher(input).matches()) {
            System.out.println("Error: the secret code's length should be a number from 1 to 36");
            return empty();
        }
        final int codeLength = Integer.parseInt(input);

        System.out.println("Input the number of possible symbols in the code:");
        input = scanner.nextLine();
        if (!PATTERN_NUMBER.matcher(input).matches()) {
            System.out.println("Error: the number of possible symbols in the code should be from 1 to 36");
            return empty();
        }
        final int codeSymbols = Integer.parseInt(input);
        if (codeSymbols < codeLength) {
            System.out.println("Error: it's not possible to generate a code with a length of "
                    + codeLength + " with " + codeSymbols + " unique symbols.");
            return empty();
        }
        final var secretCode = SecretCode.create(codeLength, codeSymbols);
        System.out.println("The secret is prepared: " + secretCode);
        return Optional.of(new Game(secretCode));
    }

    @Override
    public void run() {
        System.out.println("Okay, let's start a game!");
        int turn = 1;
        var isGuessed = false;

        do {
            System.out.println("Turn " + turn++);
            final var grade = secretCode.getGrade(scanner.nextLine());
            grade.ifPresentOrElse(System.out::println, this::printInputMismatch);
            isGuessed = grade.isPresent() && grade.get().isGuessed();
        } while (!isGuessed);

        System.out.println("Congratulations! You guessed the secret code.");
    }

    private void printInputMismatch() {
        System.out.println("Can't grade: the input doesn't match the pattern of the secret code.");
    }
}
