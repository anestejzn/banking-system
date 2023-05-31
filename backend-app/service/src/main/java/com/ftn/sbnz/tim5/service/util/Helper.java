package com.ftn.sbnz.tim5.service.util;


import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.ReportType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class Helper {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static boolean passwordsDontMatch(String password, String confirmationPassword){

        return !password.equals(confirmationPassword);
    }

    public static String getHash(String password) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static int generateSecurityCode() {
        return (int)(Math.random() * (Constants.MAX_SECURITY_NUM - Constants.MIN_SECURITY_NUM + 1) + Constants.MIN_SECURITY_NUM);
    }

    public static long generateRandomNumber(long minNum, long maxNum) {
        return (long)(Math.random() * (maxNum - minNum + 1) + minNum);
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String generateHashForURL(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String base64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return base64Hash;
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
            return null;
        }
    }

    public static DebitType getTypeFromReport(ReportType reportType) {
        switch (reportType) {
            case CASH_CREDIT_REPORT:
                return DebitType.CASH_CREDIT;
            case OVERDRAFT_REPORT:
                return DebitType.OVERDRAFT;
        }

        return null;
    }

}
