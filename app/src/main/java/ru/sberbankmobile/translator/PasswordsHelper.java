package ru.sberbankmobile.translator;

import java.util.Arrays;
import java.util.List;

public class PasswordsHelper {

    private final String[] cyrillic;
    private final String[] latin;
    private static final List<Byte> uppercase = getListOfBytes("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private static final List<Byte> numerals = getListOfBytes("0123456789");
    private static final List<Byte> specialCharacters = getListOfBytes("!@#$%&*()_+-=[]/?><");


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

    public static String quality(String password) {
        int length = password.length();
        boolean uppercaseIsPresent = false;
        boolean numeralsArePresent = false;
        boolean specialCharactersArePresent = false;
        int qualityLevel = length > 7 ? 1 : 0;

        for (Byte b : password.getBytes()) {
            if (!uppercaseIsPresent && uppercase.contains(b)) {
                uppercaseIsPresent = true;
                qualityLevel++;
            }
            if (!numeralsArePresent && numerals.contains(b)) {
                numeralsArePresent = true;
                qualityLevel++;
            }
            if (!specialCharactersArePresent && specialCharacters.contains(b)) {
                specialCharactersArePresent = true;
                qualityLevel++;
            }
        }
        switch (qualityLevel) {
            case 1:
                return "Слабый пароль";
            case 2:
                return "Нормальный пароль";
            case 3:
                return "Хороший пароль";
            case 4:
                return "Отличный пароль";
            default:
                return "Плохой пароль";
        }
    }

    private static List<Byte> getListOfBytes(String characters) {
        byte[] bytesPrimitive = characters.getBytes();
        Byte[] bytes = new Byte[bytesPrimitive.length];
        for (int i = 0; i < bytesPrimitive.length; i++) {
            bytes[i] = bytesPrimitive[i];
        }
        return Arrays.asList(bytes);
    }
}
