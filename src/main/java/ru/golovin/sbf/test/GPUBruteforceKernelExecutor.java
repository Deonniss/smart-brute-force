package ru.golovin.sbf.test;

import com.aparapi.Kernel;
import com.aparapi.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GPUBruteforceKernelExecutor {

    public static class GPUBruteforceKernel extends Kernel {
        private final char[] file1;
        private final int[] file1Lengths;
        private final int file1Count;
        private final int file1LineLength;

        private final char[] file2;
        private final int[] file2Lengths;
        private final int file2Count;
        private final int file2MaxLength;

        private final char[] target;
        private final int targetLength;

        private long offset = 0; // Новое поле

        public void setOffset(long offset) {
            this.offset = offset;
        }

        public final int[] resultOut;

        public GPUBruteforceKernel(char[] file1, int file1Count, int file1LineLength, int[] file1Lengths,
                                   char[] file2, int file2Count, int file2MaxLength, int[] file2Lengths,
                                   char[] target, int targetLength, int[] resultOut) {
            this.file1 = file1;
            this.file1Count = file1Count;
            this.file1LineLength = file1LineLength;
            this.file1Lengths = file1Lengths;

            this.file2 = file2;
            this.file2Count = file2Count;
            this.file2MaxLength = file2MaxLength;
            this.file2Lengths = file2Lengths;

            this.target = target;
            this.targetLength = targetLength;
            this.resultOut = resultOut;
        }

        @Override
        public void run() {
            long globalIndex = offset + getGlobalId();
            int i = (int)(globalIndex / file2Count);
            int j = (int)(globalIndex % file2Count);
            if (i >= file1Count || j >= file2Count) return;

            int len1 = file1Lengths[i];
            int len2 = file2Lengths[j];
            if (len1 + len2 != targetLength) return;

            boolean match = true;
            for (int k = 0; k < len1; k++) {
                match = match && (file1[i * file1LineLength + k] == target[k]);
            }
            if (match) {
                for (int k = 0; k < len2; k++) {
                    match = match && (file2[j * file2MaxLength + k] == target[len1 + k]);
                }
            }
            if (match) {
                resultOut[0] = 1;
                resultOut[1] = i;
                resultOut[2] = j;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> file1Lines = Files.readAllLines(Paths.get("dictionary/spec-3.lst"));
        List<String> file2Lines = Files.readAllLines(Paths.get("dictionary/all.lst"));

        int file1Count = file1Lines.size();
        int file2Count = file2Lines.size();

        int file1MaxLength = file1Lines.stream().mapToInt(String::length).max().orElse(1);
        int file2MaxLength = file2Lines.stream().mapToInt(String::length).max().orElse(1);

        char[] file1Array = new char[file1Count * file1MaxLength];
        int[] file1Lengths = new int[file1Count];
        for (int i = 0; i < file1Count; i++) {
            String padded = String.format("%-" + file1MaxLength + "s", file1Lines.get(i));
            padded.getChars(0, file1MaxLength, file1Array, i * file1MaxLength);
            file1Lengths[i] = file1Lines.get(i).length();
        }

        char[] file2Array = new char[file2Count * file2MaxLength];
        int[] file2Lengths = new int[file2Count];
        for (int i = 0; i < file2Count; i++) {
            String padded = String.format("%-" + file2MaxLength + "s", file2Lines.get(i));
            padded.getChars(0, file2MaxLength, file2Array, i * file2MaxLength);
            file2Lengths[i] = file2Lines.get(i).length();
        }

        String targetString = "hs1000000";
        int targetLength = targetString.length();
        char[] target = targetString.toCharArray();

        long totalCombinations = (long) file1Count * file2Count;
        int batchSize = 2_000_000_000;
        int localSize = 256;

        int[] resultOut = new int[3];

        GPUBruteforceKernel kernel = new GPUBruteforceKernel(
                file1Array, file1Count, file1MaxLength, file1Lengths,
                file2Array, file2Count, file2MaxLength, file2Lengths,
                target, targetLength,
                resultOut);

        for (long offset = 0; offset < totalCombinations; offset += batchSize) {
            int currentBatch = (int)Math.min(batchSize, totalCombinations - offset);
            int paddedSize = ((currentBatch + localSize - 1) / localSize) * localSize;
            Range range = Range.create(paddedSize, localSize);

            kernel.setOffset(offset);
            kernel.execute(range);

            System.out.println(offset / batchSize);

            if (resultOut[0] == 1) {
                int i = resultOut[1];
                int j = resultOut[2];
                String foundLine1 = new String(file1Array, i * file1MaxLength, file1Lengths[i]);
                String foundLine2 = new String(file2Array, j * file2MaxLength, file2Lengths[j]);
                System.out.println("found");
                System.out.println("file-1, line " + i + ": " + foundLine1);
                System.out.println("file-2, line " + j + ": " + foundLine2);
                break;
            }
        }
        kernel.dispose();
    }
}
