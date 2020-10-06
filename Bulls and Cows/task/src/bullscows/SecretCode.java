package bullscows;

import java.util.Random;

import static java.lang.Integer.signum;
import static java.text.MessageFormat.format;

public final class SecretCode {
    private final String secretCode;

    private SecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public static SecretCode getCode(int length) {
        if (length < 1 || length > 10) {
            throw new IllegalArgumentException(
                    "The length of security code should be from 1 to 10");
        }
        final var result = new StringBuffer();
        final var random = new Random();
        while (result.length() < length) {
            final var digit = random.nextInt(10);
            if (result.length() == 0 && digit == 0) {
                continue;
            }
            if (result.indexOf(String.valueOf(digit)) == -1) {
                result.append(digit);
            }
        }
        return new SecretCode(result.toString());
    }

    public Grade getGrade(String guess) {
        if (guess.length() != secretCode.length()) {
            throw new IllegalArgumentException("The lengths of guess and secret code must be equal");
        }
        return new Grade(guess);
    }

    public int length() {
        return secretCode.length();
    }

    public final class Grade {
        private final int bulls;
        private final int cows;

        private Grade(String guess) {
            int cows = 0;
            int bulls = 0;
            for (int i = 0; i < secretCode.length(); i++) {
                if (guess.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                } else if (secretCode.indexOf(guess.charAt(i)) > -1) {
                    cows++;
                }
            }
            this.bulls = bulls;
            this.cows = cows;
        }

        public boolean isGuessed() {
            return bulls == secretCode.length();
        }

        @Override
        public String toString() {
            return format("{0, choice, 0#|1#1 bull|1< {0,number,integer} bulls}", bulls)
                    + format("{0, choice, 0#None|1#|2# and }", signum(bulls) + signum(cows))
                    + format("{0, choice, 0#|1#1 cow|1< {0,number,integer} cows}", cows);
        }
    }
}
