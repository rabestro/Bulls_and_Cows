package bullscows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.Integer.signum;
import static java.text.MessageFormat.format;

public final class SecretCode {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final String secretCode;
    private final int numberOfSymbols;
    private final Pattern codePattern;

    private SecretCode(final String secretCode, final int numberOfSymbols) {
        this.secretCode = secretCode;
        this.numberOfSymbols = numberOfSymbols;
        codePattern = Pattern.compile(format("[{0, choice, 1#0|1<0-{1}|11#0-9a|11<0-9a-{1}}]'{'{2}'}'",
                numberOfSymbols, SYMBOLS.charAt(numberOfSymbols - 1), secretCode.length()));
    }

    public static SecretCode create(final int codeLength, final int codeSymbols) {
        List<String> symbols = Arrays.asList(SYMBOLS.substring(0, codeSymbols).split(""));
        Collections.shuffle(symbols);
        var code = String.join("", symbols).substring(0, codeLength);
        return new SecretCode(code, codeSymbols);
    }

    @Override
    public String toString() {
        return "*".repeat(secretCode.length())
                + format(" ({0, choice, 1<0-{1}|11#0-9, a|11<0-9, a-{1}})",
                numberOfSymbols, SYMBOLS.charAt(numberOfSymbols - 1));
    }

    public Optional<Grade> getGrade(final String guess) {
        if (!codePattern.matcher(guess).matches()) {
            return Optional.empty();
        }
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (secretCode.indexOf(guess.charAt(i)) > -1) {
                cows++;
            }
        }
        return Optional.of(new Grade(bulls, cows));
    }

    public final class Grade {
        private final int bulls;
        private final int cows;

        private Grade(final int bulls, final int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }

        public boolean isGuessed() {
            return bulls == secretCode.length();
        }

        @Override
        public String toString() {
            return "Grade: "
                    + format("{0, choice, 0#|1#1 bull|1< {0,number,integer} bulls}", bulls)
                    + format("{0, choice, 0#None|1#|2# and }", signum(bulls) + signum(cows))
                    + format("{0, choice, 0#|1#1 cow|1< {0,number,integer} cows}", cows);
        }
    }
}
