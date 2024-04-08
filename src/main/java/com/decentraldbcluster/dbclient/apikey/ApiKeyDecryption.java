package com.decentraldbcluster.dbclient.apikey;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class ApiKeyDecryption {

    private static final byte[] KEY = Base64.getUrlDecoder().decode("4sLUBb8wsUTkWx6eof7WdFz9Phf22joOlzYJ_IbWgqq");

    private static final String ALGORITHM = "AES";
    private static final String DELIMITER = "𝄞";

    public static String[] decryptApiKey(String encryptedApiKey) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(encryptedApiKey);
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData).split(DELIMITER);
        } catch (Exception e) {
            throw new RuntimeException("Invalid api key");
        }
    }
}

