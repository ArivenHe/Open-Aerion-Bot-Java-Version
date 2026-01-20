package com.ariven.pojo;

import lombok.Data;

@Data
public class AirwaySegment {
    private int airwayId;
    private String name;
    private int fromWaypointId;
    private int toWaypointId;
}
