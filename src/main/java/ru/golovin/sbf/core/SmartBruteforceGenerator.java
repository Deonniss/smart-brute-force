package ru.golovin.sbf.core;

import lombok.RequiredArgsConstructor;
import ru.golovin.sbf.core.gpu.aparapi.GpuAparapiBruteforceKernelExecutor;
import ru.golovin.sbf.core.gpu.aparapi.util.GpuAparapiBruteforceParamContainerUtilCreator;
import ru.golovin.sbf.mask.Mask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SmartBruteforceGenerator implements BruteforceGenerator {

    private final GpuAparapiBruteforceKernelExecutor executor;

    @Override
    public boolean generate(List<Mask> masks, String target) throws IOException {
        List<String> filePaths = new ArrayList<>();
        for (Mask mask : masks) {
            switch (mask.getMaskType()) {
                case SPECIAL -> filePaths.add(mask.getSpecialBlockSize().getPath());
                case DICTIONARY -> filePaths.add("dictionary/all.lst");
            }
        }
        return executor.start(
                GpuAparapiBruteforceParamContainerUtilCreator.getParamContainer(
                        filePaths, "1P]5*x"
                )
        );
    }
}
