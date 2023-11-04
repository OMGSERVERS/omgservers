package com.omgservers.service.module.user.impl.operation.getUserModuleClient;

import java.net.URI;

public interface GetUserModuleClientOperation {
    UserModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
