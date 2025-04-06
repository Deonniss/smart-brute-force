package ru.golovin.sbf.core.keygen;

import ru.golovin.sbf.mask.KeyWordOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeyWordProcessor {

    private final Map<KeyWordOption, KeyWordStrategy> strategyMap;

    public KeyWordProcessor(List<KeyWordStrategy> strategies) {
        strategyMap = new HashMap<>();
        strategyMap.put(KeyWordOption.REGISTER, new RegisterStrategy());
        strategyMap.put(KeyWordOption.SHORT, new ShortStrategy());
        strategyMap.put(KeyWordOption.INJECT, new InjectStrategy());
        strategyMap.put(KeyWordOption.INVERSION, new InversionStrategy());
        strategyMap.put(KeyWordOption.REPLACE, new ReplaceStrategy());
    }

    public String process(String keyWord, Set<KeyWordOption> options) {
        String result = keyWord;
        for (KeyWordOption option : options) {
            KeyWordStrategy strategy = strategyMap.get(option);
            if (strategy != null) {
                result = strategy.apply(result);
            }
        }
        return result;
    }
}
