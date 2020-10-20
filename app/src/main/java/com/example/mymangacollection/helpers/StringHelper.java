package com.example.mymangacollection.helpers;

public class StringHelper {
    public static String integerToString (Integer value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    public static String floatToString (Float value) {
        if (value == null) {
            return "";
        }
        return String.format("%.2f", value).replace(".", ",");
    }

    public static Integer stringToInteger (String value) {
        try {
            if (value.isEmpty()) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Float stringToFloat (String value) {
        try {
            if (value.isEmpty()) {
                return null;
            }
            return Float.parseFloat(value.replace(",", "."));
        } catch (Exception ex) {
            return null;
        }
    }
}
