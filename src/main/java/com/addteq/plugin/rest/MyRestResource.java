package com.addteq.plugin.rest;

import com.addteq.plugin.pojo.CityBean;
import com.addteq.plugin.pojo.RouteBean;
import com.addteq.plugin.service.DBManager;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A resource of message.
 */
@Path("/configuration")
@Produces({MediaType.APPLICATION_JSON})
public class MyRestResource {

    private DBManager dBManager;

    public MyRestResource(DBManager dBManager) {
        this.dBManager = dBManager;
    }
    @GET
    @Path("/route/{routeID}")
    public Response getCitiesForRoute(@PathParam("routeID") String routeID){
        RouteBean routeBean = new RouteBean();
        routeBean.setRoutID(Integer.parseInt(routeID));
        return Response.ok(dBManager.getCitiesForRoute(routeBean)).build();
    }
    
    @GET
    @Path("/routes")
    public Response getAllRoutes(){
        return Response.ok(dBManager.getAllRoutes()).build();
    }
    @POST
    @Path("/route")
    public Response addRoute(RouteBean routeBean){
        if(dBManager.addRout(routeBean))
        return Response.ok().build();
        return Response.status(Response.Status.FORBIDDEN).build();
    }
    @POST
    @Path("/city")
    public Response addCity(CityBean cityBean){
        if(dBManager.addCity(cityBean))
        return Response.ok().build();
        return Response.status(Response.Status.FORBIDDEN).build();
    }

}