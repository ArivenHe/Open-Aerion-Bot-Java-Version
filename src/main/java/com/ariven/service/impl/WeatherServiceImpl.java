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

import io.github.mivek.model.Cloud;
import io.github.mivek.enums.CloudType;
import io.github.mivek.enums.CloudQuantity;
import java.util.List;

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
                    .airportName(metar.getAirport().getName())
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

    private final String[] PHONETIC_ALPHABET = {
            "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel",
            "India", "Juliet", "Kilo", "Lima", "Mike", "November", "Oscar", "Papa",
            "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey",
            "X-ray", "Yankee", "Zulu"
    };

    @Override
    public String getAtis(String icao) throws ParseException {
        String code = getMetar(icao);
        if (code.startsWith("Error")) {
            return "获取METAR失败: " + code;
        }

        MetarService metarService = MetarService.getInstance();
        Messages.getInstance().setLocale(Locale.ENGLISH);
        Metar metar = metarService.decode(code);

        int hour = metar.getTime().getHour();
        String atisCode = PHONETIC_ALPHABET[hour % 26];

        String airportName = metar.getAirport().getName();
        if (airportName == null) airportName = icao;

        String timeStr = String.format("%02d%02d", metar.getTime().getHour(), metar.getTime().getMinute());


        String windDirCN, windDirEN;
        Integer dir = metar.getWind().getDirectionDegrees();
        if (dir == null) {
            windDirCN = "VRB";
            windDirEN = "VRB";
        } else {
            windDirCN = String.format("%03d", dir);
            windDirEN = String.format("%03d", dir);
        }

        int speed = metar.getWind().getSpeed();
        String unit = metar.getWind().getUnit();
        String speedStr = String.format("%02d", speed);


        String visCN, visEN;
        String cloudCN = "", cloudEN = "";
        
        if (metar.isCavok()) {
            visCN = "CAVOK";
            visEN = "CAVOK";
        } else {
            String visMain = metar.getVisibility().getMainVisibility();
            visCN = visMain;
            visEN = visMain;
            

            List<Cloud> clouds = metar.getClouds();
            if (clouds != null && !clouds.isEmpty()) {
                StringBuilder cCn = new StringBuilder();
                StringBuilder cEn = new StringBuilder();
                
                for (Cloud cloud : clouds) {
                    CloudQuantity quantity = cloud.getQuantity();
                    int height = cloud.getHeight();
                    CloudType type = cloud.getType();
                    
                    String qCn = "";
                    String qEn = "";
                    
                    if (quantity != null) {
                        switch (quantity) {
                            case FEW: qCn = "少云"; qEn = "Few"; break;
                            case SCT: qCn = "疏云"; qEn = "Scattered"; break;
                            case BKN: qCn = "多云"; qEn = "Broken"; break;
                            case OVC: qCn = "阴天"; qEn = "Overcast"; break;
                            case SKC: qCn = "天空晴朗"; qEn = "Sky Clear"; break;
                            case NSC: qCn = "无明显云"; qEn = "No Significant Clouds"; break;
                        }
                    }
                    
                    String typeCn = "";
                    String typeEn = "";
                    if (type != null) {
                        switch (type) {
                            case CB: typeCn = "积雨云"; typeEn = "Cumulonimbus"; break;
                            case TCU: typeCn = "塔状积云"; typeEn = "Towering Cumulus"; break;
                        }
                    }

                    if (quantity == CloudQuantity.SKC || quantity == CloudQuantity.NSC) {
                         cCn.append(qCn).append("，");
                         cEn.append(qEn).append(", ");
                    } else {

                         cCn.append(qCn).append(" ").append(height).append(" 英尺");
                         if (!typeCn.isEmpty()) cCn.append(" ").append(typeCn);
                         cCn.append("，");


                         cEn.append(qEn).append(" ").append(height).append(" feet");
                         if (!typeEn.isEmpty()) cEn.append(" ").append(typeEn);
                         cEn.append(", ");
                    }
                }
                cloudCN = cCn.toString();
                cloudEN = cEn.toString();
            }
        }

        int temp = metar.getTemperature();
        int dew = metar.getDewPoint();
        String tempCN = formatTempCN(temp);
        String dewCN = formatTempCN(dew);
        String tempEN = formatTempEN(temp);
        String dewEN = formatTempEN(dew);

        int qnh = metar.getAltimeter();
        String qnhUnit = "hPa";

        StringBuilder sb = new StringBuilder();

        sb.append(airportName).append("情报通播 ").append(atisCode).append("，")
                .append(timeStr).append(" 协调世界时，")
                .append("地面风向 ").append(windDirCN).append(" 度，")
                .append("风速 ").append(speedStr).append(" ").append(unit).append("，");

        if (!metar.isCavok()) {
            sb.append("能见度 ").append(visCN).append("，");
            if (!cloudCN.isEmpty()) {
                sb.append(cloudCN);
            }
        } else {
            sb.append("CAVOK，");
        }

        sb.append("温度 ").append(tempCN).append(" 摄氏度，")
                .append("露点 ").append(dewCN).append(" 摄氏度，")
                .append("修正海压 ").append(qnh).append(" ").append(qnhUnit).append("，")
                .append("首次与管制员联络时报告你已收到通波 ").append(atisCode).append("。");

        sb.append("\n\n");

        sb.append(airportName).append(" information ").append(atisCode).append(", ")
                .append(timeStr).append(" UTC, ")
                .append("wind ").append(windDirEN).append(" degrees at ").append(speedStr).append(" ").append(unit).append(", ");

        if (!metar.isCavok()) {
            sb.append("visibility ").append(visEN).append(", ");
            if (!cloudEN.isEmpty()) {
                sb.append(cloudEN);
            }
        } else {
            sb.append("CAVOK, ");
        }

        sb.append("temperature ").append(tempEN).append(" degree Celsius, ")
                .append("dew point ").append(dewEN).append(" degree Celsius, ")
                .append("corrected altimeter setting ").append(qnh).append(" ").append(qnhUnit).append(", ")
                .append("advise on initial contact you have information ").append(atisCode).append(".");

        return sb.toString();
    }

    private String formatTempCN(int t) {
        if (t < 0) return "零下 " + Math.abs(t);
        return String.valueOf(t);
    }

    private String formatTempEN(int t) {
        if (t < 0) return "minus " + Math.abs(t);
        return String.valueOf(t);
    }
}