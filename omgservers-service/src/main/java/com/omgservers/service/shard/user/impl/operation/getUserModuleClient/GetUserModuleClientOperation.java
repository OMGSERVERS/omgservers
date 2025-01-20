package com.omgservers.service.shard.user.impl.operation.getUserModuleClient;

import java.net.URI;

public interface GetUserModuleClientOperation {
    UserModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
