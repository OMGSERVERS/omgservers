package com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation;

import java.net.URI;

public interface GetRuntimeServiceApiClientOperation {
    RuntimeServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
