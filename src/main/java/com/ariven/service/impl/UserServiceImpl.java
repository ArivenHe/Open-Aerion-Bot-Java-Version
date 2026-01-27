package com.ariven.service.impl;

import com.ariven.service.IUserService;
import com.ariven.utils.ConfigUtil;
import com.ariven.vo.UserVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class UserServiceImpl implements IUserService {
    private final HttpClient client;
    private final Gson gson;
    private final String userUrl;

    public UserServiceImpl() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
        this.userUrl = ConfigUtil.getProperty("user.url");
    }

    @Override
    public UserVO getUserData(String callsign) {
        if (userUrl == null) {
            log.error("User URL not configured");
            throw new RuntimeException("Configuration error: user.url missing");
        }
        
        String fullUrl = userUrl + "?callsign=" + callsign;
        log.info("URL: {}", fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                log.info("Result: {}", body);
                return gson.fromJson(body, UserVO.class);
            } else {
                log.error("Error fetching user data: {} {}", response.statusCode(), response.body());
                return null;
            }
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
