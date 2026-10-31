package org.example.db;

import java.sql.Connection;
import java.sql.Statement;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SchemaInitializer {

    public static void initialize(Connection connection) throws Exception {

        InputStream input = SchemaInitializer.class
                .getClassLoader()
                .getResourceAsStream("schema.sql");

        if (input == null) {
            throw new RuntimeException("schema.sql not found");
        }

        String sql = new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );

        try (Statement statement = connection.createStatement()) {

            for (String query : sql.split(";")) {
                if (!query.trim().isEmpty()) {
                    statement.execute(query);
                }
            }
        }

        System.out.println("Database schema initialized.");
    }
}