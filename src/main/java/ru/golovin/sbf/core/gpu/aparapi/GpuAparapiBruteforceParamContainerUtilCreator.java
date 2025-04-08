package ru.golovin.sbf.core.gpu.aparapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class GpuAparapiBruteforceParamContainerUtilCreator {

    private GpuAparapiBruteforceParamContainerUtilCreator() {
    }

    public static GpuAparapiBruteforceParamContainer getParamContainer(List<String> filePaths, String target) throws IOException {
        List<List<String>> fileLineList = getFileLineList(filePaths);
        List<Integer> fileLengths = new ArrayList<>();
        List<Integer> fileMaxLengths = new ArrayList<>();
        for (List<String> fileLines : fileLineList) {
            fileMaxLengths.add(fileLines.stream().mapToInt(String::length).max().orElse(1));
            fileLengths.add(fileLines.size());
        }
        List<char[]> fileCharArrays = new ArrayList<>();
        List<int[]> fileLineLengths = new ArrayList<>();
        for (int i = 0; i < fileLengths.size(); i++) {
            char[] file1Array = new char[fileLengths.get(i) * fileMaxLengths.get(i)];
            int[] fileLineLength = new int[fileLengths.get(i)];
            for (int j = 0; j < fileLengths.get(i); j++) {
                String padded = String.format("%-" + fileMaxLengths.get(i) + "s", fileLineList.get(i).get(j));
                padded.getChars(0, fileMaxLengths.get(i), file1Array, j * fileMaxLengths.get(i));
                fileLineLength[j] = fileLineList.get(i).get(j).length();
            }
            fileCharArrays.add(file1Array);
            fileLineLengths.add(fileLineLength);
        }
        return new GpuAparapiBruteforceParamContainer(
                fileCharArrays,
                fileLengths,
                fileMaxLengths,
                fileLineLengths,
                target
        );
    }

    private static List<List<String>> getFileLineList(List<String> filePaths) throws IOException {
        List<List<String>> fileLines = new ArrayList<>();
        for (String filePath : filePaths) {
            fileLines.add(Files.readAllLines(Paths.get(filePath)));
        }
        return fileLines;
    }
}
