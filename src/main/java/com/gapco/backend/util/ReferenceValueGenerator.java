package com.gapco.backend.util;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;
@Service
public class ReferenceValueGenerator {

    private static final int LENGTH = 8;
//    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHARACTERS = "0123456789";
    private static final Random random = new SecureRandom();

    public static String generate() {
        StringBuilder referenceValue = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            referenceValue.append(CHARACTERS.charAt(index));
        }
        return referenceValue.toString();
    }
}
