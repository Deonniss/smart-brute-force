package ru.golovin.sbf.core.gpu.aparapi;

public class MultiFileDataPreparer {

    public static class MultiFileData {
        public final char[] fileData;      // объединённое содержимое всех файлов
        public final int[] fileOffsets;    // смещения начала каждого файла в fileData
        public final int[] fileLengths;    // длины каждого файла
        public final int fileCount;

        public MultiFileData(char[] fileData, int[] fileOffsets, int[] fileLengths, int fileCount) {
            this.fileData = fileData;
            this.fileOffsets = fileOffsets;
            this.fileLengths = fileLengths;
            this.fileCount = fileCount;
        }
    }

    public static MultiFileData prepare(String[] files) {
        int fileCount = files.length;
        int[] fileLengths = new int[fileCount];
        int[] fileOffsets = new int[fileCount];
        int totalLength = 0;

        // Считаем длины и определяем смещения
        for (int i = 0; i < fileCount; i++) {
            fileLengths[i] = files[i].length();
            fileOffsets[i] = totalLength;
            totalLength += files[i].length();
        }

        // Объединяем все файлы в один массив символов
        char[] fileData = new char[totalLength];
        int pos = 0;
        for (String file : files) {
            file.getChars(0, file.length(), fileData, pos);
            pos += file.length();
        }

        return new MultiFileData(fileData, fileOffsets, fileLengths, fileCount);
    }
}

