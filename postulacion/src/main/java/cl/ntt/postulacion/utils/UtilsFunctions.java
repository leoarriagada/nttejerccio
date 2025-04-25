package cl.ntt.postulacion.utils;

import cl.ntt.postulacion.response.ErrorResponse;

import org.springframework.http.HttpStatus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilsFunctions {
    private static HttpStatus globalStatus;

    public static String toSha256(String nttpost) {
        byte[] bytes = nttpost.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = md.digest(bytes);
        StringBuilder result = new StringBuilder();
        for (byte b : digest) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    public static void setStatus(HttpStatus status) {
        globalStatus = status;
    }

    private static final Logger log = LoggerFactory.getLogger(ErrorResponse.class);

}
