package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionProvider implements ConnectionProvider {

    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    DatabaseConfig.getDatabaseUrl()
            );
        }

        return connection;
    }

    @Override
    public void shutdown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public String getProviderName() {
        return "SQLite JDBC";
    }
}