/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addteq.plugin.serviceImpl;

import com.addteq.plugin.pojo.CityBean;
import com.addteq.plugin.pojo.RouteBean;
import com.addteq.plugin.service.AutomationHandler;
import com.addteq.plugin.service.DBManager;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author addteq
 */
public class AutomationHandlerImpl implements AutomationHandler{
    
    private static final Long PROJECT_ID = 10202l;
    private static final String EPIC_TYPE_ID = "10203";
    private static final String STORY_TYPE_ID = "10204";
    private static final Long EPIC_NAME_ID = 10203l;
    private static final Long EPIC_LINK_ID = 10201l;
    private static final Long ROUTE_ID = 10200l;
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AutomationHandlerImpl.class);
    
    private final DBManager dBManager;
    private final IssueService issueService;
    private ApplicationUser loggedInUser = null;

    public AutomationHandlerImpl(DBManager dBManager, IssueService issueService) {
        this.dBManager = dBManager;
        this.issueService = issueService;
    }


    @Override
    public void createEpicAndStories(Issue issue) {
        CustomFieldManager cfManager = ComponentAccessor.getCustomFieldManager();
        String routeName = issue.getCustomFieldValue(cfManager.getCustomFieldObject(ROUTE_ID)).toString();
        MutableIssue newEpic = null;
        RouteBean routeBean = new RouteBean();
        routeBean.setRoutName(routeName);
        
        loggedInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
        issueInputParameters.setSummary("New York to "+routeName);
        issueInputParameters.setIssueTypeId(EPIC_TYPE_ID);
        issueInputParameters.setProjectId(PROJECT_ID);
        issueInputParameters.setReporterId(loggedInUser.getName());
        issueInputParameters.addCustomFieldValue(EPIC_NAME_ID, routeName);  //epic name
        
        IssueService.CreateValidationResult result = issueService
                    .validateCreate(loggedInUser, issueInputParameters);
            if (result.getErrorCollection().hasAnyErrors()) {
                // If the validation fails				
                logger.error("\nIssue cannot be created. Invalid parameter: " + result.getErrorCollection().getErrors() + "\nReason: " + result.getErrorCollection().getReasons());
            } else {
             newEpic = issueService.create(loggedInUser, result).getIssue();
            }
        
            
        List<CityBean> cities = dBManager.getCitiesForRoute(routeBean);
        
        if(!cities.isEmpty()){
            createStories(newEpic,cities);
        }else{
            logger.error("No cities found user this route");
        }
        
    }

    private void createStories(MutableIssue newEpic, List<CityBean> cities) {
        MutableIssue newStory = null;
        for (CityBean city : cities) {

            IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
            issueInputParameters.setSummary(city.getCityName());
            issueInputParameters.setIssueTypeId(STORY_TYPE_ID);
            issueInputParameters.setProjectId(PROJECT_ID);
            issueInputParameters.setReporterId(loggedInUser.getName());
            issueInputParameters.addCustomFieldValue(EPIC_LINK_ID, newEpic.getKey());  //epic link
            IssueService.CreateValidationResult result = issueService
                    .validateCreate(loggedInUser, issueInputParameters);
            if (result.getErrorCollection().hasAnyErrors()) {
                // If the validation fails				
                logger.error("\nIssue cannot be created. Invalid parameter: " + result.getErrorCollection().getErrors() + "\nReason: " + result.getErrorCollection().getReasons());
            } else {
                try{
                    newStory = issueService.create(loggedInUser, result).getIssue();
                    logger.info("New Story created:"+newStory.getKey()+"Under Epic:"+ newEpic.getKey());
                }catch(Exception exception){
                    logger.error("\nIssue cannot be created. Invalid parameter: ");
                }
             
            }
        }
    }
    
    
}
