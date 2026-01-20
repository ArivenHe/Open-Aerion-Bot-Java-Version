package com.ariven.controller;

import com.ariven.service.IWeatherService;
import com.ariven.service.impl.WeatherServiceImpl;
import com.ariven.vo.MetarVO;
import io.github.mivek.exception.ParseException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WeatherController {
    private final IWeatherService weatherService = new WeatherServiceImpl();

    public String getWeatherDataByIcao(String icao) throws ParseException {

        try {
            String code = weatherService.getMetar(icao);
            MetarVO metarVO = weatherService.formatMetar(code);
            if (metarVO!=null){
                String data = "\n机场: "+ metarVO.getAirportName() +" ("+ metarVO.getIcao() +")\n" +
                        "\n" +
                        "METAR: " + code +
                        "\n" +
                        "发布时间: " + metarVO.getTime() + "\n" +
                        "风况: " + metarVO.getWindDir() + "°" + metarVO.getWindSpeed() + metarVO.getWindUnit() + "\n" +
                        "能见度:  " + metarVO.getVisibility() + "\n" +
                        "修正海压: " + metarVO.getQnh() + metarVO.getQnhUnit() + "\n" +
                        "温度/露点: " + metarVO.getTemperature() + "℃\\" + metarVO.getDewPoint() + "℃\n";
                return data;
            }
            return "无数据或输入出错!";

        } catch (Exception e) {
            log.info("无数据或输入出错: {}",e.getMessage());
            return "无数据或输入出错!";
        }
    }

    public String getAtis(String icao) {
        try {
            return weatherService.getAtis(icao);
        } catch (Exception e) {
            log.error("Error generating ATIS", e);
            return "生成通波失败: " + e.getMessage();
        }
    }
}
