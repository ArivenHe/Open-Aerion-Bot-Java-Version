package com.ariven.service.impl;

import com.ariven.service.IOnlineService;
import com.ariven.utils.ConfigUtil;
import com.ariven.vo.OnlineVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class OnlineServiceImpl implements IOnlineService {
    private final HttpClient client;
    private final Gson gson;
    private final String onlineUrl;

    public OnlineServiceImpl() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
        this.onlineUrl = ConfigUtil.getProperty("online.url");
    }

    @Override
    public OnlineVO getOnlineStats() {
        if (onlineUrl == null) {
            log.error("Online URL not configured");
            throw new RuntimeException("Configuration error: online.url missing");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(onlineUrl))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                log.info("Result: {}", body);
                return gson.fromJson(body, OnlineVO.class);
            } else {
                log.error("Error fetching online data: {} {}", response.statusCode(), response.body());
                return null;
            }
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
