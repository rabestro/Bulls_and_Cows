package bullscows;

import java.util.Random;

import static java.lang.Integer.signum;
import static java.text.MessageFormat.format;

public final class SecretCode {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final String secretCode;
    private final int codeSymbols;

    private SecretCode(String secretCode, int codeSymbols) {
        this.secretCode = secretCode;
        this.codeSymbols = codeSymbols;
    }

    public static SecretCode create(final int codeLength, final int codeSymbols) {
        if (codeLength < 1 || codeSymbols > SYMBOLS.length() || codeLength > codeSymbols) {
            throw new IllegalArgumentException("Illegal code length and code symbols values");
        }
        final var result = new StringBuilder();
        final var random = new Random();
        while (result.length() < codeLength) {
            final var symbol = SYMBOLS.charAt(random.nextInt(codeSymbols));
            if (result.indexOf(String.valueOf(symbol)) == -1) {
                result.append(symbol);
            }
        }
        return new SecretCode(result.toString(), codeSymbols);
    }

    @Override
    public String toString() {
        return "*".repeat(secretCode.length())
                + format(" ({0, choice, 1<0-{1}|11#0-9, a|11<0-9, a-{1}})",
                codeSymbols, SYMBOLS.charAt(codeSymbols - 1));
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
