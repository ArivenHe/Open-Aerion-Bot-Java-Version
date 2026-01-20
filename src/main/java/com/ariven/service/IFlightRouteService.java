package com.ariven.service;

import com.ariven.vo.FlightRouteVO;

public interface IFlightRouteService {
    FlightRouteVO findShortestPath(String startICAO, String endICAO);
}
