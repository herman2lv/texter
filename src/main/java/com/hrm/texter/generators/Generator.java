package com.hrm.texter.generators;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private final String pattern;
    private MessageDigest messageDigest;
    private final Reader reader;
    private final Random random = new Random();
    private Long sequence;

    public Generator(String pattern, Reader reader) {
        this.pattern = pattern;
        this.reader = reader;
    }

    public List<String> getPhrases(int number) {
        List<String> phrases = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            phrases.add(getPhrase());
        }
        return phrases;
    }

    public String getPhrase() {
        String firstName = null;
        String lastName = null;
        StringBuilder phrase = new StringBuilder(pattern);
        int placeholderStart;
        while ((placeholderStart = phrase.indexOf("{")) != -1) {
            int placeholderEnd = phrase.indexOf("}");
            String commandFull = phrase.substring(placeholderStart + 1, placeholderEnd);
            String value;
            String command = getShortCommand(commandFull);
            switch (command) {
            case "email" -> {
                firstName = updateFirstName(firstName);
                lastName = updateLastName(lastName);
                value = getEmail(firstName, lastName);
            }
            case "first_name" -> {
                firstName = updateFirstName(firstName);
                value = firstName;
            }
            case "last_name" -> {
                lastName = updateLastName(lastName);
                value = lastName;
            }
            case "email_postfix" -> value = reader.getEmailPostfix();
            case "web_domain" -> value = reader.getWebDomain();
            case "noun" -> value = reader.getNoun();
            case "verb" -> value = reader.getVerb();
            case "adjective" -> value = reader.getAdjective();
            case "number" -> value = getNumber(commandFull);
            case "decimal" -> value = getDecimal(commandFull);
            case "sequence" -> value = getSequence(commandFull);
            case "hash" -> value = getHash();
            case "phone_number" -> value = getPhoneNumber();
            default -> throw new IllegalArgumentException("Illegal value {" + command + "}");
            }
            phrase.replace(placeholderStart, placeholderEnd + 1, value);
        }
        return phrase.toString();
    }

    private String getShortCommand(String command) {
        if (command.contains(":")) {
            return command.substring(0, command.indexOf(':'));
        }
        return command;
    }

    private String getPhoneNumber() {
        return String.format("+1 (%d) %d-%d", getNumber(random), getNumber(random), getNumber(random));
    }

    private String updateLastName(String lastName) {
        return lastName == null ? reader.getLastName() : lastName;
    }

    private String updateFirstName(String firstName) {
        return firstName == null ? reader.getFirstName() : firstName;
    }

    private String getEmail(String firstName, String lastName) {
        return firstName + "_" + lastName + "@" + reader.getEmailPostfix() + "." + reader.getWebDomain();
    }

    private String getSequence(String commandFull) {
        if (sequence == null) {
            sequence = Long.parseLong(getFirstParam(commandFull));
        }
        long number = sequence;
        String step = getLastParam(commandFull);
        sequence += Long.parseLong(step);
        return String.valueOf(number);
    }

    private String getNumber(String commandFull) {
        long min = Long.parseLong(getFirstParam(commandFull));
        long max = Long.parseLong(getLastParam(commandFull));
        return String.valueOf(random.nextLong(min, max));
    }

    private String getDecimal(String commandFull) {
        double min = Double.parseDouble(getFirstParam(commandFull));
        double max = Double.parseDouble(getLastParam(commandFull));
        return String.format("%.2f", random.nextDouble(min, max));
    }

    private String getLastParam(String commandFull) {
        return commandFull.substring(commandFull.lastIndexOf(':') + 1);
    }

    private String getFirstParam(String commandFull) {
        return commandFull.substring(commandFull.indexOf(':') + 1, commandFull.lastIndexOf(':'));
    }

    private String getHash() {
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        messageDigest.update(String.valueOf(random.nextLong(Long.MAX_VALUE)).getBytes());
        byte[] bytes = messageDigest.digest();
        messageDigest.reset();
        return new BigInteger(1, bytes).toString(16);
    }

    private int getNumber(Random random) {
        return random.nextInt(100, 1000);
    }

}
