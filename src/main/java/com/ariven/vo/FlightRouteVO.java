package com.ariven.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightRouteVO {
    private String startICAO;
    private String endICAO;
    private double totalDistance;
    private String routeString;
    private boolean hasSID;
    private boolean hasSTAR;
    private String sidName;
    private String starName;
    private String sidInfo;
    private String starInfo;
}
