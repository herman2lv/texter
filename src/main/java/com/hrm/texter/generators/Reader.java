package com.hrm.texter.generators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class Reader {
    private final List<String> nouns;
    private final List<String> verbs;
    private final List<String> adjectives;
    private final List<String> lastNames;
    private final List<String> firstNames;
    private final List<String> emailPostfixes;
    private final List<String> webDomains;
    private final Random random;

    public Reader() {
        try {
            nouns = getResourceContent("/nouns.txt");
            verbs = getResourceContent("/verbs.txt");
            adjectives = getResourceContent("/adjectives.txt");
            lastNames = getResourceContent("/last_name.txt");
            firstNames = getResourceContent("/first_name.txt");
            emailPostfixes = getResourceContent("/email_postfix.txt");
            webDomains = getResourceContent("/email_domain.txt");
            random = new Random();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getResourceContent(String resourceName) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourceName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().toList();
        }
    }

    public String getNoun() {
        return nouns.get(random.nextInt(nouns.size()));
    }

    public String getAdjective() {
        return adjectives.get(random.nextInt(adjectives.size()));
    }

    public String getVerb() {
        return verbs.get(random.nextInt(verbs.size()));
    }

    public String getLastName() {
        return lastNames.get(random.nextInt(lastNames.size()));
    }

    public String getFirstName() {
        return firstNames.get(random.nextInt(firstNames.size()));
    }

    public String getEmailPostfix() {
        return emailPostfixes.get(random.nextInt(emailPostfixes.size()));
    }

    public String getWebDomain() {
        return webDomains.get(random.nextInt(webDomains.size()));
    }

    public String getEmail() {
        return getFirstName() + "_" + getLastName() + "@" + getEmailPostfix() + "." + getWebDomain();
    }

    public String getWord(String word) {
        return switch (word) {
        case "web_domain" -> getWebDomain();
        case "email_postfix" -> getEmailPostfix();
        case "last_name" -> getLastName();
        case "first_name" -> getFirstName();
        case "noun" -> getNoun();
        case "verb" -> getVerb();
        case "adjective" -> getAdjective();
        case "email" -> getEmail();
        default -> throw new IllegalArgumentException("Illegal value {" + word + "}");
        };
    }
}
