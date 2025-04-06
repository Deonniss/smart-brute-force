package ru.golovin.sbf.core.keygen;

public class InversionStrategy implements KeyWordStrategy {

    @Override
    public String apply(String keyWord) {
        return new StringBuilder(keyWord).reverse().toString();
    }
}
