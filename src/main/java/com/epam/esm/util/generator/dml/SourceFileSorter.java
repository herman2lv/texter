package com.epam.esm.util.generator.dml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.epam.esm.util.generator.dml.GeneratorConstants.RESOURCES_DIRECTORY;

public class SourceFileSorter {
    public static void sortFileContent(String inputFileName, String outputFileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + inputFileName)));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                     RESOURCES_DIRECTORY + outputFileName)))) {
            reader.lines()
                    .distinct()
                    .sorted()
                    .forEach(s -> {
                        try {
                            writer.write(s);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
