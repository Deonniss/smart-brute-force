package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.MaskType;
import ru.golovin.sbf.mask.Option;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BruteforceGeneratorFacade {

    boolean generate(Set<List<MaskType>> masks, Option option, String target) throws IOException;
}
