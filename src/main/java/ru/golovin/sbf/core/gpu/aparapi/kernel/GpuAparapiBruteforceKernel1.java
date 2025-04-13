package ru.golovin.sbf.core.gpu.aparapi.kernel;

import lombok.Setter;

public class GpuAparapiBruteforceKernel1 extends GpuAparapiBruteforceKernel {

    private final char[] file;
    private final int[] fileLengths;
    private final int fileCount;
    private final int fileMaxLength;
    private final char[] target;
    private final int targetLength;

    @Setter
    private long offset = 0;

    public final int[] resultOut;

    public GpuAparapiBruteforceKernel1(
            char[] file, int fileCount, int fileMaxLength, int[] fileLengths,
            char[] target, int targetLength, int[] resultOut
    ) {
        this.file = file;
        this.fileCount = fileCount;
        this.fileMaxLength = fileMaxLength;
        this.fileLengths = fileLengths;
        this.target = target;
        this.targetLength = targetLength;
        this.resultOut = resultOut;
    }

    @Override
    public void run() {
        int i = (int) (offset + getGlobalId());
        if (i >= fileCount) return;
        int len = fileLengths[i];
        if (len != targetLength) return;
        boolean match = true;
        for (int x = 0; x < len && match; x++) {
            if (file[i * fileMaxLength + x] != target[x]) {
                match = false;
            }
        }
        if (match) {
            resultOut[0] = 1;
            resultOut[1] = i;
        }
    }
}
