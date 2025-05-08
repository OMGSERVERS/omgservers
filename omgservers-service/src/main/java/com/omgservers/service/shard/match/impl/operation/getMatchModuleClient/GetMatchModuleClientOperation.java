package com.omgservers.service.shard.match.impl.operation.getMatchModuleClient;

import java.net.URI;

public interface GetMatchModuleClientOperation {
    MatchModuleClient execute(URI uri);
}
