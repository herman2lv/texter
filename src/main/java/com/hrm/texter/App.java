package com.hrm.texter;

import com.hrm.texter.generators.ArgumentParser;
import com.hrm.texter.generators.Generator;
import com.hrm.texter.generators.Options;
import com.hrm.texter.generators.Reader;
import com.hrm.texter.generators.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/*
INSERT INTO personal_infos (email, first_name, last_name, phone_number) VALUES ('{email}', '{first_name}', '{last_name}', {phone_number});
INSERT INTO users ("login", "password", personal_info_id) VALUES ('{first_name}_{last_name}', '{hash}', {sequence:1:1});
INSERT INTO departments ("name") VALUES ('{noun}');
INSERT INTO employees (first_name, last_name, department_id) VALUES ('{first_name}', '{last_name}', {number:1:50});
INSERT INTO sections ("name") VALUES ('{noun}');
INSERT INTO students (first_name, last_name) VALUES ('{first_name}', '{last_name}');
INSERT INTO students_sections (student_id, section_id) VALUES ({number:1:1000}, {number:1:50});
 */

public class App {
    public static void main(String[] args) {
        try {
            if (args.length < 2 || args.length > 0 && args[0].equals("-h")) {
                System.out.println(getHelp());
            }
            Options options = ArgumentParser.parseArgs(args);
            Reader reader = new Reader();
            Generator generator = new Generator(options.getPattern(), reader);
            List<String> phrases = generator.getPhrases(options.getNumber());
            Writer.saveQueries(options.getOutput(), phrases, options.isTransactional());
        } catch (Throwable e) {
            System.out.println("try run app with \"-h\" for help");
            System.err.println(e.getMessage());
        }
    }

    private static String getHelp() throws IOException {
        try (InputStream is = App.class.getResourceAsStream("/help.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
