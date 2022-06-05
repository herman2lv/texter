package com.hrm.texter;

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
            List<String> phrases = generator.getPhrases(options.getPattern(), options.getNumber());
            writer.saveQueries(options.getOutput(), phrases, options.isTransactional());
        } catch (Throwable e) {
            System.out.println("Try to run app with \"-h\" (--help) for help");
            System.err.println("Error: " + e.getMessage());
        }
    }

    private Options getOptions(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Invalid arguments length!");
        }
        if (args[0].equals("-h") || args[0].endsWith("help")) {
            System.out.println(repository.getHelp());
            return null;
        }
        return ArgumentUtil.parseArgs(args);
    }
}
