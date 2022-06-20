package com.hrm.texter.data.impl;

import java.util.List;
import java.util.Random;

import com.hrm.texter.data.Repository;
import com.hrm.texter.data.ResourceReader;

public class RepositoryImpl implements Repository {
    private final ResourceReader reader;
    private List<String> nouns;
    private List<String> verbs;
    private List<String> adjectives;
    private List<String> lastNames;
    private List<String> firstNames;
    private List<String> emailPostfixes;
    private List<String> webDomains;
    private String help;
    private final Random random;

    public RepositoryImpl(ResourceReader reader, Random random) {
        this.reader = reader;
        this.random = random;
    }

    @Override
    public String getNoun() {
        if (nouns == null) {
            nouns = reader.readLines("/nouns.txt");
        }
        return nouns.get(random.nextInt(nouns.size()));
    }

    @Override
    public String getAdjective() {
        if (adjectives == null) {
            adjectives = reader.readLines("/adjectives.txt");
        }
        return adjectives.get(random.nextInt(adjectives.size()));
    }

    @Override
    public String getVerb() {
        if (verbs == null) {
            verbs = reader.readLines("/verbs.txt");
        }
        return verbs.get(random.nextInt(verbs.size()));
    }

    @Override
    public String getLastName() {
        if (lastNames == null) {
            lastNames = reader.readLines("/last_name.txt");
        }
        return lastNames.get(random.nextInt(lastNames.size()));
    }

    @Override
    public String getFirstName() {
        if (firstNames == null) {
            firstNames = reader.readLines("/first_name.txt");
        }
        return firstNames.get(random.nextInt(firstNames.size()));
    }

    @Override
    public String getEmailPostfix() {
        if (emailPostfixes == null) {
            emailPostfixes = reader.readLines("/email_postfix.txt");
        }
        return emailPostfixes.get(random.nextInt(emailPostfixes.size()));
    }

    @Override
    public String getWebDomain() {
        if (webDomains == null) {
            webDomains = reader.readLines("/web_domain.txt");
        }
        return webDomains.get(random.nextInt(webDomains.size()));
    }

    @Override
    public String getHelpShort() {
        if (help == null) {
            help = reader.read("/helpShort.txt");
        }
        return help;
    }

    @Override
    public String getHelpFull() {
        if (help == null) {
            help = reader.read("/helpFull.txt");
        }
        return help;
    }
}
