package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.Mask;

import java.io.IOException;
import java.util.List;

public interface BruteforceGenerator {

    /**
     * @param maskTypes маски паролей, состоящие из блоков
     * @param target искомая строка
     * @return boolean - найден ли пароль
     */
    boolean generate(List<Mask> masks, String target) throws IOException;
}
