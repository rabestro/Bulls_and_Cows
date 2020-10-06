package bullscows;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game implements Runnable {
    private final String secreteCode = "9305";

    @Override
    public void run() {
        try {
            final var length = Integer.parseInt(new Scanner(System.in).next("[1-9]|10"));
            System.out.println("The random secret number is " + getRandomNumber(length) + ".");
        } catch (InputMismatchException e) {
            System.out.println("Error: Input mismatch.");
        }
    }

    private String grade(String guess) {
        int cows = 0;
        int bulls = 0;

        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secreteCode.charAt(i)) {
                bulls++;
            } else if (secreteCode.indexOf(guess.charAt(i)) > -1) {
                cows++;
            }
        }
        if (bulls + cows == 0) {
            return "None.";
        }
        String result;
        if (bulls > 0) {
            result = bulls + " bull(s)";
            if (cows > 0) {
                result += " and " + cows + " cow(s)";
            }
        } else {
            result = cows + " cow(s)";
        }
        return result + ".";
    }

    private String getRandomNumber(int length) {
        long pseudoRandomNumber = System.nanoTime();
        final var result = new StringBuffer();
        while (result.length() < length) {
            if (pseudoRandomNumber == 0) {
                pseudoRandomNumber = System.nanoTime();
            }
            var digit = pseudoRandomNumber % 10;
            pseudoRandomNumber /= 10;
            if (result.length() == 0 && digit == 0) {
                continue;
            }
            if (result.indexOf(String.valueOf(digit)) == -1) {
                result.append(String.valueOf(digit));
            }
        }
        return result.toString();
    }
}
