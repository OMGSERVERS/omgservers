package com.omgservers.service.shard.matchmaker.impl.operation.getMatchmakerModuleClient;

import java.net.URI;

public interface GetMatchmakerModuleClientOperation {
    MatchmakerModuleClient execute(URI uri);
}
