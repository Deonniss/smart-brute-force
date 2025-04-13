package ru.golovin.sbf.core;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.core.gpu.aparapi.GpuAparapiBruteforceKernelExecutor;
import ru.golovin.sbf.core.gpu.aparapi.util.GpuAparapiBruteforceParamContainerUtilCreator;
import ru.golovin.sbf.mask.MaskType;
import ru.golovin.sbf.mask.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SmartBruteforceGenerator implements BruteforceGenerator {

    private final GpuAparapiBruteforceKernelExecutor executor;

    @Override
    public boolean generate(List<MaskType> masks, Option option, String target) throws IOException {
        List<String> filePaths = new ArrayList<>();
        for (MaskType mask : masks) {
            switch (mask) {
                case SPECIAL -> filePaths.add(option.getSpecialBlockSize().getPath());
                case DICTIONARY -> filePaths.add("dictionary/all.lst");
            }
        }
        return executor.start(GpuAparapiBruteforceParamContainerUtilCreator.getParamContainer(filePaths, target));
    }
}
