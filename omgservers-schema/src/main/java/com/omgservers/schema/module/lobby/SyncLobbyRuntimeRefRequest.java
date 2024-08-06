package com.omgservers.schema.module.lobby;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import com.omgservers.schema.module.ShardedRequest;
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
