package com.omgservers.service.shard.matchmaker.impl.operation.getMatchmakerModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetMatchmakerModuleClientOperationImpl extends GetRestClientOperationImpl<MatchmakerModuleClient>
        implements GetMatchmakerModuleClientOperation {

    public GetMatchmakerModuleClientOperationImpl() {
        super(MatchmakerModuleClient.class);
    }
}
