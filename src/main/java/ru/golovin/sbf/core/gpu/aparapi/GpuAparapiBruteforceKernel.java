package ru.golovin.sbf.core.gpu.aparapi;

import com.aparapi.Kernel;
import lombok.Setter;

public class GpuAparapiBruteforceKernel extends Kernel {

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

    private final char[] target;
    private final int targetLength;

    @Setter
    private long offset = 0;

    public final int[] resultOut;

    public GpuAparapiBruteforceKernel(
            char[] file1, int file1Count, int file1MaxLength, int[] file1Lengths,
            char[] file2, int file2Count, int file2MaxLength, int[] file2Lengths,
            char[] file3, int file3Count, int file3MaxLength, int[] file3Lengths,
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

        this.target = target;
        this.targetLength = targetLength;
        this.resultOut = resultOut;
    }

    @Override
    public void run() {
        long globalIndex = offset + getGlobalId();
        int i = (int) (globalIndex / (file2Count * file3Count));
        int j = (int) ((globalIndex / file3Count) % file2Count);
        int k = (int) (globalIndex % file3Count);

        if (i >= file1Count || j >= file2Count || k >= file3Count) return;

        int len1 = file1Lengths[i];
        int len2 = file2Lengths[j];
        int len3 = file3Lengths[k];

        if (len1 + len2 + len3 != targetLength) return;

        boolean match = true;
        for (int x = 0; x < len1; x++) {
            match = match && (file1[i * file1MaxLength + x] == target[x]);
        }
        if (match) {
            for (int x = 0; x < len2; x++) {
                match = match && (file2[j * file2MaxLength + x] == target[len1 + x]);
            }
        }
        if (match) {
            for (int x = 0; x < len3; x++) {
                match = match && (file3[k * file3MaxLength + x] == target[len1 + len2 + x]);
            }
        }
        if (match) {
            resultOut[0] = 1;
            resultOut[1] = i;
            resultOut[2] = j;
            resultOut[3] = k;
        }
    }

}
