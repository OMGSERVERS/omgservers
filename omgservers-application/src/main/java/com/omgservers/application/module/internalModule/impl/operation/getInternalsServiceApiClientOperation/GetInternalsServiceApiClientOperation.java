package com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation;

import java.net.URI;

public interface GetInternalsServiceApiClientOperation {
    InternalsServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
