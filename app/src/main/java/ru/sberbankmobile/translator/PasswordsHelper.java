package ru.sberbankmobile.translator;

public class PasswordsHelper {

    private final String[] cyrillic;
    private final String[] latin;


    public PasswordsHelper(String[] cyrillic, String[] latin) {
        this.cyrillic = cyrillic;
        this.latin = latin;

        if (cyrillic.length != latin.length) {
            throw new IllegalArgumentException();
        }
    }

    public String convert(CharSequence source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            String s = String.valueOf(c);
            boolean found = false;

            for (int j = 0; j < cyrillic.length; j++) {
                if (cyrillic[j].equals(s.toLowerCase())) {
                    result.append(Character.isLowerCase(c) ? latin[j] : latin[j].toUpperCase());
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.append(c);
            }
        }
        return result.toString();
    }
}
