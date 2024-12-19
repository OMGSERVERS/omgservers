package com.omgservers.service.module.alias.impl.operation.getAliasModuleClient;

import java.net.URI;

public interface GetAliasModuleClientOperation {
    AliasModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
