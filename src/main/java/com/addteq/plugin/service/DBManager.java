package com.addteq.plugin.service;

import com.addteq.plugin.pojo.CityBean;
import com.addteq.plugin.pojo.RouteBean;
import com.atlassian.activeobjects.tx.Transactional;
import java.util.List;

/**
 *
 * @author suresh
 */
@Transactional
public interface DBManager {
    public boolean addRout(RouteBean route);
    public boolean addCity(CityBean city);
    public boolean removeRout(RouteBean route);
    public List<RouteBean> getAllRoutes();
    public List<CityBean> getCitiesForRoute(RouteBean routeBean);
}
