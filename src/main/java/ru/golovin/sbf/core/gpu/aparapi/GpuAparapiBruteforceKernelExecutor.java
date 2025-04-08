package ru.golovin.sbf.core.gpu.aparapi;

import com.aparapi.Range;

import java.io.IOException;
import java.util.List;

public class GpuAparapiBruteforceKernelExecutor {

    private static final int BATCH_SIZE = 2_000_000_000;
    private static final int LOCAL_SIZE = 256;

    public static void main(String[] args) throws IOException {
        start(
                GpuAparapiBruteforceParamContainerUtilCreator.getParamContainer(
                        List.of("dictionary/all.lst", "dictionary/spec-1.lst", "dictionary/spec-1.lst"),
                        "{thtajhlibh//"
                )
        );
    }

    public static void start(GpuAparapiBruteforceParamContainer container) {
        GpuAparapiBruteforceKernel kernel = GpuAparapiBruteforceKernelFactory.create(container);
        System.out.println("Total combinations: " + container.getTotalCombinations());
        for (long offset = 0; offset < container.getTotalCombinations(); offset += BATCH_SIZE) {
            int currentBatch = (int) Math.min(BATCH_SIZE, container.getTotalCombinations() - offset);
            int paddedSize = ((currentBatch + LOCAL_SIZE - 1) / LOCAL_SIZE) * LOCAL_SIZE;
            Range range = Range.create(paddedSize, LOCAL_SIZE);

            kernel.setOffset(offset);
            kernel.execute(range);

            System.out.println(offset / BATCH_SIZE);

            if (container.getResult()[0] == 1) {
                int i = container.getResult()[1];
                int j = container.getResult()[2];
                int k = container.getResult()[3];
                String foundLine1 = new String(
                        container.getFileCharArrays().get(0),
                        i * container.getFileMaxLengths().get(0),
                        container.getFileLineLengths().get(0)[i]
                );
                String foundLine2 = new String(
                        container.getFileCharArrays().get(1),
                        j * container.getFileMaxLengths().get(1),
                        container.getFileLineLengths().get(1)[j]
                );
                String foundLine3 = new String(
                        container.getFileCharArrays().get(2),
                        k * container.getFileMaxLengths().get(2),
                        container.getFileLineLengths().get(2)[k]
                );
                System.out.println("found");
                System.out.println("file-1, line " + i + ": " + foundLine1);
                System.out.println("file-2, line " + j + ": " + foundLine2);
                System.out.println("file-3, line " + k + ": " + foundLine3);
                break;
            }
        }
        kernel.dispose();
    }
}
