package com.hrm.texter.out.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.hrm.texter.out.Writer;

public class WriterImpl implements Writer {
    private static final String START_TRANSACTION = "START TRANSACTION;";
    private static final String COMMIT = "COMMIT;";

    @Override
    public void saveQueries(String fileName, List<String> queries, boolean transactional) {
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

    private Path createPath(String fileName) {
        Path path = Paths.get(fileName);
        Path parent = path.getParent();
        if (parent != null && Files.notExists(parent)) {
            throw new RuntimeException("Directory " + parent + " not exist");
        }
        return path;
    }
}

