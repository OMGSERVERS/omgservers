package com.omgservers.router.integration.impl.operation;

import java.net.URI;

public interface GetServiceClientOperation {
    ServiceClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
