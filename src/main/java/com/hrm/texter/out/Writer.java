package com.hrm.texter.out;

import java.util.List;

public interface Writer {
    
    public void saveQueries(String fileName, List<String> queries, boolean transactional);

}
