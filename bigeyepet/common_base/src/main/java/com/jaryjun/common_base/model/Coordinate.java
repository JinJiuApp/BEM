package com.jaryjun.common_base.model;

import java.io.Serializable;

/**
 * Created by Ted on 2015/5/13.
 */
public class Coordinate implements Serializable {

    private double lat;//纬度

    private double lon;//经度

    public Coordinate() {
    }

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isValidCoordinate() {
        return getLat() > 0 && getLon() > 0;
    }

}
