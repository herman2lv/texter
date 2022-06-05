package com.hrm.texter.service;

import java.util.List;

public interface GeneratorService {

    List<String> getPhrases(String pattern, int number);

}