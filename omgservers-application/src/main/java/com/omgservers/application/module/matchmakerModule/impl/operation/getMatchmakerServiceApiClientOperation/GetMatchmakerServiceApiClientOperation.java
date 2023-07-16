package com.omgservers.application.module.matchmakerModule.impl.operation.getMatchmakerServiceApiClientOperation;

import java.net.URI;

public interface GetMatchmakerServiceApiClientOperation {
    MatchmakerServiceApiClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
