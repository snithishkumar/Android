package co.in.mobilepay.service;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Nithish on 23-01-2016.
 */
public class ServiceUtil {

    public static  String netEncryption(String data)throws Exception{
        String alg = "AES";
        String key = "MySecondProjectMSecondPhoneNexus";
        return encryption(data,key,alg);
    }


    public static String netDecryption(String data) throws Exception{
        String alg = "AES";
        String key = "MySecondProjectMSecondPhoneNexus";
        return decryption(data, key, alg);

    }


    private static String encryption(String data,String key,String alg)throws Exception{
        Key key2  = new SecretKeySpec(key.getBytes(), alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, key2);
        byte[] encVal =  cipher.doFinal(data.getBytes());
        String encryptedValue  = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }


    private static String decryption(String data,String key,String alg) throws Exception{
        Key key2  = new SecretKeySpec(key.getBytes(), alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, key2);
        byte[] decryptedByte =  cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
        String result = new String(decryptedByte);
        return result;
    }


}
