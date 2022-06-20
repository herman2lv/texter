package com.hrm.texter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.hrm.texter.data.Repository;
import com.hrm.texter.out.Writer;
import com.hrm.texter.service.GeneratorService;
import com.hrm.texter.util.ArgumentUtil;
import com.hrm.texter.util.Options;

public class Application {
    private final GeneratorService generator;
    private final Writer writer;
    private final Repository repository;

    public Application(GeneratorService generator, Writer writer, Repository repository) {
        this.generator = generator;
        this.writer = writer;
        this.repository = repository;
    }

    public void run(String[] args) {
        try {
            Options options = getOptions(args);
            if (options == null) {
                return;
            }

            List<String> phrases = generator.getPhrases(getPattern(options), options.getNumber());
            writer.saveQueries(options.getOutput(), phrases, options.isTransactional());
        } catch (Throwable e) {
            System.out.println("Try to run app with \"-h\" (--help) for help");
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String getPattern(Options options) throws IOException {
        String pattern = options.getPatternString();
        if (pattern != null) {
            return pattern;
        }
        byte[] bytes = Files.readAllBytes(Path.of(options.getPatternFile()));
        int length = removeLastLineFeed(bytes);
        return new String(bytes, 0, length);
    }

    private int removeLastLineFeed(byte[] bytes) {
        int length = bytes.length;
        if (bytes[length - 1] == '\n') {
            length -= 1;
            if (bytes[length - 1] == '\r') {
                length -= 1;
            }
        }
        return length;
    }

    private Options getOptions(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Empty arguments list");
        }
        if (args[0].equals("-h") || args[0].endsWith("help")) {
            System.out.println(repository.getHelpShort());
            return null;
        }
        if (args[0].equals("--man")) {
            System.out.println(repository.getHelpFull());
            return null;
        }
        if (args.length < 2) {
            throw new RuntimeException("Invalid arguments length!");
        }
        return ArgumentUtil.parseArgs(args);
    }
}
