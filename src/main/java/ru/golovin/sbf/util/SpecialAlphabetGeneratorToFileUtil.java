package ru.golovin.sbf.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class SpecialAlphabetGeneratorToFileUtil {

    static final char[] alphabet;

    static {
        String specials = "!?.:;,()[]{}\"'<>@#$%^&*+=~|\\/";
        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) sb.append(c);
        for (char c = 'A'; c <= 'Z'; c++) sb.append(c);
        for (char c = '0'; c <= '9'; c++) sb.append(c);
        sb.append(specials);
        alphabet = sb.toString().toCharArray();
    }

    public static void generate(String outputFile, int maxDepth) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            generateCombinations("", 0, maxDepth, writer);
            System.out.println("Генерация завершена. Файл: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateCombinations(String prefix, int depth, int maxDepth, BufferedWriter writer) throws IOException {
        if (depth > maxDepth) return;
        writer.write(prefix);
        writer.newLine();
        for (char c : alphabet) {
            generateCombinations(prefix + c, depth + 1, maxDepth, writer);
        }
    }
}
