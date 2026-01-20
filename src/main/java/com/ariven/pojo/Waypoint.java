package com.ariven.pojo;

import lombok.Data;

@Data
public class Waypoint {
    private int waypointId;
    private String ident;
    private double lat;
    private double lon;
    private int airportId;
}
