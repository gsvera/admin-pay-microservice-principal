package com.core.principal.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class GeneralUtils {
    public static String convertCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(amount);
    }
}
