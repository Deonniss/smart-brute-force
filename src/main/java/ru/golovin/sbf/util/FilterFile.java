package ru.golovin.sbf.util;

import java.io.*;
import java.util.*;

public class FilterFile {
    // Алфавит, который нужно проверять
    static String specials = "!?.:;,()[]{}\"'<>@#$%^&*+=~|\\/";
    static char[] alphabet;

    static {
        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) sb.append(c);
        for (char c = 'A'; c <= 'Z'; c++) sb.append(c);
        for (char c = '0'; c <= '9'; c++) sb.append(c);
        sb.append(specials);
        alphabet = sb.toString().toCharArray();
    }

    // Проверка, состоит ли строка только из разрешенных символов
    private static boolean isValidString(String line) {
        for (char c : line.toCharArray()) {
            if (!isInAlphabet(c)) {
                return false;
            }
        }
        return true;
    }

    // Проверка, входит ли символ в алфавит
    private static boolean isInAlphabet(char c) {
        for (char validChar : alphabet) {
            if (c == validChar) {
                return true;
            }
        }
        return false;
    }

    // Основной метод для обработки файла
    public static void main(String[] args) throws IOException {
        String inputFilePath = "dictionary/all-2.lst"; // Путь к входному файлу
        String outputFilePath = "output.txt"; // Путь к выходному файлу

        // Чтение входного файла и запись валидных строк в выходной файл
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidString(line)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        System.out.println("Файл обработан. Невалидные строки удалены.");
    }
}

