package com.omgservers.service.integration.jenkins.impl;

import com.omgservers.service.integration.jenkins.JenkinsIntegration;
import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.JenkinsService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JenkinsIntegrationImpl implements JenkinsIntegration {

    final JenkinsService jenkinsService;

    public JenkinsService getJenkinsService() {
        return jenkinsService;
    }
}
