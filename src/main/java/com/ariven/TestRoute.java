package com.ariven;

import com.ariven.service.IFlightRouteService;
import com.ariven.service.impl.FlightRouteServiceImpl;
import com.ariven.vo.FlightRouteVO;

public class TestRoute {
    public static void main(String[] args) {
        IFlightRouteService service = new FlightRouteServiceImpl();
        System.out.println("Finding path from ZBAA to ZSSS...");
        FlightRouteVO result = service.findShortestPath("ZBAA", "ZSSS");
        System.out.println(result);
        
        System.out.println("Finding path from KJFK to EGLL...");
        result = service.findShortestPath("KJFK", "EGLL");
        System.out.println(result);
    }
}
