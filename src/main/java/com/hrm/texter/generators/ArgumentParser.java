package com.hrm.texter.generators;

public class ArgumentParser {
    
    public static Options parseArgs(String[] args) {
        String pattern = "";
        int number = 1;
        String output = "out.sql";
        boolean transactional = false;
        
        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i].equals("-p")) {
                pattern = args[i + 1];
            } else if (args[i].equals("-n")) {
                number = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-o")) {
                output = args[i + 1];
            } else if (args[i].equals("-t")) {
                transactional = Boolean.parseBoolean(args[i + 1]);
            } else {
                throw new RuntimeException("unknown argument \"" + args[i] + "\"");
            }
        }
        return new Options(pattern, number, output, transactional);
    }
}
