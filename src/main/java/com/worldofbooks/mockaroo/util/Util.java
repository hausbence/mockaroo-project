package com.worldofbooks.mockaroo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    /**
     * Rounds a double value to @param places decimals
     * @param value the number we want to round
     * @param places the number of decimals
     * @return double
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
