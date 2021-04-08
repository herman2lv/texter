package com.epam.esm.util.generator.dml;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.epam.esm.util.generator.dml.GeneratorConstants.RESOURCES_DIRECTORY;

public class QueriesOutputWriter {

    private static final String START_TRANSACTION = "START TRANSACTION;";
    private static final String COMMIT = "COMMIT;";

    public static void saveQueries(String fileName, List<String> queries) {
        createPath(fileName);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                RESOURCES_DIRECTORY + fileName, true)))) {
            writer.write(START_TRANSACTION);
            writer.newLine();
            queries.forEach(s -> {
                try {
                    writer.write(s);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write(COMMIT);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createPath(String fileName) {
        Path path = Paths.get(RESOURCES_DIRECTORY + fileName);
        path = path.getParent();
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
