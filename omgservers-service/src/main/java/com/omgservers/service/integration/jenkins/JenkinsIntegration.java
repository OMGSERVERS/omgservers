package com.omgservers.service.integration.jenkins;

import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.JenkinsService;

public interface JenkinsIntegration {

    JenkinsService getJenkinsService();
}
