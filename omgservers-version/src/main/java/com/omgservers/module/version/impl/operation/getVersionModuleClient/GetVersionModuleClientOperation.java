package com.omgservers.module.version.impl.operation.getVersionModuleClient;

import java.net.URI;

public interface GetVersionModuleClientOperation {
    VersionModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
