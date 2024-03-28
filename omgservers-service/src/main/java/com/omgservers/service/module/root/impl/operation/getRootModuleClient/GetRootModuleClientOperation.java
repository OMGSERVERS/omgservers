package com.omgservers.service.module.root.impl.operation.getRootModuleClient;

import java.net.URI;

public interface GetRootModuleClientOperation {
    RootModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
