package ru.golovin.sbf.core.keygen;

public class InjectStrategy implements KeyWordStrategy {

    @Override
    public String apply(String keyWord) {
        return keyWord;
    }
}
