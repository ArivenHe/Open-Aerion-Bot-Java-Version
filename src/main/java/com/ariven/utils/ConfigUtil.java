package com.ariven.utils;

import com.ariven.pojo.Auth;
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

    static {
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                log.error("Sorry, unable to find application.properties");
            } else {
                properties.load(input);
                auth.setAppId(properties.getProperty("app.id"));
                auth.setAppToken(properties.getProperty("app.token"));
                auth.setAppSecret(properties.getProperty("app.secret"));
                log.info("Configuration loaded successfully");
            }
        } catch (IOException ex) {
            log.error("Error loading configuration", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}