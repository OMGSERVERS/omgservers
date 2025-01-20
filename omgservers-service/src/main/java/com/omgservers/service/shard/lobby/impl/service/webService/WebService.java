package com.omgservers.service.shard.lobby.impl.service.webService;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetLobbyResponse> getLobby(GetLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);

    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(GetLobbyRuntimeRefRequest request);

    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(FindLobbyRuntimeRefRequest request);

    Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request);

    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(DeleteLobbyRuntimeRefRequest request);
}
