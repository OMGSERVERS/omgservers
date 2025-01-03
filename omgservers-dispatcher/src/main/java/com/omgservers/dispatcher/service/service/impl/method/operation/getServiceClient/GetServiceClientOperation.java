package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceClient;

import java.net.URI;

public interface GetServiceClientOperation {
    ServiceModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
