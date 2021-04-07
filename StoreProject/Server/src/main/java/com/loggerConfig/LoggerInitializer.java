package com.loggerConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggerInitializer {

    static {
        try (InputStream configuration = new FileInputStream("C:\\Users\\User\\IdeaProjects\\BookStore\\resources\\logger.properties")) {
            LogManager.getLogManager().readConfiguration(configuration);
        } catch (IOException e) {
            System.err.println("There is no file configuration" + e.toString());
        }
    }
}
