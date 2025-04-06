package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.Mask;

import java.util.List;

public interface BruteforceGenerator {

    List<String> generate(List<Mask> masks);
}
