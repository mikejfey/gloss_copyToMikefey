package com.applause.auto.core;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class GlossierConfig {

    public static String getCurrencySymbol(){
        return Currency.getInstance(identifyLocale()).getSymbol();
    }

    public static String getCountryIdentifier(){
        return identifyLocale().getCountry();
    }

    private static Locale identifyLocale(){
        return Arrays.stream(Locale.getAvailableLocales())
                .filter(locale -> locale.getCountry().equals(System.getProperty("locale"))).findFirst().get();
    }

    public static String getPreLivePassword(){
        return System.getProperty("envPass");
    }
}
