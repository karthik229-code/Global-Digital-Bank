package org.example.db;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("config/rules/persistence.properties")) {

            if (input == null) {
                throw new RuntimeException("persistence.properties not found");
            }

            PROPERTIES.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static String getDatabaseUrl() {
        return PROPERTIES.getProperty("persistence.db.url");
    }

    public static String getDriver() {
        return PROPERTIES.getProperty("persistence.db.driver");
    }

    public static String getPersistenceMode() {
        return PROPERTIES.getProperty("persistence.mode", "memory");
    }
}