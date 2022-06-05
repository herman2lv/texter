package com.hrm.texter.data.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.hrm.texter.data.ResourceReader;

public class ResourceReaderImpl implements ResourceReader {

    @Override
    public String read(String resource) {
        return read(resource, Collectors.joining(System.lineSeparator()));
    }

    @Override
    public List<String> readLines(String resource) {
        return read(resource, Collectors.toList());
    }

    private <A, R> R read(String resource, Collector<? super String, A, R> collector) {
        try (InputStream is = getClass().getResourceAsStream(resource);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(collector);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
