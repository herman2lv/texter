package com.hrm.texter.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.hrm.texter.service.Encryptor;

public class EncryptorImpl implements Encryptor {
    private static final int POSTIVE_SIGN = 1;
    private static final int RADIX = 16;
    private static final String ALGORITHM = "SHA-1";
    private MessageDigest messageDigest;

    @Override
    public String encrypt(String data) {
        if (messageDigest == null) {
            init();
        }
        messageDigest.update(data.getBytes());
        byte[] bytes = messageDigest.digest();
        messageDigest.reset();
        return new BigInteger(POSTIVE_SIGN, bytes).toString(RADIX);
    }

    private void init() {
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
