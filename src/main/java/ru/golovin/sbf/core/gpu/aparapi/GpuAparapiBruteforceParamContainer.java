package ru.golovin.sbf.core.gpu.aparapi;

import lombok.Getter;

import java.util.List;

@Getter
public class GpuAparapiBruteforceParamContainer{

    private final List<char[]> fileCharArrays;
    private final List<Integer> fileLengths;
    private final List<Integer> fileMaxLengths;
    private final List<int[]> fileLineLengths;
    private final String target;
    private final int fileCount;
    private final long totalCombinations;
    private final int[] result;

    public GpuAparapiBruteforceParamContainer(
            List<char[]> fileCharArrays,
            List<Integer> fileLengths,
            List<Integer> fileMaxLengths,
            List<int[]> fileLineLengths,
            String target
    ) {
        if (fileCharArrays.size() != fileLengths.size()
                || fileCharArrays.size() != fileMaxLengths.size()
                || fileCharArrays.size() != fileLineLengths.size()
        ) {
            throw new IllegalArgumentException("List size mismatch");
        }
        this.fileCharArrays = fileCharArrays;
        this.fileLengths = fileLengths;
        this.fileMaxLengths = fileMaxLengths;
        this.fileLineLengths = fileLineLengths;
        this.target = target;
        this.fileCount = fileCharArrays.size();
        long combinations = 1;
        for (int[] fileLineLength : fileLineLengths) {
            combinations *= fileLineLength.length;
        }
        this.totalCombinations = combinations;
        this.result = new int[fileCount + 1];
    }
}
