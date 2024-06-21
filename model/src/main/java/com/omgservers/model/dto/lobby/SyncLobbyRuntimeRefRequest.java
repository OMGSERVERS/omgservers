package com.omgservers.model.dto.lobby;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLobbyRuntimeRefRequest implements ShardedRequest {

    @NotNull
    LobbyRuntimeRefModel lobbyRuntimeRef;

    @Override
    public String getRequestShardKey() {
        return lobbyRuntimeRef.getLobbyId().toString();
    }
}
