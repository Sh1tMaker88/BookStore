package com.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleScanner {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleScanner() {
    }

    public static int scanInt() throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    public static String scanString() throws IOException {
        return reader.readLine();
    }

    public static Double scanDouble() throws IOException {
        return Double.parseDouble(reader.readLine());
    }

    public static Long scanLong() throws IOException {
        return Long.parseLong(reader.readLine());
    }
}
