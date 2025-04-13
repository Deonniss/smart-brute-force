package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.MaskType;
import ru.golovin.sbf.mask.Option;

import java.io.IOException;
import java.util.List;

public interface BruteforceGenerator {

    /**
     *
     * @param masks маски паролей, состоящие из блоков
     * @param option настройки для типов блоков масок
     * @param target искомая строка
     * @return boolean - найден ли пароль
     * @throws IOException при недоступности словарей
     */
    boolean generate(List<MaskType> masks, Option option, String target) throws IOException;
}
