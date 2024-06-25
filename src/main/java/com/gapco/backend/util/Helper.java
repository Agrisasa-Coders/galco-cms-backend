package com.gapco.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static LocalDateTime getCurrentTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now(ZoneId.of(AppConstants.TIMEZONE));
        String Temporal= formatter.format(now);
        String datetime = LocalDate.now().format(DateTimeFormatter.ofPattern(Temporal));

        LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);

        return localDateTime;

    }

    public static String getUploadedPath(String filePath){

        String uploadsPath1 = "/uploads";   //for linux
        String uploadsPath2 = "\\uploads";  //for windows

        int startIndex = filePath.indexOf(uploadsPath1);

        String result;

        if (startIndex == -1) {
            startIndex = filePath.indexOf(uploadsPath2);
        }
        result = filePath.substring(startIndex);

        return result;
    };



    public static String getDateInString(LocalDateTime localDateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = localDateTime.format(formatter);

        return formattedDate;
    }

    public static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic "+ Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public static Map<String,String> getCommonHeaders(String authorization){
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Content-Type","application/json");
        headers.put("Authorization", authorization);
        return headers;
    };

    public static LocalDate getTodayDate(){
        return LocalDate.now(ZoneId.of(AppConstants.TIMEZONE));
    }

    public static String getRandomString(int n)
    {

        //int n = 8;
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String generateEntryId(String collectionPointName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(collectionPointName.substring(0,3))
                .append("_ENTRY_")
                .append(getRandomString(8));

        return stringBuilder.toString();

    }

    public static String generateOTP(int otpLength)
    {

        int n = otpLength;
        // choose a Character random from this String
        String numericString = "0123456789";

        // create StringBuffer size of numericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to numericString variable length
            int index
                    = (int)(numericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(numericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
