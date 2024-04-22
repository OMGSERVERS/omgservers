package com.omgservers.service.integration.jenkins.impl.operation.getJenkinsClient;

import java.net.URI;

public interface GetJenkinsClientOperation {
    JenkinsClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
