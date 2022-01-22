// https://www.devglan.com/java8/rsa-encryption-decryption-java

import java.io.IOException;
import java.security.*;
import java.util.Base64;


public class GenerateKeyPair {
    
    private PrivateKey priv;
    private PublicKey pub;
    static final int keysize = 1024;

    // Constructor
    public GenerateKeyPair() {
        
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(keysize);
            KeyPair k_pair = keyGen.generateKeyPair();
            this.priv = k_pair.getPrivate();
            this.pub = k_pair.getPublic();
        } catch(Exception e) {
            System.out.println("Error creating keyGen");
        }
    }

    public PrivateKey getPrivateKey() {
        return priv;
    }
    
    public PublicKey getPublicKey() {
        return pub;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        GenerateKeyPair generator = new GenerateKeyPair();
        System.out.println(Base64.getEncoder().encodeToString(generator.getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(generator.getPrivateKey().getEncoded()));
        
    }

}
