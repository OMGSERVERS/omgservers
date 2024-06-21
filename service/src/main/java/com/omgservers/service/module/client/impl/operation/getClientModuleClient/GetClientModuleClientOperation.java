package com.omgservers.service.module.client.impl.operation.getClientModuleClient;

import java.net.URI;

public interface GetClientModuleClientOperation {
    ClientModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
