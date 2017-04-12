package epam.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    private static final Pattern emailPattern = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                    "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                    "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25" +
                    "[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    private static final Pattern lettersAndDash = Pattern.compile("[a-zA-ZА-ЯёЁа-я]+-*[a-zA-ZА-ЯёЁа-я]+");

    private static final Pattern passwordPattern = Pattern.compile("[a-zA-ZА-ЯёЁа-я0-9_!-?]+");

    private static Matcher matcher;


    public static boolean validateEmail(final String string) {
        matcher = emailPattern.matcher(string);
        return matcher.matches();
    }

    public static boolean validatePassword(final String string) {
        matcher = passwordPattern.matcher(string);
        return matcher.matches();
    }

    public static boolean validateForLettersAndDash(final String string) {
        matcher = lettersAndDash.matcher(string);
        return matcher.matches();
    }

}
