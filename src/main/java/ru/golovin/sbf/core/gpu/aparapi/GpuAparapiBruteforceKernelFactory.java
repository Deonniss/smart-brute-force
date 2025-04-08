package ru.golovin.sbf.core.gpu.aparapi;

public final class GpuAparapiBruteforceKernelFactory {

    private GpuAparapiBruteforceKernelFactory() {}

    public static GpuAparapiBruteforceKernel create(GpuAparapiBruteforceParamContainer c) {
        return switch (c.getFileCount()) {
            case 3 -> new GpuAparapiBruteforceKernel(
                    c.getFileCharArrays().get(0), c.getFileLengths().get(0), c.getFileMaxLengths().get(0), c.getFileLineLengths().get(0),
                    c.getFileCharArrays().get(1), c.getFileLengths().get(1), c.getFileMaxLengths().get(1), c.getFileLineLengths().get(1),
                    c.getFileCharArrays().get(2), c.getFileLengths().get(2), c.getFileMaxLengths().get(2), c.getFileLineLengths().get(2),
                    c.getTarget().toCharArray(), c.getTarget().length(), c.getResult()
            );
            default -> null;
        };
    }
}
