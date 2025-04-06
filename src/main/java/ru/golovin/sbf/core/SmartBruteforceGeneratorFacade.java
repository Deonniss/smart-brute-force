package ru.golovin.sbf.core;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.mask.Mask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class SmartBruteforceGeneratorFacade implements BruteforceGeneratorFacade {

    private final BruteforceGenerator generator;

    @Override
    public List<String> generate(Set<List<Mask>> masks) {
        List<String> result = new ArrayList<>();
        for (List<Mask> mask : masks) {
            result.addAll(generator.generate(mask));
        }
        return result;
    }
}
