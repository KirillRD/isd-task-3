package com.isd.ryabov.security;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HMACUtil {

    private static final HmacAlgorithms HMAC_ALGORITHM = HmacAlgorithms.HMAC_SHA_256;

    public String generateHMAC(String value, String key) {
        return new HmacUtils(HMAC_ALGORITHM, key).hmacHex(value);
    }
}
