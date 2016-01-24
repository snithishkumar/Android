package co.in.mobilepay.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Nithish on 23-01-2016.
 */
public interface PasswordHash {

    String createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String createHash(char[] password)  throws NoSuchAlgorithmException, InvalidKeySpecException;


      boolean validatePassword(String password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException;


    boolean validatePassword(char[] password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException;
}
