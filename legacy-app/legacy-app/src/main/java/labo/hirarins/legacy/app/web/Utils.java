package labo.hirarins.legacy.app.web;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private Utils() {

    }

    public static String convertToMD5String(String value) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return String.format("%040x", new BigInteger(1, md5.digest(value.getBytes())));
    }
}