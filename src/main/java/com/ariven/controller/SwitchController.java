package com.ariven.controller;

import io.github.mivek.exception.ParseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwitchController {
  private WeatherController weatherController = new WeatherController();
  private RouteController routeController = new RouteController();

  public String choice(String params) throws ParseException {
    if (params == null || params.isEmpty()) return "内容为空";

    String content = params;
    if (params.contains(": ")) {
      content = params.split(": ", 2)[1].trim();
    }

    String[] parts = content.split("\\s+", 2);
    String cmd = parts[0];
    String argument = parts.length > 1 ? parts[1] : "";

    switch (cmd) {
      case "/气象":
        log.info("气象查询成功，参数为: {}", argument);
        return weatherController.getWeatherDataByIcao(argument);
      case "/航路":
        log.info("开始航路查询，参数为: {}", argument);
        return routeController.getRoute(argument);
      case "/通波":
        log.info("开始通波查询，参数为: {}", argument);
        return weatherController.getAtis(argument);
      default:
        return "你发送了: " + content;
    }
  }
}
