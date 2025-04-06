package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.Mask;

import java.util.List;
import java.util.Set;

public interface BruteforceGeneratorFacade {

    List<String> generate(Set<List<Mask>> masks);
}
