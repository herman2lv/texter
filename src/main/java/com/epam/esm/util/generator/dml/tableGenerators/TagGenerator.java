package com.epam.esm.util.generator.dml.tableGenerators;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.epam.esm.util.generator.dml.GeneratorConstants.RESOURCES_DIRECTORY;

public class TagGenerator {
    private static final Random RANDOM = new Random();
    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES ('%s');";

    public static List<String> generateInsertQueries(int requiredSize) {
        List<String> names = generateTagNames(requiredSize);
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            queries.add(String.format(INSERT_TAG, names.get(i)));
        }
        return queries;
    }

    private static List<String> generateTagNames(int requiredSize) {
        List<String> giftCertificateNames = new ArrayList<>();
        try (BufferedReader nounsReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + "nouns.txt")));
             BufferedReader adjectivesReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES_DIRECTORY + "adjectives.txt")))) {
            List<String> words = nounsReader.lines().collect(Collectors.toList());
            words.addAll(adjectivesReader.lines().collect(Collectors.toList()));
            for (int i = 0; i < requiredSize; i++) {
                giftCertificateNames.add(getWord(words));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return giftCertificateNames;
    }

    private static String getWord(List<String> words) {
        return words.get(RANDOM.nextInt(words.size()));
    }
}
