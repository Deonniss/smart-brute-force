package ru.golovin.sbf.core;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.mask.MaskType;
import ru.golovin.sbf.mask.Option;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class SmartBruteforceGeneratorFacade implements BruteforceGeneratorFacade {

    private final BruteforceGenerator generator;

    @Override
    public boolean generate(Set<List<MaskType>> masks, Option option, String target) throws IOException {
        for (List<MaskType> mask : masks) {
            if (generator.generate(mask, option, target)) {
                return true;
            }
        }
        return false;
    }
}
