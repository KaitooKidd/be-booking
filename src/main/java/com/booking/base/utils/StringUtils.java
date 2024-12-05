package com.booking.base.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang3.math.NumberUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String removeSpecialChar(String str) {
        if (str != null) {
            return str.replaceAll("[^a-zA-Z0-9_]", "");
        }
        return "";
    }

    public static String generatePassword() {
        var characters = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ-@#$%.[]{}()=*^></?:;!~";
        StringBuilder generatePassword = new StringBuilder();
        for (var j = 0; j < 20; j++) {
            generatePassword.append(characters.charAt((int) Math.floor(Math.random() * characters.length())));
        }
        System.out.println(generatePassword);
        return generatePassword.toString();
    }

    public static String getEmailName(String email) {
        return email.split("@")[0];
    }

    public static String formatNumberForPlacementName(String str) {
        if (!isExist(str)) {
            return str;
        }
        DecimalFormat df = new DecimalFormat("0.###");
        return df.format(Double.valueOf(str));
    }

    public static boolean isNumber(String str) {
        return NumberUtils.isCreatable(str);
    }

    public static HashMap<String, String> splitURL(String url) {
        HashMap<String, String> urlMap = new HashMap<>();
        String queryString = substringAfter(url, "?");
        for (String param : queryString.split("&")) {
            urlMap.put(substringBefore(param, "="), substringAfter(param, "="));
        }
        return urlMap;
    }

    public static boolean looseCompare(String u, String v) {
        if ((u == null || u.isEmpty()) && (v == null || v.isEmpty())) {
            return true;
        }
        return Objects.equals(u, v);
    }

    public static boolean compareObject(Object u, Object v) {
        return (u == null && v == null) || (u != null && u.equals(v));
    }

    public static boolean isExist(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
