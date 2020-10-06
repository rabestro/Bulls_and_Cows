package bullscows;

import java.util.Scanner;

public class Game implements Runnable {
    private final String secreteCode = "9305";

    @Override
    public void run() {
        final var guess = new Scanner(System.in).next("\\d{4}");
        System.out.println(grade(guess) + " The secret code is " + secreteCode + ".");
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
}
