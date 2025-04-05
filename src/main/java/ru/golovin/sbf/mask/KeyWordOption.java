package ru.golovin.sbf.mask;

public enum KeyWordOption {

    /**
     * Смена регистра (для каждого символа отдельно) итеративно
     */
    REGISTER,

    /**
     * Обрезки с плавающим окном
     */
    SHORT,

    /**
     * Внедрение масок {@link Mask#SPECIAL} между символами масок {@link Mask#KEY_WORD}
     */
    INJECT,

    /**
     * Инверсия {@link Mask#KEY_WORD}
     */
    INVERSION,

    /**
     * Подмена символов из {@link Mask#KEY_WORD} подходящими символами (i=!, o = 0, s = $)
     */
    REPLACE
}
