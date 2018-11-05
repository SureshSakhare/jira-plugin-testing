package com.addteq.plugin.ao;
import net.java.ao.Entity;
import net.java.ao.OneToMany;
/**
 *
 * @author Suresh
 */
public interface Route extends Entity{
    
    public String getRouteName();

    public void setRouteName(String routeName);
    
    @OneToMany(reverse = "getRoute")
    public City[] getCities();
}
