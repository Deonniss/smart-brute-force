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
     * Внедрение масок {@link MaskType#SPECIAL} между символами масок {@link MaskType#KEY_WORD}
     */
    INJECT,

    /**
     * Инверсия {@link MaskType#KEY_WORD}
     */
    INVERSION,

    /**
     * Подмена символов из {@link MaskType#KEY_WORD} подходящими символами (i=!, o = 0, s = $)
     */
    REPLACE
}
