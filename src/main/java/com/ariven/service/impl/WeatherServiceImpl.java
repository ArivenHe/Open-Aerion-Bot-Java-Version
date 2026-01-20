package com.ariven.service.impl;

import com.ariven.service.IWeatherService;
import com.ariven.vo.MetarVO;
import io.github.mivek.exception.ParseException;
import io.github.mivek.internationalization.Messages;
import io.github.mivek.model.Metar;
import io.github.mivek.service.MetarService;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;

@Slf4j
public class WeatherServiceImpl implements IWeatherService {

    @Override
    public String getMetar(String icao) {
        String url = "https://metar.vatsim.net/" + icao;

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. 处理状态码并返回内容
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error: Unable to fetch METAR (Status Code: " + response.statusCode() + ")";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Request failed - " + e.getMessage();
        }
    }

    @Override
    public MetarVO formatMetar(String code) throws ParseException {
        try {
            MetarService metarService=MetarService.getInstance();
            Messages.getInstance().setLocale(Locale.ENGLISH);
            Metar metar= metarService.decode(code);

            MetarVO metarVO=MetarVO.builder()
                    .icao(metar.getAirport().getIcao())
                    .time(metar.getTime().toString())
                    .windDir(metar.getWind().getDirectionDegrees())
                    .windSpeed(metar.getWind().getSpeed())
                    .windUnit(metar.getWind().getUnit())
                    .visibility(metar.getVisibility().getMainVisibility())
                    .visibilityUnit("meters")
                    .temperature(metar.getTemperature())
                    .dewPoint(metar.getDewPoint())
                    .qnh(metar.getAltimeter())
                    .qnhUnit("hpa")
                    .build();

            return metarVO;
        } catch (Exception e) {
            log.info("无数据或输入出错: {}",e.getMessage());
        }
        return null;
    }
}