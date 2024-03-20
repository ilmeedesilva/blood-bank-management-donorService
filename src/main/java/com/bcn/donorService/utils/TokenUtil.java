package com.bcn.donorService.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class TokenUtil {

    private static final String SECRET_KEY = "2c788bf1-4b9d-4daf-8206-64f4703e656d";

    public static String decryptTokenAndGetRole(String token) {
        String cleanedToken = token.replace("Bearer ", "");
        String[] chunks = cleanedToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        JsonObject payloadJson = JsonParser.parseString(payload).getAsJsonObject();

        return payloadJson.get("role").getAsString();
    }


    private static SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}