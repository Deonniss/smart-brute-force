package ru.golovin.sbf.core.gpu.aparapi;

import com.aparapi.Range;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.io.IOException;
import java.util.List;

public class GpuAparapiBruteforceKernelExecutor {

    private static final int BATCH_SIZE = 2_000_000_000;
    private static final int LOCAL_SIZE = 256;

    public static void main(String[] args) throws IOException {
        start(
                GpuAparapiBruteforceParamContainerUtilCreator.getParamContainer(
                        List.of("dictionary/all.lst", "dictionary/spec-2.lst", "dictionary/spec-1.lst"),
                        "{thtajhlibh///"
                )
        );
    }

    public static void start(GpuAparapiBruteforceParamContainer container) {
        GpuAparapiBruteforceKernel kernel = GpuAparapiBruteforceKernelFactory.create(container);
        final long totalCombinations = container.getTotalCombinations();
        try (ProgressBar pb = new ProgressBar("Processing", totalCombinations / BATCH_SIZE)) {
            for (long offset = 0; offset < container.getTotalCombinations(); offset += BATCH_SIZE) {
                int currentBatch = (int) Math.min(BATCH_SIZE, container.getTotalCombinations() - offset);
                int paddedSize = ((currentBatch + LOCAL_SIZE - 1) / LOCAL_SIZE) * LOCAL_SIZE;
                Range range = Range.create(paddedSize, LOCAL_SIZE);
                kernel.setOffset(offset);
                kernel.execute(range);
                pb.step();
                printFoundLines(container);
            }
        }
        kernel.dispose();
    }

    public static void printFoundLines(GpuAparapiBruteforceParamContainer container) {
        int[] result = container.getResult();
        if (result[0] != 1) {
            return;
        }
        int fileCount = container.getFileCount();
        if (fileCount < 3 || fileCount > 5 || result.length < fileCount + 1) {
            return;
        }
        var charArrays = container.getFileCharArrays();
        var maxLengths = container.getFileMaxLengths();
        var lineLengths = container.getFileLineLengths();
        System.out.println("found");
        for (int fileIndex = 0; fileIndex < fileCount; fileIndex++) {
            int lineIndex = result[fileIndex + 1];
            char[] fileChars = charArrays.get(fileIndex);
            int offset = lineIndex * maxLengths.get(fileIndex);
            int length = lineLengths.get(fileIndex)[lineIndex];
            String foundLine = new String(fileChars, offset, length);
            System.out.println("file-" + (fileIndex + 1) + ", line " + lineIndex + ": " + foundLine);
        }
    }
}
