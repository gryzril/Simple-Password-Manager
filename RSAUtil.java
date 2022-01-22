
/**
 * The following code is taken directly from a tutorial by Dhiraj Rai at the following URL:
 * https://www.devglan.com/java8/rsa-encryption-decryption-java
 * All credit goes to Dhiraj
 * The sourcecode GitHub: https://github.com/only2dhir/rsaencryption
 */

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCP7JTOR6rbmu05v1pS3G5ymbWTMxlwZEbZjjB5zEyuunNe1GtwU97ADVFMZ0DHwHTsQCXPFo7u/Z5VB9ijQ3o/q+kp4OzcC2vUGHaXpwT6Qj6vlgQDkF7Tyhy0ub8MDV07iI/zdO6wPJ86d5fzlAGu+HDQKaNttsY4TCqA+ffRnQIDAQAB";
    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI/slM5Hqtua7Tm/WlLcbnKZtZMzGXBkRtmOMHnMTK66c17Ua3BT3sANUUxnQMfAdOxAJc8Wju79nlUH2KNDej+r6Sng7NwLa9QYdpenBPpCPq+WBAOQXtPKHLS5vwwNXTuIj/N07rA8nzp3l/OUAa74cNApo222xjhMKoD599GdAgMBAAECgYBNsMbO507qxqHB53JpgkY4pJKoiA/2ZJr+O0wdo1PNiiZXDVGFzDBU+ZtmI+KLMW+sTCBcvm+fwNYkTlz6HKWzPN23WfHBkgELV6qqC1+kjacENxCscytxZpZwb0CjUwWQYiDiNgb7x9R/dxmPac+mDHXU5uiva5Sz4/tq8Q+kYQJBAPkDg22353Q/ekeclgJ31YeIMz2oxeceOXfAKP3fKzlOwS8BKY6MtGqH67euvVDP33F4SMGNuT5MoAM3vVhJ1+UCQQCT9kjzpNUvwRdftEGrbZnN0AnHYIxxvkAgtPdtZVktqjrf+InAG7wTvLMDFfNWjv539M6MhdKK/BHk2IS+iodZAkARsN41fUtERv44+DbwFfNlnZeGWec1dEBA8bX301MOzzmKTVrWdcibiOyGMDPi+eG1/gWtOpReiLMVh3A8RYLBAkAUWmGImlj0+7OyZ4JNCYOJqx9dPKu1Db2Wgi4y7ykIPg1hN7gDJA2IDYH2X7OcaYkZiSRMcjAXDDuN5jbEWxvpAkBZuWO7dtPxjSBK3QeOL5wFgodqRstHhHc5k2O0KAoV5Z9moij/5EO691nYSRML8muha7RlzIfYu7CfUj/7hHux";

    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }
    
    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException,
    NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("Dhiraj is the author", publicKey));
            System.out.println(encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
  
    }
}
