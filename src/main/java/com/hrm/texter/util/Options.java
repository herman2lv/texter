package com.hrm.texter.util;

public class Options {
    private String pattern = "";
    private int number = 1;
    private String output = "out.txt";
    private boolean transactional = false;
    
    public Options(String pattern, int number, String output, boolean transactional) {
        this.pattern = pattern;
        this.number = number;
        this.output = output;
        this.transactional = transactional;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    @Override
    public String toString() {
        return "Options [pattern=" + pattern + ", number=" + number + ", output=" + output + ", transactional="
                + transactional + "]";
    }

}
