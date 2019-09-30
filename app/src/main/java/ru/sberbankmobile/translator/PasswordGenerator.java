package ru.sberbankmobile.translator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERALS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()_+-=[]/?><";

    public static String generate(boolean useSpecialCharacters, boolean useUppercase, boolean useNumerals, int length) {
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());
        List<String> charCategories = new ArrayList<>(4);
        charCategories.add(LOWERCASE);

        if (useSpecialCharacters) {
            charCategories.add(SPECIAL_CHARACTERS);
        }
        if (useUppercase) {
            charCategories.add(UPPERCASE);
        }
        if (useNumerals) {
            charCategories.add(NUMERALS);
        }

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }
}
