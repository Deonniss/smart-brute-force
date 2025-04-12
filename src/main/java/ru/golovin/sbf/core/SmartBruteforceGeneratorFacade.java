package ru.golovin.sbf.core;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.mask.Mask;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class SmartBruteforceGeneratorFacade implements BruteforceGeneratorFacade {

    private final BruteforceGenerator generator;

    @Override
    public boolean generate(Set<List<Mask>> masks, String target) throws IOException {
        for (List<Mask> mask : masks) {
            if (generator.generate(mask, target)) {
                return true;
            }
        }
        return false;
    }
}
