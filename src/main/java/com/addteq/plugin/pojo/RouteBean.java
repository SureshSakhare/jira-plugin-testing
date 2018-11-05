package com.addteq.plugin.pojo;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Suresh
 */
public class RouteBean {

    @JsonProperty
    private String routName;
    @JsonProperty
    private int routID;
    @JsonProperty
    private List<CityBean> cities;

    public String getRoutName() {
        return routName;
    }

    public void setRoutName(String routName) {
        this.routName = routName;
    }

    public int getRoutID() {
        return routID;
    }

    public void setRoutID(int routID) {
        this.routID = routID;
    }

    public List<CityBean> getCities() {
        return cities;
    }

    public void setCities(List<CityBean> cities) {
        this.cities = cities;
    }


}
