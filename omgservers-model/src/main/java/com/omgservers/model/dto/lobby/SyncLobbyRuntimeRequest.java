package com.omgservers.model.dto.lobby;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLobbyRuntimeRequest implements ShardedRequest {

    @NotNull
    LobbyRuntimeModel lobbyRuntime;

    @Override
    public String getRequestShardKey() {
        return lobbyRuntime.getLobbyId().toString();
    }
}
