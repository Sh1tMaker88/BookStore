package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleScanner {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger LOGGER = LogManager.getLogger(ConsoleScanner.class.getName());

    public ConsoleScanner() {
    }

    public static int scanInt() {
        Integer i = 0;
        try {
            i = Integer.parseInt(reader.readLine());
            return i;
        } catch (IOException e) {
            LOGGER.warn("Incorrect input for Integer", e);
        }
        return i;
    }

    public static String scanString() {
        String s = "";
        try {
            s = reader.readLine();
            return s;
        } catch (IOException e) {
            LOGGER.warn("Incorrect input for String", e);
        }
        return s;

    }

    public static Double scanDouble() {
        Double d = 0.0;
        try {
            d = Double.parseDouble(reader.readLine());
            return d;
        } catch (IOException e) {
            LOGGER.warn("Incorrect input for Double", e);
        }
        return d;

    }

    public static Long scanLong() {
        Long l = 0L;
        try {
            l = Long.parseLong(reader.readLine());
            return l;
        } catch (IOException e) {
            LOGGER.warn("Incorrect input for Long", e);
        }
        return l;
    }
}
