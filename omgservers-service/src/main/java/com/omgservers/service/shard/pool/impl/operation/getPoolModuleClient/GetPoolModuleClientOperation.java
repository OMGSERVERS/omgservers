package com.omgservers.service.shard.pool.impl.operation.getPoolModuleClient;

import java.net.URI;

public interface GetPoolModuleClientOperation {
    PoolModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
