package com.omgservers.service.shard.lobby.impl.operation.getLobbyModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetLobbyModuleClientOperationImpl extends GetRestClientOperationImpl<LobbyModuleClient>
        implements GetLobbyModuleClientOperation {

    public GetLobbyModuleClientOperationImpl() {
        super(LobbyModuleClient.class);
    }
}
