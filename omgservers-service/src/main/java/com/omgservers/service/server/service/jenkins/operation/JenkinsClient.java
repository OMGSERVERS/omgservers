package com.omgservers.service.server.service.jenkins.operation;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(JenkinsExceptionMapper.class)
@RegisterClientHeaders(JenkinsClientHeadersFactory.class)
public interface JenkinsClient extends JenkinsApi {
}
