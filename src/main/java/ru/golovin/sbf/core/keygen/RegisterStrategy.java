package ru.golovin.sbf.core.keygen;

public class RegisterStrategy implements KeyWordStrategy {

    @Override
    public String apply(String keyWord) {
        StringBuilder result = new StringBuilder();
        for (char c : keyWord.toCharArray()) {
            if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
