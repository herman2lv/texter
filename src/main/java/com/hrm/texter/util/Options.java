package com.hrm.texter.util;

public class Options {
    private String patternString;
    private Integer number;
    private String output;
    private String patternFile;
    private boolean transactional;

    public Options(String patternFile, String patternString, int number, String output, boolean transactional) {
        this.patternFile = patternFile;
        this.patternString = patternString;
        this.number = number;
        this.output = output;
        this.transactional = transactional;
    }

    public String getPatternString() {
        return patternString;
    }

    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }

    public String getPatternFile() {
        return patternFile;
    }

    public void setPatternFile(String patternFile) {
        this.patternFile = patternFile;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public Integer getNumber() {
        return number;
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
        return "Options [patternString=" + patternString + ", number=" + number + ", output=" + output
                + ", patternFile=" + patternFile + ", transactional=" + transactional + "]";
    }

}
