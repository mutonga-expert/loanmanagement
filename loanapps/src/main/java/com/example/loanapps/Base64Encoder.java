package com.example.loanapps;

import java.util.Base64;

public class Base64Encoder {
    public static void main(String[] args) {
        String secret = "YourSuperSecureSecretKey"; // Change this to a strong key
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println("Base64 Encoded Secret: " + encodedSecret);
    }
}
