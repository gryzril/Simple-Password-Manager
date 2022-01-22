import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.lang.Thread;

public class index {

    GenerateKeyPair generator = new GenerateKeyPair();
    String privateKey = Base64.getEncoder().encodeToString(generator.getPrivateKey().getEncoded());
    String publicKey = Base64.getEncoder().encodeToString(generator.getPublicKey().getEncoded());

    public static void main(String args[]) throws Exception {

        inputLoop user_input_loop = new inputLoop();
        user_input_loop.start();
        System.out.println("Starting user input loop");

    }

    public void print_saved() {

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader("encrypted.txt"));
            while ((line = br.readLine()) != null) {
                // String[] items = line.split(splitBy);
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class inputInformation extends index {

        inputInformation(String location, String username, String password)
                throws InvalidKeyException, NoSuchAlgorithmException,
                BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
            String encryptedUsername = Base64.getEncoder().encodeToString(RSAUtil.encrypt(username, publicKey));
            String encryptedPassword = Base64.getEncoder().encodeToString(RSAUtil.encrypt(password, publicKey));
            
            //System.out.print("Encrypted username: ");
            //System.out.println(encryptedUsername);
            //System.out.print("Encrypted password: ");
            //System.out.println(encryptedPassword);

            // String decryptedUsername = RSAUtil.decrypt(encryptedUsername, privateKey);
            // String decryptedPassword = RSAUtil.decrypt(encryptedPassword, privateKey);
            // System.out.print("Decrypted username: ");
            // System.out.println(decryptedUsername);
            // System.out.print("Decrypted password: ");
            // System.out.println(decryptedPassword);

            String line = "";
            String splitBy = ",";
            Boolean found = false;
            try {

                BufferedReader br = new BufferedReader(new FileReader("encrypted.txt"));
                while ((line = br.readLine()) != null) {
                    String[] items = line.split(splitBy);
                    System.out.println(line);
                    if (location.equals(items[0])) {
                        System.out.print("Password already in system.");
                        found = true;
                    }
                }

                if (!found) {
                    FileWriter write = new FileWriter("encrypted.txt", true);
                    BufferedWriter bw = new BufferedWriter(write);
                    bw.newLine();
                    bw.write(location + "," + encryptedUsername + "," + encryptedPassword);
                    bw.close();
                    write.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class inputLoop extends Thread {
    Scanner scnr = new Scanner(System.in);

    public inputLoop() {
    }

    public void run() {
        while (true) {
            try {

                String location = scnr.nextLine();
                if (location.toLowerCase().equals("exit")) {
                    break;
                }
                String username = scnr.nextLine();
                String password = scnr.nextLine();
                index index_object = new index();
                index_object.new inputInformation(location, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

