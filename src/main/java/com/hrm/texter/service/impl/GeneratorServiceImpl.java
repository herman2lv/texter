package com.hrm.texter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hrm.texter.data.Repository;
import com.hrm.texter.service.Encryptor;
import com.hrm.texter.service.GeneratorService;

public class GeneratorServiceImpl implements GeneratorService {
    private static final String F_POINT_FORMAT = "%.2f";
    private final Encryptor encryptor;
    private final Repository repository;
    private final Random random;
    private final List<Sequence> sequenceList = new ArrayList<>();

    public GeneratorServiceImpl(Repository repository, Encryptor encryptor, Random random) {
        this.repository = repository;
        this.encryptor = encryptor;
        this.random = random;
    }

    @Override
    public List<String> getPhrases(String pattern, int number) {
        List<String> phrases = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            phrases.add(getPhrase(pattern));
        }
        return phrases;
    }

    private String getPhrase(String pattern) {
        String firstName = null;
        String lastName = null;
        int currentSequence = 0;
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
            case "email_postfix" -> value = repository.getEmailPostfix();
            case "web_domain" -> value = repository.getWebDomain();
            case "noun" -> value = repository.getNoun();
            case "verb" -> value = repository.getVerb();
            case "adjective" -> value = repository.getAdjective();
            case "number" -> value = getNumber(commandFull);
            case "decimal" -> value = getDecimal(commandFull);
            case "sequence" -> value = getSequence(commandFull, ++currentSequence);
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
        return lastName == null ? repository.getLastName() : lastName;
    }

    private String updateFirstName(String firstName) {
        return firstName == null ? repository.getFirstName() : firstName;
    }

    private String getEmail(String firstName, String lastName) {
        return firstName + "_" + lastName + "@" + repository.getEmailPostfix() + "." + repository.getWebDomain();
    }

    private static class Sequence {
        private Long value;
        private final Long step;

        public Sequence(Long value, Long step) {
            super();
            this.value = value;
            this.step = step;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        public Long getStep() {
            return step;
        }

    }

    private String getSequence(String commandFull, int currentSequence) {
        long number;
        if (sequenceList.size() < currentSequence) {
            number = Long.parseLong(getFirstParam(commandFull));
            long step = Long.parseLong(getLastParam(commandFull));
            sequenceList.add(new Sequence(number, step));
        } else {
            Sequence current = sequenceList.get(currentSequence - 1);
            number = current.getValue() + current.getStep();
            current.setValue(number);
        }
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
        return String.format(F_POINT_FORMAT, random.nextDouble(min, max));
    }

    private String getLastParam(String commandFull) {
        return commandFull.substring(commandFull.lastIndexOf(':') + 1);
    }

    private String getFirstParam(String commandFull) {
        return commandFull.substring(commandFull.indexOf(':') + 1, commandFull.lastIndexOf(':'));
    }

    private String getHash() {
        String rawData = String.valueOf(random.nextLong(Long.MAX_VALUE));
        return encryptor.encrypt(rawData);
    }

    private int getNumber(Random random) {
        return random.nextInt(100, 1000);
    }

}
