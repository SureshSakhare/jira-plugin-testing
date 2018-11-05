package com.addteq.plugin.pojo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author suresh
 */
public class CityBean {
    @JsonProperty
    private String cityName;
    @JsonProperty
    private int cityID;
    @JsonProperty
    private int routeID;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    
    
}
