package com.anand.pdf.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MemoryUtil {

    public static void print(String stage) throws IOException {

        Runtime rt =
                Runtime.getRuntime();

        long total =
                rt.totalMemory();

        long free =
                rt.freeMemory();

        long used =
                total - free;

        System.out.println(
                stage
                        + " Used MB="
                        + used / 1024 / 1024);

        Path folder =
                Paths.get(
                        "src/main/resources/mock-data/request-1001");

        long totalBytes = Files.walk(folder)

                .filter(Files::isRegularFile)

                .mapToLong(f -> {

                    try {

                        return Files.size(f);

                    } catch(Exception e) {

                        return 0;
                    }
                })

                .sum();
        System.out.println(

                "Dataset MB="

                        + totalBytes / 1024 / 1024);
    }
}