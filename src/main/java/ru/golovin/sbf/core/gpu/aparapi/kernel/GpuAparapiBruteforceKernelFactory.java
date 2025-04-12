package ru.golovin.sbf.core.gpu.aparapi.kernel;

import ru.golovin.sbf.core.gpu.aparapi.GpuAparapiBruteforceParamContainer;

public final class GpuAparapiBruteforceKernelFactory {

    private GpuAparapiBruteforceKernelFactory() {}

    public static GpuAparapiBruteforceKernel create(GpuAparapiBruteforceParamContainer c) {
        return switch (c.getFileCount()) {
            case 2 -> new GpuAparapiBruteforceKernel2(
                    c.getFileCharArrays().get(0), c.getFileLengths().get(0), c.getFileMaxLengths().get(0), c.getFileLineLengths().get(0),
                    c.getFileCharArrays().get(1), c.getFileLengths().get(1), c.getFileMaxLengths().get(1), c.getFileLineLengths().get(1),
                    c.getTarget().toCharArray(), c.getTarget().length(), c.getResult()
            );
            case 3 -> new GpuAparapiBruteforceKernel3(
                    c.getFileCharArrays().get(0), c.getFileLengths().get(0), c.getFileMaxLengths().get(0), c.getFileLineLengths().get(0),
                    c.getFileCharArrays().get(1), c.getFileLengths().get(1), c.getFileMaxLengths().get(1), c.getFileLineLengths().get(1),
                    c.getFileCharArrays().get(2), c.getFileLengths().get(2), c.getFileMaxLengths().get(2), c.getFileLineLengths().get(2),
                    c.getTarget().toCharArray(), c.getTarget().length(), c.getResult()
            );
            case 4 -> new GpuAparapiBruteforceKernel4(
                    c.getFileCharArrays().get(0), c.getFileLengths().get(0), c.getFileMaxLengths().get(0), c.getFileLineLengths().get(0),
                    c.getFileCharArrays().get(1), c.getFileLengths().get(1), c.getFileMaxLengths().get(1), c.getFileLineLengths().get(1),
                    c.getFileCharArrays().get(2), c.getFileLengths().get(2), c.getFileMaxLengths().get(2), c.getFileLineLengths().get(2),
                    c.getFileCharArrays().get(3), c.getFileLengths().get(3), c.getFileMaxLengths().get(3), c.getFileLineLengths().get(3),
                    c.getTarget().toCharArray(), c.getTarget().length(), c.getResult()
            );
            case 5 -> new GpuAparapiBruteforceKernel5(
                    c.getFileCharArrays().get(0), c.getFileLengths().get(0), c.getFileMaxLengths().get(0), c.getFileLineLengths().get(0),
                    c.getFileCharArrays().get(1), c.getFileLengths().get(1), c.getFileMaxLengths().get(1), c.getFileLineLengths().get(1),
                    c.getFileCharArrays().get(2), c.getFileLengths().get(2), c.getFileMaxLengths().get(2), c.getFileLineLengths().get(2),
                    c.getFileCharArrays().get(3), c.getFileLengths().get(3), c.getFileMaxLengths().get(3), c.getFileLineLengths().get(3),
                    c.getFileCharArrays().get(4), c.getFileLengths().get(4), c.getFileMaxLengths().get(4), c.getFileLineLengths().get(4),
                    c.getTarget().toCharArray(), c.getTarget().length(), c.getResult()
            );
            default -> throw new IllegalStateException("Unexpected value: " + c.getFileCount());
        };
    }
}
