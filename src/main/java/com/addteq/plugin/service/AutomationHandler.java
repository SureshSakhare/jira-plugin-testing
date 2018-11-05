package com.addteq.plugin.service;

import com.atlassian.jira.issue.Issue;

/**
 *
 * @author suresh
 */
public interface AutomationHandler {

    public void createEpicAndStories(Issue issue);
}
