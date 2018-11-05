package com.addteq.plugin.serviceImpl;

import com.addteq.plugin.ao.City;
import com.addteq.plugin.ao.Route;
import com.addteq.plugin.pojo.CityBean;
import com.addteq.plugin.pojo.RouteBean;
import com.addteq.plugin.service.DBManager;
import com.atlassian.activeobjects.external.ActiveObjects;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author suresh
 */
public class DBManagerImpl implements DBManager{
    
    private ActiveObjects ao;
    private Logger logger;

    public DBManagerImpl(ActiveObjects ao) {
        this.ao = ao;
    }
    
    public boolean addRout(RouteBean mRoute) {
        try{
        Route route = ao.create(Route.class);
        route.setRouteName(mRoute.getRoutName());
        route.save();
        return true;
        }catch(Exception e){
            logger.error("Unable to add rout due to" + e);
        }
        return false;
    }

    public boolean addCity(CityBean mcity) {
        try{
            Route[] route = ao.find(Route.class,"ID = ?",mcity.getRouteID());
            if(route.length<=0){
                logger.error("Route not configured with this city");
                return false;
            }else{
                City city = ao.create(City.class);
                city.setCity(mcity.getCityName());
                city.setRoute(route[0]);
                city.save();
                return true;
            }
        }catch(Exception exception){
            logger.error("Unable to add city due to" + exception);
        }
        return true;
    }

    public boolean removeRout(RouteBean mRoute) {
        try{
            Route[] route = ao.find(Route.class,"ID = ?", mRoute.getRoutID());
            if(route.length==0){
                logger.error("Unable to delete route with ID:"+mRoute.getRoutID());
                return false;
            }else{
                City[] cities = route[0].getCities();
                if(cities.length>0){
                ao.delete(cities);
                }
                ao.delete(route);
                ao.flushAll();
                return true;
            }
        }catch(Exception exception){
            logger.error("Unable to remove route due to"+exception);
        }
        return false;
    }

    public List<RouteBean> getAllRoutes() {
        List<RouteBean> routebeans = new ArrayList<RouteBean>();
        try{
            Route[] routes = ao.find(Route.class);
            if(routes.length>0){
                for (Route mRoute1 : routes) {
                    RouteBean mRoute = new RouteBean();
                    mRoute.setRoutID(mRoute1.getID());
                    mRoute.setRoutName(mRoute1.getRouteName());
                    routebeans.add(mRoute);
                }
                return routebeans;
            }
            return routebeans;
        }catch(Exception exception){
            logger.error("Unable to get All Routes due to :"+exception);
        }
        return routebeans;
    }

    public List<CityBean> getCitiesForRoute(RouteBean routeBean) {
        List<CityBean> cityBeans = new ArrayList<CityBean>();
        try{
            Route[] route = ao.find(Route.class,"ID = ? OR ROUTE_NAME = ?",routeBean.getRoutID(),routeBean.getRoutName());
            if(route.length>0){
                City[] mCities = route[0].getCities();
                if(mCities.length==0){
                    logger.error("No cities found for route:"+ routeBean.getRoutID());
                    return cityBeans;
                }
                for (City city : mCities) {
                    CityBean mCity = new CityBean();
                    mCity.setCityID(city.getID());
                    mCity.setCityName(city.getCity());
                    mCity.setRouteID(city.getRoute().getID());
                    cityBeans.add(mCity);
                }
                return cityBeans;
            }else{
                logger.error("No route found for ID:"+ routeBean.getRoutID()+ "And Route Name:"+routeBean.getRoutName());
            }
        }catch(Exception exception){
            logger.error("Unable to get Cities for route:"+routeBean.getRoutName()+" due to "+exception);
        }
        return cityBeans;
    }

    
}
