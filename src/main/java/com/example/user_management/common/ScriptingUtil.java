package com.example.user_management.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
public class ScriptingUtil {
    public static String encodeBase64(String plainText){
        String encodedString = Base64.getEncoder().encodeToString(plainText.getBytes());
        log.info("Encoded service id: {}",encodedString);
        return encodedString;
    }

    public static String decodeBase64(String base64String){
        String decodedString = new String(Base64.getDecoder().decode(base64String));
        log.info("decoded sevice id: {}",decodedString);
        return decodedString;
    }
}
