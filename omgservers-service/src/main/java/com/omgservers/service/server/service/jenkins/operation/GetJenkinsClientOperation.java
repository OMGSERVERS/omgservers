package com.omgservers.service.server.service.jenkins.operation;

import java.net.URI;

public interface GetJenkinsClientOperation {
    JenkinsClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
