package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AccountRulesPropertiesLoader {

    private final Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String filePath) {
        load(filePath);
    }

    private void load(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                try (InputStream input = Files.newInputStream(path)) {
                    properties.load(input);
                }
                return;
            }

            try (InputStream input =
                         getClass().getClassLoader().getResourceAsStream(filePath)) {

                if (input == null) {
                    throw new RuntimeException(
                            "Properties file not found: " + filePath
                    );
                }

                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load properties file: " + filePath, e
            );
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}