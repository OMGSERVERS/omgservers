package com.omgservers.service.shard.lobby.impl.operation.getLobbyModuleClient;

import java.net.URI;

public interface GetLobbyModuleClientOperation {
    LobbyModuleClient execute(URI uri);
}
