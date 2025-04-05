package ru.golovin.sbf;

import lombok.Data;

@Data
public class Property {

    private static Property instance;

    private int defaultMaxMaskAmount = 3;

    private Property() {}

    public static Property getInstance() {
        if (instance == null) {
            instance = new Property();
        }
        return instance;
    }

    public static void init(int defaultMaxMaskAmount) {
        if (instance == null) {
            instance = new Property();
            instance.setDefaultMaxMaskAmount(defaultMaxMaskAmount);
        } else {
            throw new IllegalStateException("Property is already initialized");
        }
    }
}

