package com.epam.esm.util.generator.dml.tableGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoinTagGiftCertificateGenerator {
    private static final Random RANDOM = new Random();
    private static final int MIN_NUMBER_OF_TAGS_FOR_ONE_CERTIFICATE = 2;
    private static final int MAX_NUMBER_OF_TAGS_FOR_ONE_CERTIFICATE = 5;
    private static final String INSERT_JOIN_TABLE =
            "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (%d, %d);";

    public static List<String> generateInsertQueries(int numberOfGiftCertificates, int numberOfTags) {
        List<String> queries = new ArrayList<>();
        for (int i = 1; i <= numberOfGiftCertificates; i++) {
            int numberOfTagsOfGiftCertificate =
                    generateInt(MIN_NUMBER_OF_TAGS_FOR_ONE_CERTIFICATE, MAX_NUMBER_OF_TAGS_FOR_ONE_CERTIFICATE);
            List<Integer> tagIds = new ArrayList<>();
            for (int j = 1; j <= numberOfTagsOfGiftCertificate; j++) {
                int tagId = generateInt(0, numberOfTags - 1);
                if (tagIds.contains(tagId)) {
                    continue;
                }
                queries.add(String.format(INSERT_JOIN_TABLE, i, tagId));
                tagIds.add(tagId);
            }
        }
        return queries;
    }

    private static int generateInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
