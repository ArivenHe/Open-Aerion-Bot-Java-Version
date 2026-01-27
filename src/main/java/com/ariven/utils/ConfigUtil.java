package com.ariven.utils;

import com.ariven.pojo.Auth;
import com.ariven.pojo.Nav;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigUtil {
    private static final Properties properties = new Properties();
    @Getter
    private static final Auth auth = new Auth();

    @Getter
    private static final Nav nav=new Nav();

    static {
        // First try to load from external config file
        String externalConfigPath = "config/application.properties";
        try (InputStream input = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(externalConfigPath))) {
            properties.load(input);
            log.info("Loaded external configuration from {}", externalConfigPath);
        } catch (IOException e) {
            log.info("External configuration not found, falling back to classpath resource");
            // Fallback to classpath resource
            try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    log.error("Sorry, unable to find application.properties");
                } else {
                    properties.load(input);
                }
            } catch (IOException ex) {
                log.error("Error loading configuration", ex);
            }
        }
        
        auth.setAppId(properties.getProperty("app.id"));
        auth.setAppToken(properties.getProperty("app.token"));
        auth.setAppSecret(properties.getProperty("app.secret"));

        nav.setVersion(properties.getProperty("nav.version"));


        log.info("Configuration loaded successfully");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}