package com.addteq.plugin.jira.webwork;

import com.addteq.plugin.pojo.RouteBean;
import com.addteq.plugin.service.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import java.util.List;

public class MyWebworkModuleAction extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(MyWebworkModuleAction.class);
    
     private DBManager dBManager ;

    public MyWebworkModuleAction(DBManager dBManager) {
        this.dBManager = dBManager;
    }
     
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    public List<RouteBean> getRoutes() {
        return dBManager.getAllRoutes();
    }
    public String doView() throws Exception {
        return SUCCESS;
    }
}
