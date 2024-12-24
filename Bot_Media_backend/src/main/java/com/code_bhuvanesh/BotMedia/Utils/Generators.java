package com.code_bhuvanesh.BotMedia.Utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Generators {
    // Define the characters that can be used to generate the random string
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";

    public static String generateRandomFileName(String username, String filename){

        String fileExtension = filename.substring(filename.lastIndexOf("."));

        String combinedInput = username + filename + getCurrentTimestamp();

        // Initialize SecureRandom for better randomness
        SecureRandom random = new SecureRandom();

        // StringBuilder to hold the result
        StringBuilder randomString = new StringBuilder();

        // Loop to generate a 256-character long string
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(combinedInput.length());
            char randomChar = combinedInput.charAt(index);
            randomString.append(randomChar);
        }

        randomString.append(fileExtension);

        // Return the generated string
        return randomString.toString();
    }

    // Helper method to get the current timestamp
    private static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // Format: YYYYMMDDHHMMSSSSS
        return sdf.format(new Date());
    }
}
