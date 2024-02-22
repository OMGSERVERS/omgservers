package com.omgservers.service.module.lobby.impl.service.lobbyService;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface LobbyService {
    Uni<GetLobbyResponse> getLobby(@Valid GetLobbyRequest request);

    Uni<SyncLobbyResponse> syncLobby(@Valid SyncLobbyRequest request);

    Uni<DeleteLobbyResponse> deleteLobby(@Valid DeleteLobbyRequest request);

    Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(@Valid GetLobbyRuntimeRefRequest request);

    Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(@Valid FindLobbyRuntimeRefRequest request);

    Uni<SyncLobbyRuntimeResponse> syncLobbyRuntimeRef(@Valid SyncLobbyRuntimeRefRequest request);

    Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(@Valid DeleteLobbyRuntimeRefRequest request);
}
