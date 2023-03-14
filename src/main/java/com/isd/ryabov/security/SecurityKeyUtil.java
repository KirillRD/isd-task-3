package com.isd.ryabov.security;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityKeyUtil {

    private static final String KEY_GENERATOR_ALGORITHM = "HmacSHA256";
    private static final int KEY_SIZE = 256;

    private KeyGenerator keyGenerator;

    public SecurityKeyUtil() throws NoSuchAlgorithmException {
        initKeyGenerator();
    }

    private void initKeyGenerator() throws NoSuchAlgorithmException {
        this.keyGenerator = KeyGenerator.getInstance(KEY_GENERATOR_ALGORITHM);
        this.keyGenerator.init(KEY_SIZE, new SecureRandom());
    }

    public String generateKey() {
        return Hex.encodeHexString(keyGenerator.generateKey().getEncoded());
    }
}
