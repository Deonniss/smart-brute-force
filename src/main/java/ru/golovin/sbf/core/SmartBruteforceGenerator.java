package ru.golovin.sbf.core;

import ru.golovin.sbf.mask.Mask;

import java.util.ArrayList;
import java.util.List;

public class SmartBruteforceGenerator implements BruteforceGenerator {

    @Override
    public List<String> generate(List<Mask> masks) {
        List<String> result = new ArrayList<>();

        if (masks.contains(Mask.KEY_WORD)) {
            //generation KEY_WORD dictionary
        }

        for (Mask mask : masks) {



        }
        return result;
    }
}
