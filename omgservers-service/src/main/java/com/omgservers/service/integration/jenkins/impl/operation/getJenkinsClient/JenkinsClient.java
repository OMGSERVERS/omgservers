package com.omgservers.service.integration.jenkins.impl.operation.getJenkinsClient;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(JenkinsExceptionMapper.class)
@RegisterClientHeaders(JenkinsClientHeadersFactory.class)
public interface JenkinsClient extends JenkinsApi {
}
