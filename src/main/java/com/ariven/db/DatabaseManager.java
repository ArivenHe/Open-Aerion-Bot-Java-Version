package com.ariven.db;

import com.ariven.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DatabaseManager {
    
    public static Connection getConnection() throws SQLException {
        String dbPath = ConfigUtil.getProperty("db.path");
        if (dbPath == null || dbPath.isEmpty()) {
            // Prioritize db/nav.sqlite as requested
            File targetDb = new File("db/nav.sqlite");
            if (targetDb.exists()) {
                dbPath = "db/nav.sqlite";
            } else {
                // Fallback to legacy paths for compatibility
                if (new File("little_navmap_navigraph.sqlite").exists()) {
                    dbPath = "little_navmap_navigraph.sqlite";
                } else {
                    dbPath = "src/main/resources/little_navmap_navigraph.sqlite";
                }
            }
        }
        
        String dbUrl = "jdbc:sqlite:" + dbPath;
        log.debug("Connecting to database: {}", dbUrl);

        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        return DriverManager.getConnection(dbUrl, config.toProperties());
    }
}
