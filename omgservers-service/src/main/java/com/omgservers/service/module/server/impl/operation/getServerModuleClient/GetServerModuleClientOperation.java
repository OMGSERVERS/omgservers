package com.omgservers.service.module.server.impl.operation.getServerModuleClient;

import java.net.URI;

public interface GetServerModuleClientOperation {
    ServerModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
