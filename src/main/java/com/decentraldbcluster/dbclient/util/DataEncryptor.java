package com.decentraldbcluster.dbclient.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DataEncryptor {

    private static final byte[] KEY = Base64.getUrlDecoder().decode("4sLUBb8wsUTkWx6eof7WdFz9Phf22joOlzYJ_IbWgqq");
    private static final String ALGORITHM = "AES";
    private static final String DELIMITER = "𝄞";


    public static String[] decryptApiKey(String encryptedData) throws Exception {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encryptedData);
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes).split(DELIMITER);
    }
}
