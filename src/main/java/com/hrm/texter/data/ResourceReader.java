package com.hrm.texter.data;

import java.util.List;

public interface ResourceReader {

    String read(String resource);
    
    List<String> readLines(String resource);
    
}
