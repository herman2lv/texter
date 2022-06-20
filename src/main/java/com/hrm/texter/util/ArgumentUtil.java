package com.hrm.texter.util;

public class ArgumentUtil {

    public static Options parseArgs(String[] args) {
        String patternString = null;
        String patternFile = null;
        int number = 1;
        String output = "out.txt";
        boolean transactional = false;

        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i].equals("-p")) {
                patternString = args[i + 1];
            } else if (args[i].equals("-n")) {
                number = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-o")) {
                output = args[i + 1];
            } else if (args[i].equals("-t")) {
                transactional = Boolean.parseBoolean(args[i + 1]);
            } else if (args[i].equals("-i")) {
                patternFile = args[i + 1];
            } else {
                throw new RuntimeException("unknown argument \"" + args[i] + "\"");
            }
        }

        if (patternString == null && patternFile == null) {
            throw new RuntimeException(
                    "Pattern is not specified. Use -p \"some pattern...\" or -i \"inputFileName.txt\"");
        }
        if (patternString != null && patternFile != null) {
            throw new RuntimeException(
                    "Both input file with pattern and pattern string are specified. Use only one of these options");
        }

        return new Options(patternFile, patternString, number, output, transactional);
    }
}
