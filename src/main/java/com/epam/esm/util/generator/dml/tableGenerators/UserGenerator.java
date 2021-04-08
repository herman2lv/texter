package com.epam.esm.util.generator.dml.tableGenerators;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.epam.esm.util.generator.dml.GeneratorConstants.RESOURCES_DIRECTORY;

public class UserGenerator {
    private static final Random RANDOM = new Random();
    private static final String INSERT_USER = "INSERT INTO user (email, password, first_name, last_name) "
            + "VALUES ('%s', '%s', '%s', '%s');";
    private static final int HEX_RADIX = 16;
    private static final int POSITIVE_SIGN = 1;
    private static final String SHA_1 = "SHA-1";

    public static List<String> generateInsertQueries(int requiredSize) {
        List<String> firstNames = generateFirstNames(requiredSize);
        List<String> lastNames = generateLastNames(requiredSize);
        List<String> emails = generateEmails(requiredSize, firstNames, lastNames);
        List<String> passwords = generatePasswords(requiredSize, lastNames);
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            queries.add(String.format(INSERT_USER, emails.get(i), passwords.get(i),
                    firstNames.get(i), lastNames.get(i)));
        }
        return queries;
    }

    private static List<String> generateNames(int requiredSize, String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader namesReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + fileName)))) {
            List<String> rawNames = namesReader.lines().collect(Collectors.toList());
            for (int i = 0; i < requiredSize; i++) {
                names.add(getWord(rawNames));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    private static List<String> generateFirstNames(int requiredSize) {
        return generateNames(requiredSize, "first_name.txt");
    }

    private static List<String> generateLastNames(int requiredSize) {
        return generateNames(requiredSize, "last_name.txt");
    }

    private static List<String> generateEmails(int requiredSize, List<String> firstNames, List<String> lastNames) {
        List<String> emails = new ArrayList<>();
        try (BufferedReader postfixReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                RESOURCES_DIRECTORY + "email_postfix.txt")));
             BufferedReader domainReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                     RESOURCES_DIRECTORY + "email_domain.txt")))) {
            List<String> postfixes = postfixReader.lines().collect(Collectors.toList());
            List<String> domains = domainReader.lines().collect(Collectors.toList());
            for (int i = 0; i < requiredSize; i++) {
                emails.add(firstNames.get(i).toLowerCase() + "_" + lastNames.get(i).toLowerCase() + "@"
                        + getWord(postfixes) + "." + getWord(domains));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emails;
    }

    private static List<String> generatePasswords(int requiredSize, List<String> lastNames) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < requiredSize; i++) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(SHA_1);
                messageDigest.update(lastNames.get(i).getBytes(StandardCharsets.UTF_8));
                byte[] passwordEncryptedArray = messageDigest.digest();
                BigInteger passwordEncryptedNumber =
                        new BigInteger(POSITIVE_SIGN, passwordEncryptedArray);
                passwords.add(passwordEncryptedNumber.toString(HEX_RADIX));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return passwords;
    }

    private static String getWord(List<String> words) {
        return words.get(RANDOM.nextInt(words.size()));
    }
}
