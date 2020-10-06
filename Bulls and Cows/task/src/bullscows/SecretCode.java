package bullscows;

public class SecretCode {
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
        var pseudoRandomNumber = System.nanoTime();
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
                result.append(digit);
            }
        }
        return new SecretCode(result.toString());
    }

    public Grade getGrade(String guess) {
        return new Grade(guess);
    }

    public int length() {
        return secretCode.length();
    }

    public class Grade {
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
            return "None.";
        }
    }

}
