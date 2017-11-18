package com.atmavedagana.shivoham.shivoham.utils;

/**
 * Created by shiv on 11/14/2017.
 */

public class TimeHelper {
    public static String convertMsToMMSS(long timeInMs) {
        long timeInSecs = timeInMs / 100;
        int secs = (int)timeInSecs % 60;
        int mins = (int)java.lang.Math.floor((timeInSecs/ 60.0));

        return String.format("%02d", mins) + ":" + String.format("%02d", secs);
    }
}
