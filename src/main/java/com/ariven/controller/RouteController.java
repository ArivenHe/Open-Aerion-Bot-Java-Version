package com.ariven.controller;

import com.ariven.service.IFlightRouteService;
import com.ariven.service.impl.FlightRouteServiceImpl;
import com.ariven.utils.ConfigUtil;
import com.ariven.vo.FlightRouteVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class RouteController {
    private final IFlightRouteService flightRouteService = new FlightRouteServiceImpl();

    public String getRoute(String params) {
        String[] parts = params.split(" ");
        FlightRouteVO flightRouteVO = flightRouteService.findShortestPath(parts[0].toUpperCase(), parts[1].toUpperCase());
        String data =
                flightRouteVO.getStartICAO() + "✈" + flightRouteVO.getEndICAO() + "\n" +
                        "\n" +
                        flightRouteVO.getRouteString() + "\n" +
                        "全程共" + flightRouteVO.getTotalDistance() + " nm\n" +
                        "\n" +
                        "导航数据版本:" + ConfigUtil.getNav().getVersion();
        return data;
    }
}
