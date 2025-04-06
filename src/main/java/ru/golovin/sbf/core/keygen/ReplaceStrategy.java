package ru.golovin.sbf.core.keygen;

public class ReplaceStrategy implements KeyWordStrategy {

    @Override
    public String apply(String keyWord) {
        // Маппинг символов
        String[][] replacements = {{"i", "!"}, {"o", "0"}, {"s", "$"}};

        for (String[] pair : replacements) {
            keyWord = keyWord.replace(pair[0], pair[1]);
        }
        return keyWord;
    }
}
