package ru.golovin.sbf.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FileCombinator {

    static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        // Целевая строка, при совпадении которой происходит завершение
        final String target = "gs1os3";
        // Флаг, сигнализирующий о том, что нужная комбинация найдена
        AtomicBoolean found = new AtomicBoolean(false);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Загружаем строки из файлов в память
            List<String> file1Lines = Files.readAllLines(Paths.get("dictionary/spec-3.lst"));
            List<String> file2Lines = Files.readAllLines(Paths.get("dictionary/spec-3.lst"));
//            List<String> file3Lines = Files.readAllLines(Paths.get("dictionary/all.lst"));

            // Пул виртуальных потоков

            List<Future<Void>> futures = new ArrayList<>();

            int blockSize = 40;

            // Для каждой строки из первого файла создаём подзадачу
            for (int i = 0; i < file1Lines.size(); i += blockSize) {
                if (found.get()) {
                    break;
                }

                // Определяем текущий блок строк
                int end = Math.min(i + blockSize, file1Lines.size());
                List<String> block = file1Lines.subList(i, end);

                Future<Void> future = executor.submit(() -> {
                    System.out.println(count.get());
                    // Если флаг уже установлен, выходим
                    if (found.get()) {
                        return null;
                    }
                    // Перебираем строки из второго файла
                    for (String line1 : block) {
                        if (found.get()) {
                            break;
                        }
                        for (String line2 : file2Lines) {
                            if (found.get()) {
                                break;
                            }
                            StringBuilder sb = new StringBuilder(line1);
                            sb.append(line2);
                            if (sb.toString().equals(target)) {
                                System.out.println("WIN!!!");
                                found.set(true);
                                break;
                            }
                        }
                        if (found.get()) {
                            break;
                        }
                    }
                    System.err.println(count.incrementAndGet());
                    return null;
                });
                futures.add(future);
            }

            // Ждём завершения всех задач
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (CancellationException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            executor.shutdown();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

