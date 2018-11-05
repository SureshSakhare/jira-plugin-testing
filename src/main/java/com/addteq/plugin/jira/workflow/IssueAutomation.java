package com.addteq.plugin.jira.workflow;

import com.addteq.plugin.service.AutomationHandler;
import com.atlassian.jira.component.ComponentAccessor;
import java.util.Map;

import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the post-function class that gets executed at the end of the transition.
 * Any parameters that were saved in your factory class will be available in the transientVars Map.
 */
public class IssueAutomation extends AbstractJiraFunctionProvider
{
    private static final Logger log = LoggerFactory.getLogger(IssueAutomation.class);
    
    private final AutomationHandler automationHandler;

    public IssueAutomation(AutomationHandler automationHandler) {
        this.automationHandler = automationHandler;
    }

    public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException
    {
        MutableIssue issue = getIssue(transientVars);
        automationHandler.createEpicAndStories(issue);
        
        
    }
}