package com.epam.esm.util.generator.dml.tableGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderDetailsGenerator {
    private static final Random RANDOM = new Random();
    private static final String INSERT_ORDER_DETAILS = "INSERT INTO order_details "
            + "(order_id, gift_certificate_id, number_of_items, item_cost) "
            + "SELECT %d, %d, %d, gc.price FROM gift_certificate gc WHERE gc.id = %d;";

    public static List<String> generateInsertQueries(int numberOfOrders, int numberOfGiftCertificates) {
        List<String> queries = new ArrayList<>();
        for (int i = 1; i <= numberOfOrders; i++) {
            int numberOfDifferentCertificates = generateNumberOfDifferentCertificates();
            List<Integer> giftCertificatesIds = new ArrayList<>();
            for (int j = 0; j < numberOfDifferentCertificates; j++) {
                int giftCertificateId = generateInt(1, numberOfGiftCertificates);
                if (giftCertificatesIds.contains(giftCertificateId)) {
                    continue;
                }
                int numberOfItems = generateInt(1, 2);
                queries.add(String.format(
                        INSERT_ORDER_DETAILS, i, giftCertificateId, numberOfItems, giftCertificateId));
                giftCertificatesIds.add(giftCertificateId);
            }
        }
        return queries;
    }

    private static int generateNumberOfDifferentCertificates() {
        int numberOfDifferentCertificates;
        int randomNumber = RANDOM.nextInt(100);
        if (randomNumber > 25) {
            numberOfDifferentCertificates = 1;
        } else if (randomNumber > 5) {
            numberOfDifferentCertificates = 2;
        } else {
            numberOfDifferentCertificates = 3;
        }
        return numberOfDifferentCertificates;
    }

    private static int generateInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
