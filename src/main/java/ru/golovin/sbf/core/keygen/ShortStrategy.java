package ru.golovin.sbf.core.keygen;

public class ShortStrategy implements KeyWordStrategy {

    @Override
    public String apply(String keyWord) {
        // Пример обрезки: удаляем последний символ
        return keyWord.length() > 1 ? keyWord.substring(0, keyWord.length() - 1) : keyWord;
    }
}
