package com.epam.esm.util.generator.dml.tableGenerators;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.epam.esm.util.generator.dml.GeneratorConstants.RESOURCES_DIRECTORY;

public class GiftCertificateGenerator {
    private static final Random RANDOM = new Random();
    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate "
            + "(name, description, price, duration, create_date, last_update_date) "
            + "VALUES ('%s', '%s', %.2f, %d, '%s', '%s');";

    public static List<String> generateInsertQueries(int requiredSize) {
        List<String> names = generateGiftCertificateNames(requiredSize);
        List<String> descriptions = generateDescriptions(requiredSize);
        List<BigDecimal> prices = generateDecimalNumbers(requiredSize, 30, 1000, 2);
        List<Integer> durations = generateIntegers(requiredSize, 15, 180, 5);
        List<String> dates =
                generateDateStrings(requiredSize, LocalDateTime.parse("2020-12-31T10:00:00"), LocalDateTime.now());
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            queries.add(String.format(INSERT_GIFT_CERTIFICATE, names.get(i), descriptions.get(i), prices.get(i),
                    durations.get(i), dates.get(i), dates.get(i)));
        }
        return queries;
    }

    private static List<String> generateDateStrings(int requiredSize, LocalDateTime minDate, LocalDateTime maxDate) {
        List<Instant> instants = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            instants.add(generateInstantDate(minDate, maxDate));
        }
        return instants.stream()
                .sorted()
                .map(Instant::toString)
                .map(GiftCertificateGenerator::removeLastChar)
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


    private static List<BigDecimal> generateDecimalNumbers(int requiredSize, int min, int max, int scale) {
        List<BigDecimal> decimals = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            decimals.add(generateDecimalNumber(min, max, scale));
        }
        return decimals;
    }

    private static List<Integer> generateIntegers(int requiredSize, int min, int max, int divisor) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            integers.add(generateInt(min, max, divisor));
        }
        return integers;
    }

    private static List<String> generateGiftCertificateNames(int requiredSize) {
        List<String> giftCertificateNames = new ArrayList<>();
        try (BufferedReader nounsReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + "nouns.txt")));
             BufferedReader adjectivesReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES_DIRECTORY + "adjectives.txt")))) {
            List<String> nouns = nounsReader.lines().collect(Collectors.toList());
            List<String> adjectives = adjectivesReader.lines().collect(Collectors.toList());
            for (int i = 0; i < requiredSize; i++) {
                giftCertificateNames.add(capitalizeFirstLetter(getWord(adjectives)) + " "
                        + capitalizeFirstLetter(getWord(nouns)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return giftCertificateNames;
    }

    private static List<String> generateDescriptions(int requiredSize) {
        List<String> descriptions = new ArrayList<>();
        try (BufferedReader nounsReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + "nouns.txt")));
             BufferedReader adjectivesReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES_DIRECTORY + "adjectives.txt")));
             BufferedReader verbsReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES_DIRECTORY + "verbs.txt")))
        ) {
            List<String> nouns = nounsReader.lines().collect(Collectors.toList());
            List<String> adjectives = adjectivesReader.lines().collect(Collectors.toList());
            List<String> verbs = verbsReader.lines().collect(Collectors.toList());
            for (int i = 0; i < requiredSize; i++) {
                descriptions.add(capitalizeFirstLetter(getWord(verbs)) + " "
                        + getWord(adjectives) + " " + getWord(nouns) + " and "
                        + getWord(verbs) + " " + getWord(nouns));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return descriptions;
    }

    private static String getWord(List<String> words) {
        return words.get(RANDOM.nextInt(words.size()));
    }

    private static BigDecimal generateDecimalNumber(int min, int max, int scale) {
        return BigDecimal.valueOf(RANDOM.nextDouble() * (max - min) + min).setScale(scale, RoundingMode.HALF_UP);
    }

    private static int generateInt(int min, int max, int divisor) {
        int randomInt = RANDOM.nextInt(max - min + 1) + min;
        return randomInt - randomInt % divisor;
    }

    private static String capitalizeFirstLetter(String word) {
        char firstLetter = word.charAt(0);
        return word.replaceFirst(String.valueOf(firstLetter), String.valueOf(firstLetter).toUpperCase());
    }
}
