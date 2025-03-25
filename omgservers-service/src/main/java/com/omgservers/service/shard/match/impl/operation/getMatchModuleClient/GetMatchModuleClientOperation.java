package com.omgservers.service.shard.match.impl.operation.getMatchModuleClient;

import java.net.URI;

public interface GetMatchModuleClientOperation {
    MatchModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
