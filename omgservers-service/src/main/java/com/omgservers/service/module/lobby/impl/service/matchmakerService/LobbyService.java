package com.omgservers.service.module.lobby.impl.service.matchmakerService;

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
import jakarta.validation.Valid;

public interface LobbyService {
    Uni<GetLobbyResponse> getLobby(@Valid GetLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobby(@Valid SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> deleteLobby(@Valid DeleteLobbyRequest request);

    Uni<GetLobbyRuntimeResponse> getLobbyRuntime(@Valid GetLobbyRuntimeRequest request);

    Uni<FindLobbyRuntimeResponse> findLobbyRuntime(@Valid FindLobbyRuntimeRequest request);

    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(@Valid SyncLobbyRuntimeRequest request);

    Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(@Valid DeleteLobbyRuntimeRequest request);
}
