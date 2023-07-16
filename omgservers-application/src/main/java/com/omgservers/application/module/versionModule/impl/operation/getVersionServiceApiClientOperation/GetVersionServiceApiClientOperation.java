package com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation;

import java.net.URI;

public interface GetVersionServiceApiClientOperation {
    VersionServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
