package com.omgservers.service.shard.match.impl.operation.getMatchModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetMatchModuleClientOperationImpl extends GetRestClientOperationImpl<MatchModuleClient>
        implements GetMatchModuleClientOperation {

    public GetMatchModuleClientOperationImpl() {
        super(MatchModuleClient.class);
    }
}
