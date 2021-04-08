package com.epam.esm.util.generator.dml.tableGenerators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemOrderGenerator {
    private static final Random RANDOM = new Random();
    private static final String INSERT_ITEM_ORDER = "INSERT INTO item_order "
            + "(total_cost, user_id, create_date, status_id) "
            + "VALUES (00.00, %d, '%s', %d);";
    private static final String UPDATE_TOTAL_COST = "UPDATE item_order SET total_cost = "
            + "(SELECT SUM(number_of_items * item_cost) AS total FROM order_details WHERE order_id = %d) "
            + "WHERE id = %d;";
    private static final int DELIVERED = 4;
    private static final int CONFIRMED = 2;
    private static final int CANCELLED = 3;
    private static final int PENDING = 1;

    public static List<String> generateInsertQueries(int requiredSize, int numberOfUsers) {
        List<Integer> userIds = generateUserIds(requiredSize, numberOfUsers);
        List<String> createDates = generateDateStrings(requiredSize, LocalDateTime.parse("2020-12-31T10:00:00"), LocalDateTime.now());
        List<Integer> statusIds = generateStatusIds(requiredSize);
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            queries.add(String.format(INSERT_ITEM_ORDER, userIds.get(i), createDates.get(i), statusIds.get(i)));
        }
        return queries;
    }

    public static List<String> generateTotalCostUpdateQueries(int numberOfOrders) {
        List<String> queries = new ArrayList<>();
        for (int i = 1; i <= numberOfOrders; i++) {
            queries.add(String.format(UPDATE_TOTAL_COST, i, i));
        }
        return queries;
    }

    private static List<Integer> generateStatusIds(int requiredSize) {
        List<Integer> statusIds = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            int randomNumber = RANDOM.nextInt(100);
            if (randomNumber > 25) {
                statusIds.add(DELIVERED);
            } else if (randomNumber > 10) {
                statusIds.add(CONFIRMED);
            } else if (randomNumber > 5) {
                statusIds.add(CANCELLED);
            } else {
                statusIds.add(PENDING);
            }
        }
        return statusIds;
    }

    private static List<Integer> generateUserIds(int requiredSize, int numberOfUsers) {
        List<Integer> userIds = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            userIds.add(generateInt(1, numberOfUsers));
        }
        return userIds;
    }

    private static List<String> generateDateStrings(int requiredSize, LocalDateTime minDate, LocalDateTime maxDate) {
        List<Instant> instants = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            instants.add(generateInstantDate(minDate, maxDate));
        }
        return instants.stream()
                .sorted()
                .map(Instant::toString)
                .map(ItemOrderGenerator::removeLastChar)
                .collect(Collectors.toList());
    }

    private static String removeLastChar(String string) {
        return new StringBuilder(string).deleteCharAt(string.length() - 1).toString();
    }

    private static Instant generateInstantDate(LocalDateTime minDate, LocalDateTime maxDate) {
        long min = minDate.toEpochSecond(ZoneOffset.UTC);
        long max = maxDate.toEpochSecond(ZoneOffset.UTC);
        long randomEpochSeconds = RANDOM.nextInt((int) (max + 1 - min)) + min;
        return Instant.ofEpochSecond(randomEpochSeconds);
    }

    private static int generateInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
