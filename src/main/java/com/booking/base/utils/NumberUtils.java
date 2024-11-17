package com.booking.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {
    //    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.####");
    public static final DecimalFormat TWO_POINT_DECIMAL_FORMAT = new DecimalFormat("0.00");

    public static String round(double number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.toString();
    }

    public static boolean compareAsDouble(String n1, String n2) {
        return Double.valueOf(n1).equals(Double.valueOf(n2));
    }

    public static double roundToTwoDecimalPoints(double val) {
        try {
            String value = TWO_POINT_DECIMAL_FORMAT.format(val);
            return Double.parseDouble(value);
        } catch (Exception e) {
            return val;
        }
    }

    public static String convert2TwoDecimalPoints(String number) {
        try {
            return TWO_POINT_DECIMAL_FORMAT.format(Double.parseDouble(number));
        } catch (Exception e) {
            return number;
        }
    }
}
