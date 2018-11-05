package com.addteq.plugin.ao;

import net.java.ao.Entity;

/**
 *
 * @author suresh
 */
public interface City extends Entity{
     
    public String getCity();

    public void setCity(String city);
    
    public Route getRoute();

    public void setRoute(Route Route);
}
