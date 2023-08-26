package com.omgservers.base.module.internal.impl.operation.getInternalModuleClient;

import java.net.URI;

public interface GetInternalModuleClientOperation {
    InternalModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
