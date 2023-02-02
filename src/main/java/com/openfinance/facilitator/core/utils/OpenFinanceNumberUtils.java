package com.openfinance.facilitator.core.utils;

public class OpenFinanceNumberUtils {

    public static boolean isInteger(final String value){
        try {
            Integer.parseInt(value);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isDouble(final String value){
        try {
            Double.parseDouble(value);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
