package com.ariven.db;

import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/little_navmap_navigraph.sqlite";

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        return DriverManager.getConnection(DB_URL, config.toProperties());
    }
}
