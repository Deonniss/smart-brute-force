package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.Mask;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BruteforceGeneratorFacade {

    boolean generate(Set<List<Mask>> masks, String target) throws IOException;
}
