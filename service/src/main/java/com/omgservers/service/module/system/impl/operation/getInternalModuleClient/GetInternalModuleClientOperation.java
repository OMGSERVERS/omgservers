package com.omgservers.service.module.system.impl.operation.getInternalModuleClient;

import java.net.URI;

public interface GetInternalModuleClientOperation {
    SystemModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
