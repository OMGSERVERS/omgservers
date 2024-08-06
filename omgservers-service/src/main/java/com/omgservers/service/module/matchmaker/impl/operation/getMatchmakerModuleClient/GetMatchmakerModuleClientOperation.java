package com.omgservers.service.module.matchmaker.impl.operation.getMatchmakerModuleClient;

import java.net.URI;

public interface GetMatchmakerModuleClientOperation {
    MatchmakerModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
