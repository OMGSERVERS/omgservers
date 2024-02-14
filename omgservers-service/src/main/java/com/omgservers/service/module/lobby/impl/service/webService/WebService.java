package com.omgservers.service.module.lobby.impl.service.webService;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetLobbyResponse> getLobby(GetLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobby(SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> deleteLobby(DeleteLobbyRequest request);

    Uni<GetLobbyRuntimeResponse> getLobbyRuntime(GetLobbyRuntimeRequest request);

    Uni<FindLobbyRuntimeResponse> findLobbyRuntime(FindLobbyRuntimeRequest request);

    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(SyncLobbyRuntimeRequest request);

    Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(DeleteLobbyRuntimeRequest request);
}
