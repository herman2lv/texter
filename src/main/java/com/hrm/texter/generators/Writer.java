package com.hrm.texter.generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Writer {
    private static final String START_TRANSACTION = "START TRANSACTION;";
    private static final String COMMIT = "COMMIT;";

    public static void saveQueries(String fileName, List<String> queries, boolean transactional) {
        try {
            Path path = createPath(fileName);
            if (transactional) {
                queries.add(0, START_TRANSACTION);
                queries.add(COMMIT);
            }
            Files.write(path, queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path createPath(String fileName) {
        Path path = Paths.get(fileName);
        Path parent = path.getParent();
        if (parent != null && Files.notExists(parent)) {
            throw new RuntimeException("Directory " + parent + " not exist");
        }
        return path;
    }
}

