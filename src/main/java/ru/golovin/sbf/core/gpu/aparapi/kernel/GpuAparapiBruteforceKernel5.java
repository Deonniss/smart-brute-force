package ru.golovin.sbf.core.gpu.aparapi.kernel;

import lombok.Setter;

public class GpuAparapiBruteforceKernel5 extends GpuAparapiBruteforceKernel {

    private final char[] file1;
    private final int[] file1Lengths;
    private final int file1Count;
    private final int file1MaxLength;

    private final char[] file2;
    private final int[] file2Lengths;
    private final int file2Count;
    private final int file2MaxLength;

    private final char[] file3;
    private final int[] file3Lengths;
    private final int file3Count;
    private final int file3MaxLength;

    private final char[] file4;
    private final int[] file4Lengths;
    private final int file4Count;
    private final int file4MaxLength;

    private final char[] file5;
    private final int[] file5Lengths;
    private final int file5Count;
    private final int file5MaxLength;

    private final char[] target;
    private final int targetLength;

    @Setter
    private long offset = 0;

    public final int[] resultOut;

    public GpuAparapiBruteforceKernel5(
            char[] file1, int file1Count, int file1MaxLength, int[] file1Lengths,
            char[] file2, int file2Count, int file2MaxLength, int[] file2Lengths,
            char[] file3, int file3Count, int file3MaxLength, int[] file3Lengths,
            char[] file4, int file4Count, int file4MaxLength, int[] file4Lengths,
            char[] file5, int file5Count, int file5MaxLength, int[] file5Lengths,
            char[] target, int targetLength, int[] resultOut
    ) {
        this.file1 = file1;
        this.file1Count = file1Count;
        this.file1MaxLength = file1MaxLength;
        this.file1Lengths = file1Lengths;

        this.file2 = file2;
        this.file2Count = file2Count;
        this.file2MaxLength = file2MaxLength;
        this.file2Lengths = file2Lengths;

        this.file3 = file3;
        this.file3Count = file3Count;
        this.file3MaxLength = file3MaxLength;
        this.file3Lengths = file3Lengths;

        this.file4 = file4;
        this.file4Count = file4Count;
        this.file4MaxLength = file4MaxLength;
        this.file4Lengths = file4Lengths;

        this.file5 = file5;
        this.file5Count = file5Count;
        this.file5MaxLength = file5MaxLength;
        this.file5Lengths = file5Lengths;

        this.target = target;
        this.targetLength = targetLength;
        this.resultOut = resultOut;
    }

    @Override
    public void run() {
        long globalIndex = offset + getGlobalId();
        int i = (int) (globalIndex / (file2Count * file3Count * file4Count * file5Count));
        int j = (int) ((globalIndex / (file3Count * file4Count * file5Count)) % file2Count);
        int k = (int) ((globalIndex / (file4Count * file5Count)) % file3Count);
        int l = (int) ((globalIndex / file5Count) % file4Count);
        int m = (int) (globalIndex % file5Count);

        if (i >= file1Count || j >= file2Count || k >= file3Count || l >= file4Count || m >= file5Count) return;

        int len1 = file1Lengths[i];
        int len2 = file2Lengths[j];
        int len3 = file3Lengths[k];
        int len4 = file4Lengths[l];
        int len5 = file5Lengths[m];

        if (len1 + len2 + len3 + len4 + len5 != targetLength) return;

        boolean match = true;
        int pos = 0;

        for (int x = 0; x < len1 && match; x++, pos++) {
            if (file1[i * file1MaxLength + x] != target[pos]) match = false;
        }
        for (int x = 0; x < len2 && match; x++, pos++) {
            if (file2[j * file2MaxLength + x] != target[pos]) match = false;
        }
        for (int x = 0; x < len3 && match; x++, pos++) {
            if (file3[k * file3MaxLength + x] != target[pos]) match = false;
        }
        for (int x = 0; x < len4 && match; x++, pos++) {
            if (file4[l * file4MaxLength + x] != target[pos]) match = false;
        }
        for (int x = 0; x < len5 && match; x++, pos++) {
            if (file5[m * file5MaxLength + x] != target[pos]) match = false;
        }

        if (match) {
            resultOut[0] = 1;
            resultOut[1] = i;
            resultOut[2] = j;
            resultOut[3] = k;
            resultOut[4] = l;
            resultOut[5] = m;
        }
    }
}
